package com.cims.business.card.service;

import com.cims.business.card.entity.CardProject;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;

import java.util.List;

/**
 * @author baidu
 * @date 2019-04-20 21:20
 * @description 出库项目
 **/
public interface CardProjectService {
    /**
     * 项目列表
     * @param condition
     * @param pageBean
     * @return
     */
    PageDataResult<CardProject> list(CardProject condition, PageBean pageBean);

    /**
     * 获取项目选项
     * @return
     */
    List<CardProject> list();

    /**
     * 新增
     * @param cardProject
     */
    void add(CardProject cardProject) throws BizException;

    /**
     * 修改
     * @param cardProject
     */
    void update(CardProject cardProject) throws BizException;

    /**
     * 删除
     * @param ids
     */
    void delete(List<Integer> ids);
}
