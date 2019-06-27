package com.cims.business.settlement.service;

import com.cims.business.settlement.entity.CardSettlement;
import com.cims.business.settlement.vo.*;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;

import java.util.Date;
import java.util.List;

/**
 * @author baidu
 * @date 2019-04-19 14:28
 * @description 卡片结算
 **/
public interface CardSettlementService {

    /**
     * 选卡列表
     * @param condition
     * @param pageBean
     * @return
     */
    PageDataResult<CardInfoListVO> listCard(CardInfoCondition condition, PageBean pageBean);

    /**
     * 结算记录列表
     * @param condition
     * @param pageBean
     * @return
     */
    PageDataResult<CardSettlementListVO> listSettlement(SettlementCondition condition, PageBean pageBean);

    /**
     * 默认结束时间获取
     * @param cardNos
     * @return
     */
    Date getDate(List<String> cardNos,Integer type);

    /**
     * 删除记录
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 新增入库结算
     * @param settlement
     * @param cardNos
     */
    List<String> addInData(CardSettlement settlement,List<String> cardNos) throws BizException;

    /**
     * 新增出库结算
     * @param settlement
     * @param cardNos
     */
    List<String> addOutData(CardSettlement settlement,List<String> cardNos) throws BizException;

    /**
     * 更新结算记录
     * @param vo
     */
    void update(CardSettlementListVO vo) throws BizException;

    /**
     * 更新结算记录付款日期
     * @param list
     */
    void updateFinishDate(List<CardSettlement> list) throws BizException;

}
