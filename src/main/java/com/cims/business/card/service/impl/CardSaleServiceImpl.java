package com.cims.business.card.service.impl;

import com.cims.business.CommonService;
import com.cims.business.card.entity.Card;
import com.cims.business.card.entity.CardProject;
import com.cims.business.card.entity.CardSale;
import com.cims.business.card.entity.Warehouse;
import com.cims.business.card.mapper.CardSaleMapper;
import com.cims.business.card.service.CardSaleService;
import com.cims.business.card.vo.*;
import com.cims.common.Constants;
import com.cims.common.context.UserContext;
import com.cims.common.enums.CardStatusEnum;
import com.cims.common.util.ExtraCollectionUtils;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;
import com.cims.framework.page.PageHelp;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author belly
 * @create 4/21/19 8:00 PM
 * @description 卡片出库信息
 */

@Service
public class CardSaleServiceImpl extends CommonService implements CardSaleService {

    @Autowired
    private CardSaleMapper cardSaleMapper;


    @Override
    public PageDataResult<CardSaleListVO> list(SaleCondition conditionVO, PageBean pageBean) {

        // 拥有入库角色且不是管理员，则只能看到自身绑定仓库的数据
        if (UserContext.getUser().getRoles().contains(Constants.OUT_CARD)
                && !UserContext.hasRoles(Constants.ADMIN,Constants.OP)){
            Warehouse warehouse = warehouseMapper.getByUserId(UserContext.getUserId());
            conditionVO.setWarehouseId(warehouse.getId());
        }

        PageHelp.pageAndOrderBy(pageBean);
        List<CardSaleListVO> list = cardSaleMapper.list(conditionVO);
        return PageHelp.getDataResult(list, pageBean);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saleCards(CardSaleVO cardSaleVO) throws BizException {
        List<String> cardNos = cardSaleVO.getCardNos();
        List<Card> errCardList;
        List<String> errCardNos;
        // 确定卡片都存在
        List<Card> dbCardList = cardMapper.selectByCardNos(cardNos);
        List<String> dbCardNos = dbCardList.stream().map(Card::getCardNo).collect(Collectors.toList());
        cardNos.removeAll(dbCardNos);
        if (CollectionUtils.isNotEmpty(cardNos)){
            throw new BizException("卡号 " + cardNos + " 在系统中不存在，请修改后重试");
        }
        cardNos.addAll(dbCardNos);
        // 确认卡片为未出库状态
        errCardList = dbCardList.stream()
                .filter(m -> !m.getStatus().equals(CardStatusEnum.UNUSED.getBackValue()))
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(errCardList)){
            errCardNos = errCardList.stream().map(Card::getCardNo).collect(Collectors.toList());
            throw new BizException("卡号 " + errCardNos + " 不是未出状态，不能进行出库操作");
        }
        // 若为出库员角色且不是管理员确认卡片仓库正确
        if (UserContext.getUser().getRoles().contains(Constants.OUT_CARD)
                && !UserContext.hasRoles(Constants.ADMIN,Constants.OP)){
            Warehouse warehouse = warehouseMapper.getByUserId(UserContext.getUserId());
            errCardList = dbCardList.stream().filter(m -> !m.getWarehouseId().equals(warehouse.getId())).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(errCardList)){
                errCardNos = errCardList.stream().map(Card::getCardNo).collect(Collectors.toList());
                throw new BizException("卡号 " + errCardNos + " 对应仓库不正确无法出库，当前用户属于 " + warehouse.getName() + " 仓库");
            }
        }
        // 确认项目正确
        List<CardProject> dbProjectList = cardProjectMapper.list(new CardProject());
        List<Integer> dbProjectIds = dbProjectList.stream().map(CardProject::getId).collect(Collectors.toList());
        if (!dbProjectIds.contains(cardSaleVO.getProjectId())){
            throw new BizException("填写的项目信息在系统中不存在，请修改后重试");
        }

