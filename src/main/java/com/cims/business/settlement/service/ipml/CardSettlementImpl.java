package com.cims.business.settlement.service.ipml;

import com.cims.business.CommonService;
import com.cims.business.card.entity.Card;
import com.cims.business.card.entity.CardMaintain;
import com.cims.business.card.entity.CardPurchase;
import com.cims.business.card.entity.CardSale;
import com.cims.business.card.mapper.CardMaintainMapper;
import com.cims.business.card.mapper.CardPurchaseMapper;
import com.cims.business.card.mapper.CardSaleMapper;
import com.cims.business.settlement.entity.CardSettlement;
import com.cims.business.settlement.mapper.CardSettlementMapper;
import com.cims.business.settlement.service.CardSettlementService;
import com.cims.business.settlement.vo.CardInfoCondition;
import com.cims.business.settlement.vo.CardInfoListVO;
import com.cims.business.settlement.vo.CardSettlementListVO;
import com.cims.business.settlement.vo.SettlementCondition;
import com.cims.common.context.UserContext;
import com.cims.common.enums.CardStatusEnum;
import com.cims.common.enums.FreezeTypeEnum;
import com.cims.common.enums.SettlementTypeEnum;
import com.cims.common.util.DateUtils;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;
import com.cims.framework.page.PageHelp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author baidu
 * @date 2019-04-19 14:28
 * @description TODO
 **/

@Service("settlementService")
public class CardSettlementImpl extends CommonService implements CardSettlementService {

    private final static Integer DAYS_LIMIT = 15;    // 结算天数界限
    private final static Integer DAYS_OF_MONTH = 30; // 月固定天数
    private final static Integer ARCHIVE = 1;        // 归档不在使用

    @Autowired
    private CardSettlementMapper settlementMapper;
    @Autowired
    private CardPurchaseMapper purchaseMapper;
    @Autowired
    private CardSaleMapper saleMapper;
    @Autowired
    private CardMaintainMapper maintainMapper;

    @Override
    public PageDataResult<CardInfoListVO> listCard(CardInfoCondition condition, PageBean pageBean) {
        PageHelp.pageAndOrderBy(pageBean);
        List<CardInfoListVO> list;
        int type = condition.getType();
        if(type == SettlementTypeEnum.SETTLEMENT_IN.getBackValue()) {
            list = settlementMapper.listPurchaseCard(condition);
        } else {
            list = settlementMapper.listSaleCard(condition);
        }
        return PageHelp.getDataResult(list, pageBean);
    }

    @Override
    public PageDataResult<CardSettlementListVO> listSettlement(SettlementCondition condition, PageBean pageBean) {
        PageHelp.pageAndOrderBy(pageBean);
        List<CardSettlementListVO> list = settlementMapper.listSettlement(condition);
        return PageHelp.getDataResult(list, pageBean);
    }

    @Override
    public Date getDate(List<String> cardNos, Integer type) {
        List<CardSettlement> list = settlementMapper.listByCardNos(cardNos,type);
        Collections.sort(list, Comparator.comparing(CardSettlement::getStopDate).reversed());
        Date date = list.get(0).getStopDate();
        if(cardNos.size() != list.size()){
            List<CardPurchase> cardPurchases = purchaseMapper.listByCardNos(cardNos);
            Collections.sort(cardPurchases, Comparator.comparing(CardPurchase::getPurchaseDate).reversed());
            Date purchaseDate = cardPurchases.get(0).getPurchaseDate();
            if(purchaseDate.after(date)){
                date = purchaseDate;
            }
        }
        return date;
    }

