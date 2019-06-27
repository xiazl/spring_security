package com.cims.business.card.service;

import com.cims.business.card.vo.*;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;

import java.util.List;

/**
 * @author belly
 * @create 4/21/19 8:00 PM
 * @description 卡片出库信息
 */
public interface CardSaleService {

    /**
     * 未出卡片列表
     * @param conditionVO
     * @param pageBean
     * @return
     */
    PageDataResult<CardSaleListVO> list(SaleCondition conditionVO, PageBean pageBean);

    /**
     * 多选卡片出库
     * @param cardSaleVO
     * @throws BizException
     */
    void saleCards(CardSaleVO cardSaleVO) throws BizException;

    /**
     * 出库记录列表
     * @param conditionVO
     * @param pageBean
     * @return
     */
    PageDataResult<CardSaleDetailVO> listDetails(SaleCondition conditionVO, PageBean pageBean);

    /**
     * 更新出库记录
     * @param cardSaleVO
     * @throws BizException
     */
    void updateCardSaleDetail(CardSaleVO cardSaleVO) throws BizException;

    /**
     * 导入出库记录
     * @param list
     */
    void importCardSaleDetails(List<CardSaleImportVO> list) throws BizException;

}