        // 出库 - 更新卡库状态
        cardMapper.updateStatusBatch(CardStatusEnum.USED.getBackValue(), cardNos);
        // 出库 - 新增出库记录
        List<CardSale> newSaleList = new ArrayList<>();
        for (String curCardNo : cardNos){
            CardSale curSale = new CardSale();
            curSale.setCardNo(curCardNo);
            curSale.setPrice(cardSaleVO.getPrice());
            curSale.setReceiver(cardSaleVO.getReceiver());
            curSale.setSaleDate(cardSaleVO.getSaleDate());
            curSale.setProjectId(cardSaleVO.getProjectId());
            newSaleList.add(curSale);
        }
        cardSaleMapper.insertBatch(newSaleList, UserContext.getUserId());
    }


    @Override
    public PageDataResult<CardSaleDetailVO> listDetails(SaleCondition conditionVO, PageBean pageBean) {
        // 拥有入库角色且不是管理员，则只能看到自身绑定仓库的数据
        if (UserContext.getUser().getRoles().contains(Constants.OUT_CARD)
                && !UserContext.hasRoles(Constants.ADMIN,Constants.OP)){
            Warehouse warehouse = warehouseMapper.getByUserId(UserContext.getUserId());
            conditionVO.setWarehouseId(warehouse.getId());
        }

        PageHelp.pageAndOrderBy(pageBean);
        List<CardSaleDetailVO> list = cardSaleMapper.listDetail(conditionVO);
        PageDataResult<CardSaleDetailVO> result =  PageHelp.getDataResult(list, pageBean);

        List<Integer> projectIds = list.stream().map(CardSaleDetailVO::getProjectId).distinct().collect(Collectors.toList());
        List<String> cardNos = list.stream().map(CardSaleDetailVO::getCardNo).collect(Collectors.toList());
        updateListProjectName(list, projectIds);
        updateListWarehouseName(list);
        updateListChannelName(list);
        updateListProblemDesc(list, cardNos);
        return result;
    }

    @Override
    public void updateCardSaleDetail(CardSaleVO cardSaleVO) throws BizException {
        // 检查出库记录存在
        CardSale cardSale = cardSaleMapper.selectByPrimaryKey(cardSaleVO.getId());
        if (cardSale == null){
            throw new BizException("系统查询不到此数据，无法修改。请刷新页面后重试");
        }

        BeanUtils.copyProperties(cardSaleVO, cardSale);
        cardSaleMapper.updateByPrimaryKeySelective(cardSale);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importCardSaleDetails(List<CardSaleImportVO> list) throws BizException {
        // excel对象都为在用状态
        List<String> errCardNos = list.stream().filter(m -> m.getStatus() == null || !m.getStatus().equals(CardStatusEnum.USED.getBackValue())).map(CardSaleImportVO::getCardNo).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(errCardNos)){
            throw new BizException("Excel文件中卡号: " + errCardNos + " 状态不正确，请检查后重试");
        }

        // 确定卡片都存在
        List<String> cardNos = list.stream().map(CardSaleImportVO::getCardNo).collect(Collectors.toList());
        List<Card> dbCardList = cardMapper.selectByCardNos(cardNos);
        List<String> dbCardNos = dbCardList.stream().map(Card::getCardNo).collect(Collectors.toList());
        cardNos.removeAll(dbCardNos);
        if (CollectionUtils.isNotEmpty(cardNos)){
            throw new BizException("Excel文件中卡号: " + cardNos + " 在系统中不存在，请修改后重试");
        }
        cardNos.addAll(dbCardNos);
        // 确认卡片为未出库状态
        errCardNos = dbCardList.stream()
                .filter(m -> !m.getStatus().equals(CardStatusEnum.UNUSED.getBackValue()))
                .map(Card::getCardNo)
                .collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(errCardNos)){
            throw new BizException("系统中卡号: " + errCardNos + " 不是未出状态，不能进行出库操作");
        }

        // 项目名称存在并更新项目Id
        List<CardProject> dbProjectList = cardProjectMapper.list(new CardProject());
        Map<String, Integer> map = dbProjectList.stream().collect(Collectors.toMap(CardProject::getProjectName, CardProject::getId));
        list.stream().forEach(m -> m.setProjectId(map.get(m.getProjectName())));
        errCardNos = list.stream().filter(m -> m.getProjectId() == null).map(CardSaleImportVO::getCardNo).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(errCardNos)){
            throw new BizException("Excel中卡号：" + errCardNos + " 填写的项目信息在系统中不存在");
        }

        // 分割处理入库
        List<List<CardSaleImportVO>> groupedList = ExtraCollectionUtils.fixedGrouping(list, 1000);
        for (List<CardSaleImportVO> curList : groupedList){
            List<String> curCardNos = curList.stream().map(CardSaleImportVO::getCardNo).collect(Collectors.toList());
            cardMapper.updateStatusBatch(CardStatusEnum.USED.getBackValue(), cardNos);
            List<CardSale> newSaleList = new ArrayList<>();
            for (CardSaleImportVO curData : curList){
                CardSale curSale = new CardSale();
                curSale.setCardNo(curData.getCardNo());
                curSale.setPrice(curData.getPrice());
                curSale.setReceiver(curData.getReceiver());
                curSale.setSaleDate(curData.getSaleDate());
                curSale.setProjectId(curData.getProjectId());
                newSaleList.add(curSale);
            }
            cardSaleMapper.insertBatch(newSaleList, UserContext.getUserId());
        }
    }
}
