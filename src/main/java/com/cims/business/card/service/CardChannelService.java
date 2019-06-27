package com.cims.business.card.service;

import com.cims.business.card.entity.CardChannel;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;

import java.util.List;

/**
 * @author baidu
 * @date 2019-04-20 21:20
 * @description 入库渠道
 **/
public interface CardChannelService {
    /**
     * 入库渠道列表
     * @param condition
     * @param pageBean
     * @return
     */
    PageDataResult<CardChannel> list(CardChannel condition, PageBean pageBean);

    /**
     * 获取渠道
     * @return
     */
    List<CardChannel> list();

    /**
     * 新增
     * @param cardProject
     */
    void add(CardChannel cardProject) throws BizException;

    /**
     * 修改
     * @param cardProject
     */
    void update(CardChannel cardProject) throws BizException;

    /**
     * 删除
     * @param ids
     */
    void delete(List<Integer> ids);
}
