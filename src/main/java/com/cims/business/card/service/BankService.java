package com.cims.business.card.service;

import com.cims.business.card.entity.Bank;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;

import java.util.List;

/**
 * @author baidu
 * @date 2019-04-17 14:59
 * @description 银行
 **/
public interface BankService {
    /**
     * 银行下拉列表
     * @return
     */
    List<Bank> listBank();

    /**
     * 银行页面列表
     * @param condition
     * @param pageBean
     * @return
     */
    PageDataResult<Bank> list(Bank condition, PageBean pageBean);

    /**
     * 新增银行
     * @param bank
     */
    void add(Bank bank) throws BizException;


    /**
     * 修改银行
     * @param bank
     * @throws BizException
     */
    void update(Bank bank) throws BizException;


    /**
     * 删除银行
     * @param ids
     */
    void delete(List<Long> ids);

}
