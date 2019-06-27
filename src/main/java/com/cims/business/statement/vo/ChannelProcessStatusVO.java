package com.cims.business.statement.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author belly
 * @create 5/13/19 3:17 PM
 * @description 渠道处理状态统计
 */

@Getter
@Setter
public class ChannelProcessStatusVO {
    // 渠道Id
    private Integer channelId;
    // 渠道名称
    private String channelName;
    // 处理中数量
    private Integer processingCount;
    // 处理中金额
    private BigDecimal processingAmount;
    // 未完成数量
    private Integer unCompleteCount;
    // 未完成金额
    private BigDecimal unCompleteAmount;
    // 按时完成数量
    private Integer onTimeCount;
    // 按时完成金额
    private BigDecimal onTimeAmount;
    // 逾期完成数量
    private Integer overTimeCount ;
    // 逾期完成金额
    private BigDecimal overTimeAmount;
    // 被代扣数量
    private Integer betainedCount;
    // 被代扣金额
    private BigDecimal betainedAmount;
    // 不需处理数量
    private Integer noDealCount;
    // 不需处理金额
    private BigDecimal noDealAmount;
}
