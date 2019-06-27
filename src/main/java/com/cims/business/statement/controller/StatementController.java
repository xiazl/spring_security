package com.cims.business.statement.controller;

import com.cims.business.statement.service.StatementService;
import com.cims.business.statement.vo.*;
import com.cims.common.Constants;
import com.cims.framework.exception.BizException;
import com.cims.framework.response.ResponseData;
import com.cims.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * @author belly
 * @create 4/22/19 9:07 PM
 * @description 报表
 */

@RestController
@RequestMapping(value = "/statement")
public class StatementController {

    @Autowired
    private StatementService statementService;

    /**
     * 计算渠道应付款
     *
     * 生成‘渠道 – 预估应付款’报表
     * @return
     */
    @RequestMapping(value = "/calcPayment")
    @RolesAllowed({Constants.STOCK,Constants.SETTLER,Constants.ADMIN,Constants.OP})
    public Result calcPayment() throws BizException{
        List<StatementCalcPaymentVO> list = statementService.calcPayment();
        return ResponseData.success(list);
    }


    /**
     * 计算项目应收款
     *
     * 生成‘出卡项目 – 预估应收款’报表
     * @return
     */
    @RequestMapping(value = "/calcReceipt")
    @RolesAllowed({Constants.STOCK,Constants.SETTLER,Constants.ADMIN,Constants.OP})
    public Result calcReceipt() throws BizException{
        List<StatementCalcReceiptVO> list = statementService.calcReceipt();
        return ResponseData.success(list);
    }




    /**
     * 渠道处理方式统计
     * @param condition
     * @return
     */
    @RequestMapping(value = "/channelProcessMethod")
    @RolesAllowed({Constants.STOCK,Constants.SETTLER,Constants.ADMIN,Constants.OP})
    public Result channelProcessMethod(StatementCondition condition){
        List<ChannelProcessMethodVO> list = statementService.channelProcessMethod(condition);
        return ResponseData.success(list);
    }


    /**
     * 渠道处理状态统计
     * @param condition
     * @return
     */
    @RequestMapping(value = "/channelProcessStatus")
    @RolesAllowed({Constants.STOCK,Constants.SETTLER,Constants.ADMIN,Constants.OP})
    public Result channelProcessStatus(StatementCondition condition){
        List<ChannelProcessStatusVO> list = statementService.channelProcessStatus(condition);
        return ResponseData.success(list);
    }


    /**
     * 渠道冻结类型统计
     * @param condition
     * @return
     */
    @RequestMapping(value = "/channelFreezeType")
    @RolesAllowed({Constants.STOCK,Constants.SETTLER,Constants.ADMIN,Constants.OP})
    public Result channelFreezeType(StatementCondition condition){
        List<ChannelFreezeTypeVO> list = statementService.channelFreezeType(condition);
        return ResponseData.success(list);
    }

    /**
     * 使用项目冻结类型统计
     * @param condition
     * @return
     */
    @RequestMapping(value = "/projectFreezeType")
    @RolesAllowed({Constants.STOCK,Constants.SETTLER,Constants.ADMIN,Constants.OP})
    public Result projectFreezeType(StatementCondition condition){
        List<ProjectFreezeTypeVO> list = statementService.projectFreezeType(condition);
        return ResponseData.success(list);
    }


    /**
     * 银行冻结类型统计
     * @param condition
     * @return
     */
    @RequestMapping(value = "/bankFreezeType")
    @RolesAllowed({Constants.STOCK,Constants.SETTLER,Constants.ADMIN,Constants.OP})
    public Result bankFreezeType(StatementCondition condition){
        List<BankFreezeTypeVO> list = statementService.bankFreezeType(condition);
        return ResponseData.success(list);
    }


    /**
     * 各仓库分类未用统计表
     * @return
     */
    @RequestMapping(value = "/warehouseUnused")
    @RolesAllowed({Constants.STOCK,Constants.SETTLER,Constants.ADMIN,Constants.OP})
    public Result warehouseUnused(){
        List<WarehouseUnusedVO> list = statementService.warehouseUnused();
        return ResponseData.success(list);
    }


    /**
     * 各仓库银行未用统计表
     * @return
     */
    @RequestMapping(value = "/warehouseBankUnused")
    @RolesAllowed({Constants.STOCK,Constants.SETTLER,Constants.ADMIN,Constants.OP})
    public Result warehouseBankUnused(){
        List<WarehouseBankUnusedVO> list = statementService.warehouseBankUnused();
        return ResponseData.success(list);
    }


    /**
     * 渠道存量统计表
     * @return
     */
    @RequestMapping(value = "/channelStock")
    @RolesAllowed({Constants.STOCK,Constants.SETTLER,Constants.ADMIN,Constants.OP})
    public Result channelStock(){
        List<ChannelStockVO> list = statementService.channelStock();
        return ResponseData.success(list);
    }


    /**
     * 停用统计
     * @return
     */
    @RequestMapping(value = "/channelDisableType")
    @RolesAllowed({Constants.STOCK,Constants.SETTLER,Constants.ADMIN,Constants.OP})
    public Result channelDisableType(StatementCondition condition){
        List<ChannelDisableTypeVO> list = statementService.channelDisableType(condition);
        return ResponseData.success(list);
    }

    /**
     * 使用项目在用统计表
     * @return
     */
    @RequestMapping(value = "/projectUsed")
    @RolesAllowed({Constants.STOCK,Constants.SETTLER,Constants.ADMIN,Constants.OP})
    public Result projectUsed(){
        ProjectUsedVO result = statementService.projectUsed();
        return ResponseData.success(result);
    }

}
