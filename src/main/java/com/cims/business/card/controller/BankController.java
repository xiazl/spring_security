package com.cims.business.card.controller;

import com.cims.business.card.entity.Bank;
import com.cims.business.card.service.BankService;
import com.cims.common.Constants;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;
import com.cims.framework.response.ResponseData;
import com.cims.framework.response.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * @author belly
 * @create 5/16/19 2:27 PM
 * @description 银行管理页面
 */

@RestController
@RequestMapping(value = "/bank")
@RolesAllowed({Constants.IN_CARD,Constants.ADMIN, Constants.OP})
public class BankController {

    @Autowired
    private BankService bankService;


    /**
     * 银行页面列表
     * @param condition
     * @param pageBean
     * @return
     */
    @RequestMapping(value = "/list")
    public Result list(Bank condition, PageBean pageBean){
        PageDataResult<Bank> list = bankService.list(condition, pageBean);
        return ResponseData.success(list);
    }


    /**
     * 新增银行
     * @param bankName
     * @return
     */
    @RequestMapping(value = "/add")
    public Result add(@RequestParam(value = "bankName") String bankName) throws BizException{
        Bank bank = new Bank();
        bank.setBankName(bankName);
        bankService.add(bank);
        return ResponseData.success();
    }


    /**
     * 修改银行
     * @param id
     * @param bankName
     * @return
     * @throws BizException
     */
    @RequestMapping(value = "/update")
    public Result update(@RequestParam(value = "id") Long id,
                         @RequestParam(value = "bankName") String bankName) throws BizException{
        Bank bank = new Bank();
        bank.setId(id);
        bank.setBankName(bankName);
        bankService.update(bank);
        return ResponseData.success();
    }


    /**
     * 删除银行
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete")
    public Result delete(@RequestParam(value = "ids") List<Long>ids){
        bankService.delete(ids);
        return ResponseData.success();
    }



}
