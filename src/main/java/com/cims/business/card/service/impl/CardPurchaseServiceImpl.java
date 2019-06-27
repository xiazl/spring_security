package com.cims.business.card.service.impl;

import com.cims.business.CommonService;
import com.cims.business.card.entity.*;
import com.cims.business.card.mapper.*;
import com.cims.business.card.service.CardPurchaseService;
import com.cims.business.card.vo.*;
import com.cims.common.Constants;
import com.cims.common.context.UserContext;
import com.cims.common.enums.CardStatusEnum;
import com.cims.common.enums.CardTypeEnum;
import com.cims.common.util.BeanUtilsEx;
import com.cims.common.util.EnumUtils;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;
import com.cims.framework.page.PageHelp;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author belly
 * @create 4/15/19 5:44 PM
 * @description 卡片入库信息
 */

@Service
public class CardPurchaseServiceImpl extends CommonService implements CardPurchaseService {

    @Autowired
    private CardPurchaseMapper cardPurchaseMapper;
    @Autowired
    private CardSaleMapper cardSaleMapper;
    @Autowired
    private BankMapper bankMapper;

    @Override
    public PageDataResult<CardPurchaseVO> list(PurchaseCondition conditionVO, PageBean pageBean) {

        // 拥有入库角色且不是管理员，则只能看到自身绑定仓库的数据
        if (UserContext.getUser().getRoles().contains(Constants.IN_CARD)
                && !UserContext.hasRoles(Constants.ADMIN,Constants.OP)){
            Warehouse warehouse = warehouseMapper.getByUserId(UserContext.getUserId());
            conditionVO.setWarehouseId(warehouse.getId());
        }

        PageHelp.pageAndOrderBy(pageBean);
        List<CardPurchaseVO> list = cardPurchaseMapper.list(conditionVO);
        PageDataResult<CardPurchaseVO> result =  PageHelp.getDataResult(list, pageBean);

        List<String> cardNos = list.stream().map(CardPurchaseVO::getCardNo).collect(Collectors.toList());
        updateListProblemDesc(list, cardNos);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCardAndPurchase(CardPurchaseVO cardPurchaseVO) throws BizException {

        // 检查必填字段
        checkCardPurchaseData(Arrays.asList(cardPurchaseVO));
        // 检查字段合法性
        validateCardPurchaseData(Arrays.asList(cardPurchaseVO));
        // 检查各Id字段合法性
        checkFiledById(Arrays.asList(cardPurchaseVO));
        // 检查卡片在系统中不存在
        checkCardNotExist(Arrays.asList(cardPurchaseVO));

        Long userId = UserContext.getUserId();
        Card card = new Card();
        BeanUtils.copyProperties(cardPurchaseVO, card);
        cardMapper.insertBatch(Arrays.asList(card), userId);

        CardPurchase cardPurchase = new CardPurchase();
        BeanUtils.copyProperties(cardPurchaseVO, cardPurchase);
        cardPurchaseMapper.insertBatch(Arrays.asList(cardPurchase), userId);

        if (cardPurchaseVO.getStatus().equals(CardStatusEnum.PROBLEM.getBackValue())){
            CardProblemVO cardProblem = new CardProblemVO();
            BeanUtils.copyProperties(cardPurchaseVO, cardProblem);
            cardProblem.setDate(cardPurchaseVO.getPurchaseDate());
            cardMaintainMapper.insertBatchProblem(Arrays.asList(cardProblem), userId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCardAndPurchase(CardPurchaseVO cardPurchaseVO) throws BizException {

        // 检查必填字段
        checkCardPurchaseData(Arrays.asList(cardPurchaseVO));
        // 检查字段合法性
        validateCardPurchaseData(Arrays.asList(cardPurchaseVO));
        // 检查各Id字段合法性
        checkFiledById(Arrays.asList(cardPurchaseVO));
        // 查询卡片是否存在
        Card oldCard = cardMapper.selectByCardNo(cardPurchaseVO.getCardNo());
        if (oldCard == null){
            throw new BizException("卡号 " + cardPurchaseVO.getCardNo() + " 不存在，无法更新");
        }
        // 状态仅能在未用和问题之间变化
        if (!oldCard.getStatus().equals(cardPurchaseVO.getStatus())){
            if (!oldCard.getStatus().equals(CardStatusEnum.UNUSED.getBackValue())
                    && !oldCard.getStatus().equals(CardStatusEnum.PROBLEM.getBackValue())){
                throw new BizException("只有未出状态和问题状态的卡片才能修改状态");
            }

            if (!cardPurchaseVO.getStatus().equals(CardStatusEnum.UNUSED.getBackValue())
                    && !cardPurchaseVO.getStatus().equals(CardStatusEnum.PROBLEM.getBackValue())){
                throw new BizException("卡片状态只能在未出状态和问题状态之间修改");
            }
        }

        Long userId = UserContext.getUserId();
        Card card = new Card();
        BeanUtils.copyProperties(cardPurchaseVO, card);
        cardMapper.insertBatch(Arrays.asList(card), userId);

        CardPurchase cardPurchase = new CardPurchase();
        BeanUtils.copyProperties(cardPurchaseVO, cardPurchase);
        cardPurchaseMapper.insertBatch(Arrays.asList(cardPurchase), userId);

        // card_maintain表数据处理
        // 问->问:更新, 未->问:新增
        if (cardPurchaseVO.getStatus().equals(CardStatusEnum.PROBLEM.getBackValue())){
            CardProblemVO cardProblem = new CardProblemVO();
            BeanUtils.copyProperties(cardPurchaseVO, cardProblem);
            cardProblem.setDate(cardPurchaseVO.getPurchaseDate());
            cardMaintainMapper.insertBatchProblem(Arrays.asList(cardProblem), userId);
        }
        // 问->未:删除(问题卡的维护记录不需要删除), 未->未:不处理
//        if (oldCard.getStatus().equals(CardStatusEnum.PROBLEM.getBackValue())
//                && cardPurchaseVO.getStatus().equals(CardStatusEnum.UNUSED.getBackValue())){
//            cardMaintainMapper.deleteByCardNos(Arrays.asList(cardPurchaseVO.getCardNo()));
//        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCardAndPurchase(List<Long> ids) throws BizException {
        // 仅未用与问题状态的卡片才能删除
        List<Card> cardList = cardMapper.selectByIds(ids);
        List<Card> errorList = cardList.stream().filter(m -> {
            if (!m.getStatus().equals(CardStatusEnum.PROBLEM.getBackValue())
                    && !m.getStatus().equals(CardStatusEnum.UNUSED.getBackValue())){
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(errorList)){
            List<String> errorCardNos = errorList.stream().map(Card::getCardNo).collect(Collectors.toList());
            throw new BizException("卡号 " + errorCardNos + " 不能删除，仅未用状态与问题状态的卡片才能删除");
        }

        List<String> cardNos = cardList.stream().map(Card::getCardNo).collect(Collectors.toList());
        cardMapper.deleteByCardNos(cardNos);
        cardPurchaseMapper.deleteByCardNos(cardNos);
        // 问题卡的维护记录不需要删除
//        List<String> problemCardNos = cardList.stream().filter(m -> m.getStatus().equals(CardStatusEnum.PROBLEM.getBackValue()))
//                .map(Card::getCardNo).collect(Collectors.toList());
//        if (CollectionUtils.isNotEmpty(problemCardNos)){
//            cardMaintainMapper.deleteByCardNos(problemCardNos);
//        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importCardAndPurchase(List<CardPurchaseVO> list) throws BizException {
        // 检查必填字段
        checkCardPurchaseData(list);
        // 检查字段合法性
        validateCardPurchaseData(list);
        // 检查各Name字段合法性
        checkFiledByName(list);
        // 检查卡片是否不存在
        checkCardNotExist(list);
        // 填入仓库id
        List<Warehouse> warehouseList = warehouseMapper.list();
        Map<String, Integer> warehouseMap = warehouseList.stream().collect(Collectors.toMap(Warehouse::getName, Warehouse::getId));
        list.stream().forEach(m -> m.setWarehouseId(warehouseMap.get(m.getWarehouseName())));
        // 填入渠道id
        List<CardChannel> channelList = cardChannelMapper.list(new CardChannel());
        Map<String, Integer> channelMap = channelList.stream().collect(Collectors.toMap(CardChannel::getChannelName, CardChannel::getId));
        list.stream().forEach(m -> m.setChannel(channelMap.get(m.getChannelStr())));

        // 入库
        Long userId = UserContext.getUserId();
        List<Card> cardList =new ArrayList<>();
        List<CardPurchase> cardPurchaseList = new ArrayList<>();
        List<CardProblemVO> cardProblemList = new ArrayList<>();

        for (CardPurchaseVO curData : list) {
            Card curCard = new Card();
            BeanUtils.copyProperties(curData, curCard);
            cardList.add(curCard);

            CardPurchase curCardPurchase = new CardPurchase();
            BeanUtils.copyProperties(curData, curCardPurchase);
            cardPurchaseList.add(curCardPurchase);

            if (curData.getStatus().equals(CardStatusEnum.PROBLEM.getBackValue())){
                CardProblemVO curCardProblem = new CardProblemVO();
                BeanUtils.copyProperties(curData, curCardProblem);
                curCardProblem.setDate(curData.getPurchaseDate());
                cardProblemList.add(curCardProblem);
            }
        }

        cardMapper.insertBatch(cardList, userId);
        cardPurchaseMapper.insertBatch(cardPurchaseList, userId);
        if (CollectionUtils.isNotEmpty(cardProblemList)){
            cardMaintainMapper.insertBatchProblem(cardProblemList, userId);
        }
    }

    @Override
    public void importUpdatePrice(List<CardPurchaseVO> list) throws BizException {
        // 检查价格是否合法
        List<CardPurchaseVO> errDataList = list.stream().filter(m -> m.getPrice() == null).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(errDataList)){
            List<String> errCardNos = errDataList.stream().map(CardPurchaseVO::getCardNo).collect(Collectors.toList());
            throw new BizException("卡号 " + errCardNos + " 对应日价格字段不正确，请修改后重试");

        }

        // 检查卡片是否已存在
        checkCardExist(list);

        // 批量更新
        List<CardPurchase> cardPurchaseList = new ArrayList<>();
        for (CardPurchaseVO curData : list) {
            CardPurchase curCardPurchase = new CardPurchase();
            BeanUtils.copyProperties(curData, curCardPurchase);
            cardPurchaseList.add(curCardPurchase);
        }
        List<String> cardNos = cardPurchaseList.stream().map(CardPurchase::getCardNo).collect(Collectors.toList());
        cardPurchaseMapper.updatePriceBatch(cardPurchaseList, cardNos);
    }

    // 检查卡片与入库信息必填字段是否完整
    private void checkCardPurchaseData(List<CardPurchaseVO> list) throws BizException {
        for (CardPurchaseVO m : list) {
            if (m.getStatus() == null || m.getMainType() == null){
                throw new BizException("卡号 " + m.getCardNo() + " 的状态或类型字段不正确，请修改后重试");
            }
            if (m.getStatus().equals(CardStatusEnum.PROBLEM.getBackValue())) {
                BeanUtilsEx.copyProperties(m, new CardPurchaseProblemVO());
                continue;
            }
            if (m.getStatus().equals(CardStatusEnum.UNUSED.getBackValue())) {
                if (m.getMainType().equals(CardTypeEnum.MainType.PERSONAL_BANK.getBackValue())) {
                    BeanUtilsEx.copyProperties(m, new CardPurchasePersonalUnuseVO());
                    continue;
                }
                if (m.getMainType().equals(CardTypeEnum.MainType.ALIPAY.getBackValue())) {
                    BeanUtilsEx.copyProperties(m, new CardPurchaseAlipayUnuseVO());
                    continue;
                }
                if (m.getMainType().equals(CardTypeEnum.MainType.MOBILE_BANK.getBackValue())) {
                    BeanUtilsEx.copyProperties(m, new CardPurchaseMobileUnuseVO());
                    continue;
                }
            }
            throw new BizException("卡号 " + m.getCardNo() + " 状态不正确，只能入库未用和问题状态的卡片");

        }
    }

    // 检查卡片与入库信息字段是否合规
    private void validateCardPurchaseData(List<CardPurchaseVO> list) throws BizException {
        List<CardPurchaseVO> errorDataList;
        List<String> errorCardNos;
        // 检查bankName合法
        List<String> excelBankNames = list.stream().map(CardPurchaseVO::getBankName).distinct().collect(Collectors.toList());
        List<Bank> dbBankList = bankMapper.listByNames(excelBankNames);
        List<String> dbbankNameList = dbBankList.stream().map(Bank::getBankName).distinct().collect(Collectors.toList());
        excelBankNames.removeAll(dbbankNameList);
        if (CollectionUtils.isNotEmpty(excelBankNames)){
            throw new BizException("银行名称 " + excelBankNames.toString() + " 在系统中不存在，请修改后重试");
        }
        // 检查类型
        errorDataList = list.stream().filter(m -> m.getMainType() == null).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(errorDataList)){
            errorCardNos = errorDataList.stream().map(CardPurchaseVO::getCardNo).collect(Collectors.toList());
            throw new BizException("卡号 " + errorCardNos + " 对应类型不正确，请修改后重试");
        }
        // 检查分类
        errorDataList = list.stream().filter(m -> m.getSecondaryType() == null).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(errorDataList)){
            errorCardNos = errorDataList.stream().map(CardPurchaseVO::getCardNo).collect(Collectors.toList());
            throw new BizException("卡号 " + errorCardNos + " 对应分类不正确，请修改后重试");
        }
        // 检查类型和分类对应关系
        errorDataList = list.stream().filter(m -> {
            CardTypeEnum.SecondaryType sType = EnumUtils.getEnum(CardTypeEnum.SecondaryType.class, m.getSecondaryType());
            if (sType == null || !sType.getMainType().equals(m.getMainType())){
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(errorDataList)){
            errorCardNos = errorDataList.stream().map(CardPurchaseVO::getCardNo).collect(Collectors.toList());
            throw new BizException("卡号 " + errorCardNos + " 的类型和分类对应关系不正确，请修改后重试");
        }
    }

    // 检查卡片是否已经存在 - 不存在则报错
    private void checkCardExist(List<CardPurchaseVO> list) throws BizException {
        List<String> cardNos = list.stream().map(CardPurchaseVO::getCardNo).collect(Collectors.toList());
        List<Card> dbCardList = cardMapper.selectByCardNos(cardNos);
        List<String> dbCardNos = dbCardList.stream().map(Card::getCardNo).collect(Collectors.toList());
        cardNos.removeAll(dbCardNos);
        if (CollectionUtils.isNotEmpty(cardNos)){
            throw new BizException("卡号 " + cardNos + " 在系统中不存在，请修改后重试");
        }
    }

    // 检查卡片是否不存在 - 存在则报错
    private void checkCardNotExist(List<CardPurchaseVO> list) throws BizException {
        List<String> cardNos = list.stream().map(CardPurchaseVO::getCardNo).collect(Collectors.toList());
        List<Card> dbCardList = cardMapper.selectByCardNos(cardNos);
        List<String> dbCardNos = dbCardList.stream().map(Card::getCardNo).collect(Collectors.toList());
        cardNos.retainAll(dbCardNos);
        if (CollectionUtils.isNotEmpty(cardNos)){
            throw new BizException("卡号 " + cardNos + " 在系统中已存在，请修改后重试");
        }
    }

    // 检查各名称字段是否正确
    private void checkFiledByName(List<CardPurchaseVO> list) throws BizException {
        List<CardPurchaseVO> errorDataList;
        List<String> errorCardNos;
        // 检查仓库名称 - 拥有入库角色，则只能入库到自身绑定的仓库
        if (UserContext.getUser().getRoles().contains(Constants.IN_CARD)){
            Warehouse warehouse = warehouseMapper.getByUserId(UserContext.getUserId());
            errorDataList = list.stream().filter(m -> m.getWarehouseName()==null || !m.getWarehouseName().equals(warehouse.getName())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(errorDataList)){
                errorCardNos = errorDataList.stream().map(CardPurchaseVO::getCardNo).collect(Collectors.toList());
                throw new BizException("卡号 " + errorCardNos + " 对应仓库不正确，当前用户属于 " + warehouse.getName() + " 仓库,请修改后重试");
            }
        } else if (UserContext.hasRoles(Constants.ADMIN,Constants.OP)){
            // 检查仓库名称 - 拥有管理员角色，则能入库到所有的仓库
            List<Warehouse> warehouseList = warehouseMapper.list();
            List<String> warehouseNameList = warehouseList.stream().map(Warehouse::getName).collect(Collectors.toList());
            errorDataList = list.stream().filter(m -> m.getWarehouseName()==null || !warehouseNameList.contains(m.getWarehouseName())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(errorDataList)){
                errorCardNos = errorDataList.stream().map(CardPurchaseVO::getCardNo).collect(Collectors.toList());
                throw new BizException("卡号 " + errorCardNos + " 对应仓库在系统中不存在,请修改后重试");
            }
        }
        // 检查渠道名称
        List<CardChannel> channelList = cardChannelMapper.list(new CardChannel());
        List<String> channelNameList = channelList.stream().map(CardChannel::getChannelName).collect(Collectors.toList());
        errorDataList = list.stream().filter(m -> m.getChannelStr()==null || !channelNameList.contains(m.getChannelStr())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(errorDataList)){
            errorCardNos = errorDataList.stream().map(CardPurchaseVO::getCardNo).collect(Collectors.toList());
            throw new BizException("卡号 " + errorCardNos + " 对应渠道在系统中不存在，请修改后重试");
        }
    }

    // 检查各Id字段是否正确
    private void checkFiledById(List<CardPurchaseVO> list) throws BizException {
        List<CardPurchaseVO> errorDataList;
        List<String> errorCardNos;
        // 检查仓库Id - 拥有入库角色且不是管理员，则只能入库到自身绑定的仓库
        if (UserContext.getUser().getRoles().contains(Constants.IN_CARD)
                && !UserContext.hasRoles(Constants.ADMIN,Constants.OP)){
            Warehouse warehouse = warehouseMapper.getByUserId(UserContext.getUserId());
            errorDataList = list.stream().filter(m -> !m.getWarehouseId().equals(warehouse.getId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(errorDataList)){
                errorCardNos = errorDataList.stream().map(CardPurchaseVO::getCardNo).collect(Collectors.toList());
                throw new BizException("卡号 " + errorCardNos + " 对应仓库不正确，当前用户属于 " + warehouse.getName() + " 仓库,请修改后重试");
            }
        } else if (UserContext.hasRoles(Constants.ADMIN,Constants.OP)){
            // 检查仓库Id - 拥有管理员角色，则能入库到所有的仓库
            List<Warehouse> warehouseList = warehouseMapper.list();
            List<Integer> warehouseIdList = warehouseList.stream().map(Warehouse::getId).collect(Collectors.toList());
            errorDataList = list.stream().filter(m -> !warehouseIdList.contains(m.getWarehouseId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(errorDataList)){
                errorCardNos = errorDataList.stream().map(CardPurchaseVO::getCardNo).collect(Collectors.toList());
                throw new BizException("卡号 " + errorCardNos + " 对应仓库在系统中不存在,请修改后重试");
            }
        }
        // 检查渠道Id
        List<CardChannel> channelList = cardChannelMapper.list(new CardChannel());
        List<Integer> channelIdList = channelList.stream().map(CardChannel::getId).collect(Collectors.toList());
        errorDataList = list.stream().filter(m -> !channelIdList.contains(m.getChannel())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(errorDataList)){
            errorCardNos = errorDataList.stream().map(CardPurchaseVO::getCardNo).collect(Collectors.toList());
            throw new BizException("卡号 " + errorCardNos + " 对应渠道在系统中不存在，请修改后重试");
        }
    }





}
