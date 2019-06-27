package com.cims.business.card.service;

import com.cims.business.card.vo.CardReturnListVO;
import com.cims.business.card.vo.Condition;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;

import java.util.List;

/**
 * @author baidu
 * @date 2019-04-17 19:39
 * @description 退库
 **/
public interface CardReturnService {

    /**
     * 退库列表
     * @param condition
     * @param pageBean
     * @return
     */
    PageDataResult<CardReturnListVO> list(Condition condition, PageBean pageBean);

    /**
     * 新增
     * @param returnVO
     */
    void add(CardReturnListVO returnVO) throws BizException;

    /**
     * 修改
     * @param returnVO
     */
    void update(CardReturnListVO returnVO);

    /**
     * 删除
     * @param ids
     */
    void delete(List<Long> ids);

    /**
     * 批量插入
     * @param list
     */
    List<String> addBatch(List<CardReturnListVO> list) throws BizException;
}
