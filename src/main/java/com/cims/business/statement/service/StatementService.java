package com.cims.business.statement.service;

import com.cims.business.statement.vo.*;
import com.cims.framework.exception.BizException;

import java.util.List;

/**
 * @author belly
 * @create 4/22/19 9:09 PM
 * @description 报表
 */
public interface StatementService {

    /**
     * 计算渠道应付款
     *
     * 生成‘渠道 – 预估应付款’报表
     * @return
     */
    List<StatementCalcPaymentVO> calcPayment() throws BizException;


    /**
     * 计算项目应收款
     *
     * 生成‘出卡项目 – 预估应收款’报表
     * @return
     */
    List<StatementCalcReceiptVO> calcReceipt() throws BizException;




    /**
     * 渠道处理方式统计
     * @param condition
     * @return
     */
    List<ChannelProcessMethodVO> channelProcessMethod(StatementCondition condition);


    /**
     * 渠道处理状态统计
     * @param condition
     * @return
     */
    List<ChannelProcessStatusVO> channelProcessStatus(StatementCondition condition);


    /**
     * 渠道冻结类型统计
     * @param condition
     * @return
     */
    List<ChannelFreezeTypeVO> channelFreezeType(StatementCondition condition);


    /**
     * 使用项目冻结类型统计
     * @param condition
     * @return
     */
    List<ProjectFreezeTypeVO> projectFreezeType(StatementCondition condition);


    /**
     * 银行冻结类型统计
     * @param condition
     * @return
     */
    List<BankFreezeTypeVO> bankFreezeType(StatementCondition condition);


    /**
     * 各仓库分类未用统计表
     * @return
     */
    List<WarehouseUnusedVO> warehouseUnused();

    /**
     * 各仓库银行未用统计表
     * @return
     */
    List<WarehouseBankUnusedVO> warehouseBankUnused();

    /**
     * 渠道存量统计表
     * @return
     */
    List<ChannelStockVO> channelStock();


    /**
     * 停用统计
     * @param condition
     * @return
     */
    List<ChannelDisableTypeVO> channelDisableType(StatementCondition condition);

    /**
     * 使用项目在用统计表
     * @return
     */
    ProjectUsedVO projectUsed();

}
