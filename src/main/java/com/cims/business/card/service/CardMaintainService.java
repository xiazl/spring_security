package com.cims.business.card.service;

import com.cims.business.card.vo.*;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;

import java.util.List;

/**
 * @author baidu
 * @date 2019-04-18 15:39
 * @description 卡片维护
 **/
public interface CardMaintainService {

    /**
     * 维护列表
     * @param condition
     * @param pageBean
     * @return
     */
    PageDataResult<CardMaintainListVO> list(Condition condition, PageBean pageBean);

    /**
     * 新增停用
     * @param disableVO
     */
    List<String> disable(CardDisableVO disableVO) throws BizException;

    /**
     * 新增冻结
     * @param freezeVO
     */
    List<String> freeze(CardFreezeVO freezeVO) throws BizException;

    /**
     * 新增问题卡
     * @param problemVO
     */
    List<String> convertProblem(CardProblemVO problemVO) throws BizException;

    /**
     * 新增停用
     * @param disableVOS
     */
    List<String> disable(List<CardDisableVO> disableVOS) throws BizException;

    /**
     * 新增冻结
     * @param freezeVOS
     */
    List<String> freeze(List<CardFreezeVO> freezeVOS) throws BizException;


    /**
     * 修改
     * @param maintainListVO
     */
    void update(CardMaintainListVO maintainListVO);

    /**
     * 修改状态还原
     * @param id
     * @param status
     */
    void updateStatus(Long id,Integer status);

    /**
     * 删除
     * @param ids
     */
    void delete(List<Long> ids);
}
