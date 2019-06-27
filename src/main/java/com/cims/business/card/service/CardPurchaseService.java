package com.cims.business.card.service;

import com.cims.business.card.vo.PurchaseCondition;
import com.cims.business.card.vo.CardPurchaseVO;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;

import java.util.List;

/**
 * @author belly
 * @create 4/15/19 5:43 PM
 * @description 卡片入库信息
 */

public interface CardPurchaseService {
    /**
     * 入库页面列表
     * @param conditionVO
     * @param pageBean
     * @return
     */
    PageDataResult<CardPurchaseVO> list(PurchaseCondition conditionVO, PageBean pageBean);

    /**
     * 新增单张卡片与入库信息
     * @param cardPurchaseVO
     * @throws BizException
     */
    void saveCardAndPurchase(CardPurchaseVO cardPurchaseVO) throws BizException;

    /**
     * 更新单张卡片与入库信息
     * @param cardPurchaseVO
     * @throws BizException
     */
    void updateCardAndPurchase(CardPurchaseVO cardPurchaseVO) throws BizException;

    /**
     * 批量删除卡片
     * @param ids
     * @throws BizException
     */
    void deleteCardAndPurchase(List<Long> ids) throws BizException;

    /**
     * 批量导入卡片与入库信息
     * @param list
     * @throws BizException
     */
    void importCardAndPurchase(List<CardPurchaseVO> list) throws BizException;

    /**
     * 批量更新入库价格
     * @param list
     * @throws BizException
     */
    void importUpdatePrice(List<CardPurchaseVO> list) throws BizException;

}