    @Override
    public void delete(List<Long> ids) {
        settlementMapper.deleteByPrimaryKeys(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> addInData(CardSettlement cardSettlement,List<String> cardNos) throws BizException {
        List<Card> cards = cardMapper.selectByCardNos(cardNos);
        List<String> messages = checkCard(cardNos,cards);
        if(messages.size() > 0){
            return messages;
        }
        Map<String,Integer> statusMap = getCardStatusMap(cards);

        Map<String, CardPurchase> map = getPurchaseMap(cardNos);

        Map<String, Date> lastMap = getLastMap(cardNos,1);
        Map<String, CardMaintain> maintainsMap = getMaintainsMap(cardNos);

        List<CardSettlement> settlements = new ArrayList<>();
        for (String cardNo: cardNos){
            Date lastStopDate = lastMap.get(cardNo);
            CardMaintain cardMaintain = maintainsMap.get(cardNo);

            CardSettlement settlement = createCardSettlement(cardSettlement,lastStopDate,cardMaintain,cardNo);
            settlement.setStatus(statusMap.get(cardNo));
            CardPurchase cardPurchase = map.get(cardNo);
            Assert.notNull(cardPurchase,"该卡号不存在入库记录："+cardNo);

            Date startDate = null;
            if(lastStopDate == null){
                startDate = cardPurchase.getPurchaseDate();
            } else {
                startDate = lastStopDate;
            }

            int days = DateUtils.getDays(settlement.getStopDate(),startDate);
            if(days < 0){
                continue;
            }
            settlement.setDays(days);
            settlement.setStartDate(DateUtils.getDate(startDate,1));
            BigDecimal price = cardPurchase.getPrice();
            Assert.notNull(price,"入库结算价格为空："+cardNo);
            settlement.setPrice(price);
            settlement.setAmount(getAmount(price, days, cardMaintain));
            settlement.setDeductAmount(getDeductAmount(cardMaintain));

            settlement.setRealAmount(settlement.getAmount().subtract(settlement.getDeductAmount()));
            settlements.add(settlement);
        }

        Assert.notEmpty(settlements,"请填写有效数据");

        settlementMapper.updateIsLast(cardNos,SettlementTypeEnum.SETTLEMENT_IN.getBackValue());
        settlementMapper.insertBatchInSettlement(settlements, UserContext.getUserId());
        tagArchiveCard(cardNos,SettlementTypeEnum.SETTLEMENT_OUT.getBackValue());

        return Collections.emptyList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<String> addOutData(CardSettlement cardSettlement,List<String> cardNos) throws BizException {
        List<Card> cards = cardMapper.selectByCardNos(cardNos);
        List<String> messages = checkCard(cardNos,cards);
        if(messages.size() > 0){
           return messages;
        }
        Map<String,Integer> statusMap = getCardStatusMap(cards);

        Map<String, CardSale> map = getSaleMap(cardNos);

        Map<String, Date> lastMap = getLastMap(cardNos,2);
        Map<String, CardMaintain> maintainsMap = getMaintainsMap(cardNos);

        List<CardSettlement> settlements = new ArrayList<>();
        for (String cardNo: cardNos){
            Date lastStopDate = lastMap.get(cardNo);
            CardMaintain cardMaintain = maintainsMap.get(cardNo);

            CardSettlement settlement = createCardSettlement(cardSettlement,lastStopDate,cardMaintain,cardNo);
            settlement.setStatus(statusMap.get(cardNo));
            CardSale cardSale = map.get(cardNo);
            Assert.notNull(cardSale,"该卡号不存在出库记录："+cardNo);

            Date startDate = null;
            if(lastStopDate == null){
                startDate = cardSale.getSaleDate();
            } else {
                startDate = lastStopDate;
            }

            int days = DateUtils.getDays(settlement.getStopDate(),startDate);
            if(days < 0){
                continue;
            }
            settlement.setDays(days);
            settlement.setStartDate(DateUtils.getDate(startDate,1));
            BigDecimal price = cardSale.getPrice();
            Assert.notNull(price,"出库结算价格为空："+cardNo);

            settlement.setPrice(price);
            settlement.setAmount(getAmount(price, days, cardMaintain));
            settlements.add(settlement);
        }

        Assert.notEmpty(settlements,"请填写有效数据");

        settlementMapper.updateIsLast(cardNos,SettlementTypeEnum.SETTLEMENT_OUT.getBackValue());
        settlementMapper.insertBatchOutSettlement(settlements, UserContext.getUserId());

        tagArchiveCard(cardNos,SettlementTypeEnum.SETTLEMENT_IN.getBackValue());

        return Collections.emptyList();
    }

    @Override
    public void update(CardSettlementListVO vo) throws BizException {
        List<Long> ids = vo.getIds();
        int type = vo.getType();
        List<CardSettlement> settlements = settlementMapper.listByIds(ids,true);
        if (ids.size() != settlements.size()){
            throw new BizException("只能修改最新一次结算记录");
        }
        Map<Long,CardSettlement> map = settlements.stream().collect(Collectors.toMap(CardSettlement::getId,Function.identity()));
        List<String> cardNos = settlements.stream().map(CardSettlement::getCardNo).collect(Collectors.toList());
        Map<String, CardPurchase> purchaseMap = getPurchaseMap(cardNos);
        Map<String, CardSale> saleMap = getSaleMap(cardNos);
        Map<String, CardMaintain> maintainsMap = getMaintainsMap(cardNos);
        Map<String, Date> lastMap = getLastMap(cardNos,type);

        List<Card> cards = cardMapper.selectByCardNos(cardNos);
        Map<String,Integer> statusMap = getCardStatusMap(cards);

        for (Long id: ids){
            CardSettlement settlement = map.get(id);

            String cardNo = settlement.getCardNo();
            CardMaintain cardMaintain = maintainsMap.get(cardNo);
            Date lastStopDate = lastMap.get(cardNo);
            Date stopDate = getStopDate(settlement,lastStopDate,cardMaintain);
            settlement.setStopDate(stopDate);

            int days = DateUtils.getDays(stopDate,settlement.getStartDate()) + 1;
            if(days < 0){
                throw new BizException("结束时间必须晚于开始时间");
            }
            settlement.setDays(days);
            if(type == SettlementTypeEnum.SETTLEMENT_IN.getBackValue()) {
                CardPurchase cardPurchase = purchaseMap.get(cardNo);
                BigDecimal price = cardPurchase.getPrice();
                Assert.notNull(price, "入库结算价格为空：" + cardNo);
                settlement.setPrice(price);
                settlement.setAmount(getAmount(price, settlement.getDays(), cardMaintain));
                settlement.setDeductAmount(getDeductAmount(cardMaintain));
                settlement.setRealAmount(settlement.getAmount().subtract(settlement.getDeductAmount()));
            } else {
                CardSale cardSale = saleMap.get(cardNo);
                BigDecimal price = cardSale.getPrice();
                Assert.notNull(price, "出库结算价格为空：" + cardNo);
                settlement.setPrice(price);
                settlement.setAmount(getAmount(price, settlement.getDays(), cardMaintain));
            }

            settlement.setStatus(statusMap.get(cardNo));
        }

        if(type == SettlementTypeEnum.SETTLEMENT_IN.getBackValue()) {
            settlementMapper.updateInByPrimaryKeys(ids, settlements);
        } else {
            settlementMapper.updateOutByPrimaryKeys(ids, settlements);
        }
    }

    @Override
    public void updateFinishDate(List<CardSettlement> list) throws BizException {
        List<Long> ids = list.stream().map(CardSettlement::getId).collect(Collectors.toList());
        List<CardSettlement> settlements = settlementMapper.listByIds(ids,false);
        if (ids.size() != settlements.size()){
            throw new BizException("部分序号未找到，请仔细检查数据");
        }
        settlementMapper.updatePayDateByPrimaryKeys(ids, list);

    }


    /**
     * 获取银行卡的上次结算截止时间
     * @param cardNos
     * @param type 1 入库 2 出库
     * @return
     */
    private Map<String, Date> getLastMap(List<String> cardNos,Integer type){
        List<CardSettlement> list = settlementMapper.listByCardNos(cardNos, type);
        Map<String, Date> lastMap = list.stream().collect(Collectors.toMap(CardSettlement::getCardNo,
                CardSettlement::getStopDate));

        return lastMap;
    }

    /**
     * 获取银行卡的异常维护记录
     * @param cardNos
     * @return
     */
    private Map<String, CardMaintain> getMaintainsMap(List<String> cardNos){
        List<CardMaintain> maintains = maintainMapper.listByCardNos(cardNos);
        Map<String, CardMaintain> maintainsMap = maintains.stream().collect(Collectors.toMap(CardMaintain::getCardNo,
                Function.identity()));

        return maintainsMap;
    }

    /**
     * 获取入库记录
     * @param cardNos
     * @return
     */
    private Map<String,CardPurchase> getPurchaseMap(List<String> cardNos){
        List<CardPurchase> cardPurchases = purchaseMapper.listByCardNos(cardNos);
        Map<String, CardPurchase> map = cardPurchases.stream().collect(Collectors.toMap(CardPurchase::getCardNo,
                Function.identity()));
        return map;
    }

    /**
     * 获取出库记录
     * @param cardNos
     * @return
     */
    private Map<String,CardSale> getSaleMap(List<String> cardNos){
        List<CardSale> cardSales = saleMapper.listByCardNos(cardNos);
        Map<String, CardSale> map = cardSales.stream().collect(Collectors.toMap(CardSale::getCardNo, Function.identity()));
        return map;
    }

    /**
     * 获取银行卡状态
     * @param cards
     * @return
     */
    private Map<String,Integer> getCardStatusMap(List<Card> cards){
        Map<String, Integer> statusMap = cards.stream().collect(Collectors.toMap(Card::getCardNo,
                Card::getStatus));
        return statusMap;
    }

    /**
     * 创建结算对象
     * @param cardSettlement
     * @param lastStopDate
     * @param cardMaintain
     * @param cardNo
     * @return
     */
    private CardSettlement createCardSettlement(CardSettlement cardSettlement, Date lastStopDate, CardMaintain cardMaintain,
                                      String cardNo){
        CardSettlement settlement = new CardSettlement();
        BeanUtils.copyProperties(cardSettlement,settlement);
        settlement.setCardNo(cardNo);
        Date stopDate = getStopDate(settlement,lastStopDate,cardMaintain);
        settlement.setStopDate(stopDate);
        return settlement;
    }

    /**
     * 结算时间计算
     * @param cardSettlement
     */
    private Date getStopDate(CardSettlement cardSettlement, Date lastStopDate, CardMaintain cardMaintain){

        Date stopDate;
        if(lastStopDate != null && cardMaintain != null){
            Date mainDate = cardMaintain.getDate();
            if (mainDate.after(lastStopDate)) {
                stopDate = mainDate;
            } else {
                stopDate = lastStopDate;
            }
        } else if(cardMaintain != null){
            stopDate = cardMaintain.getDate();
        } else {
            stopDate = cardSettlement.getStopDate();
        }

        return stopDate;
    }


    /**
     * 结算金额计算
     * @param price
     * @param days
     * @param cardMaintain
     * @return
     */
    private BigDecimal getAmount(BigDecimal price,int days, CardMaintain cardMaintain){
        if(cardMaintain == null) {
            return price.multiply(new BigDecimal(days));
        }
        int status = cardMaintain.getStatus();
        BigDecimal amount = null;
        switch (status) {
            case 3:  // 冻结
                if(!FreezeTypeEnum.ARTIFICIAL.getBackValue().equals(cardMaintain.getFreezeType())) {
                    int quotient = days / DAYS_OF_MONTH;
                    int remainder = days % DAYS_OF_MONTH;
                    int effectiveDays = getEffectiveDays(remainder);
                    amount = price.multiply(new BigDecimal(DAYS_OF_MONTH)).multiply(new BigDecimal(quotient)).
                            add(price.multiply(new BigDecimal(effectiveDays)));
                }
                break;

            default:
                break;
        }

        if(amount == null){
            amount = price.multiply(new BigDecimal(days));
        }

        return amount;
    }

    /**
     * 有效天数计算: 15天以内以实际天数为准，15～30天算30天
     * @param days
     * @return
     */
    private Integer getEffectiveDays(Integer days){
        if(days <= DAYS_LIMIT){
            return days;
        }

        return DAYS_OF_MONTH;
    }


    /**
     * 扣减金额计算
     * @param cardMaintain
     * @return
     */
    private BigDecimal getDeductAmount(CardMaintain cardMaintain){
        if(cardMaintain == null || cardMaintain.getStatus().intValue() != CardStatusEnum.FREEZE.getBackValue()) {
            return new BigDecimal(0);
        }
        int processStatus = cardMaintain.getProcessStatus();
        BigDecimal deductAmount;
        switch (processStatus) {
            case 1:
                deductAmount = cardMaintain.getFreezeAmount().multiply(new BigDecimal(0.15));
                break;
            case 2:
                deductAmount = cardMaintain.getFreezeAmount();
                break;
            case 5:
                deductAmount = cardMaintain.getFreezeAmount().multiply(new BigDecimal(0.5));
                break;
            default:
                deductAmount = new BigDecimal(0);
                break;
        }

        return deductAmount;
    }

    /**
     * 标记归档卡 如果入库时检查出库已经进行了最后一次结算，则可以归档
     * @param cardNos
     */
    private void tagArchiveCard(List<String> cardNos,Integer type){
        List<CardMaintain> maintains = maintainMapper.listByCardNos(cardNos);
        List<String> maintainCardNos = maintains.stream().map(CardMaintain::getCardNo).collect(Collectors.toList());
        if(maintainCardNos.size() == 0){
            return;
        }
        List<String> list = settlementMapper.listCardNos(maintainCardNos,type);
        if(list.size() == 0){
            return;
        }
        cardMapper.updateArchiveBatch(list,ARCHIVE);
    }


}
