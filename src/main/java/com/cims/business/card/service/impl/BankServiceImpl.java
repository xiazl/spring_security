package com.cims.business.card.service.impl;

import com.cims.business.card.entity.Bank;
import com.cims.business.card.mapper.BankMapper;
import com.cims.business.card.service.BankService;
import com.cims.common.context.UserContext;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;
import com.cims.framework.page.PageHelp;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author baidu
 * @date 2019-04-17 15:00
 * @description TODO
 **/

@Service("bankService")
public class BankServiceImpl implements BankService {
    @Autowired
    private BankMapper bankMapper;

    @Override
    public List<Bank> listBank() {
        return bankMapper.listBank();
    }


    @Override
    public PageDataResult<Bank> list(Bank condition, PageBean pageBean) {
        PageHelp.pageAndOrderBy(pageBean);
        List<Bank> list = bankMapper.list(condition);
        return PageHelp.getDataResult(list, pageBean);
    }


    @Override
    public void add(Bank bank) throws BizException{
        if (StringUtils.isEmpty(bank.getBankName())){
            throw new BizException("银行名称不能为空");
        }
        List<Bank> oldList = bankMapper.listByNames(Arrays.asList(bank.getBankName()));
        if (!CollectionUtils.isEmpty(oldList)){
            throw new BizException("银行名称已存在");
        }
        bank.setCreateUserId(UserContext.getUserId());
        bankMapper.insert(bank);
    }


    @Override
    public void update(Bank bank) throws BizException {
        if (bank.getId() == null || StringUtils.isEmpty(bank.getBankName())){
            throw new BizException("参数不正确，请刷新页面后重试");
        }
        Bank oldBank = bankMapper.selectById(bank.getId());
        if (oldBank == null){
            throw new BizException("查询不到待修改银行，请刷新页面后重试");
        }
        oldBank = bankMapper.selectByBankName(bank.getBankName());
        if (oldBank != null && !oldBank.getId().equals(bank.getId()) ){
            throw new BizException("银行名称" + bank.getBankName() + "已存在，请修改后重试");
        }
        bankMapper.updateByPrimaryKey(bank);
    }

    @Override
    public void delete(List<Long> ids) {
        bankMapper.deleteByPrimaryKeys(ids);
    }
}
