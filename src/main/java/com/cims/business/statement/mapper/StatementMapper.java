package com.cims.business.statement.mapper;

import com.cims.business.statement.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author belly
 * @create 4/22/19 9:12 PM
 * @description 报表
 */

@Mapper
public interface StatementMapper {

    /**
     * 查询未出卡和已出卡应付款信息
     * @return
     */
    List<CalcInUnusedAndUsedVO> queryUnusedAndUsedPayment();


    /**
     * 查询停用卡应付款信息
     * @return
     */
    List<CalcInDisabledVO> queryDisabledPayment();

    /**
     * 查询问题卡应付款信息
     * @return
     */
    List<CalcInProblemVO> queryProblemPayment();

    /**
     * 查询冻结卡应付款信息
     * @return
     */
    List<CalcInFreezeVO> queryFreezePayment();


    /**
     * 查询启用卡应收款信息
     * @return
     */
    List<CalcOutUsedVO> queryUsedReceipt();

    /**
     * 查询停用卡应收款信息
     * @return
     */
    List<CalcOutDisabledVO> queryDisabledReceipt();

    /**
     * 查询问题卡应收款信息
     * @return
     */
    List<CalcOutProblemVO> queryProblemReceipt();


    /**
     * 查询冻结卡应收款信息
     * @return
     */
    List<CalcOutFreezeVO> queryFreezeReceipt();


    /**
     * 渠道处理方式统计
     * @return
     */
    List<ChannelProcessMethodVO> queryChannelProcessMethod(StatementCondition condition);

    /**
     * 渠道处理状态统计
     * @param condition
     * @return
     */
    List<ChannelProcessStatusVO> queryChannelProcessStatus(StatementCondition condition);

    /**
     * 渠道冻结类型统计
     * @param condition
     * @return
     */
    List<ChannelFreezeTypeVO> queryChannelFreezeType(StatementCondition condition);


    /**
     * 使用项目冻结类型统计
     * @param condition
     * @return
     */
    List<ProjectFreezeTypeVO> queryProjectFreezeType(StatementCondition condition);


    /**
     * 银行冻结类型统计
     * @param condition
     * @return
     */
    List<BankFreezeTypeVO> queryBankFreezeType(StatementCondition condition);


    /**
     * 各仓库分类未用统计表
     * @return
     */
    List<WarehouseUnusedVO> queryWarehouseUnused();

    /**
     * 各仓库银行未用统计表
     * @return
     */
    List<WarehouseBankUnusedVO> queryWarehouseBankUnused();

    /**
     * 渠道存量统计表
     * @return
     */
    List<ChannelStockVO> queryChannelStock();


    /**
     * 停用统计
     * @param condition
     * @return
     */
    List<ChannelDisableTypeVO> queryChannelDisableType(StatementCondition condition);

    /**
     * 使用项目在用统计表
     * @return
     */
    List<ProjectUsedDetailVO> queryProjectUsedDetail();
}
