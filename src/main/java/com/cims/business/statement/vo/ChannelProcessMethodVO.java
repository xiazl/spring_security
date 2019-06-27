package com.cims.business.statement.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author belly
 * @create 5/13/19 2:11 PM
 * @description 渠道处理方式统计列表
 */

@Getter
@Setter
public class ChannelProcessMethodVO {
    // 渠道Id
    private Integer channelId;
    // 渠道名称
    private String channelName;
    // 总张数
    private Integer totalCount;
    // 总金额
    private BigDecimal totalAmount;
    // 自主代扣张数
    private Integer withholdingCount;
    // 自主代扣金额
    private BigDecimal withholdingAmount;
    // 渠道处理张数
    private Integer channelDealCount;
    // 渠道处理金额
    private BigDecimal channelDealAmount;
    // 自行解冻张数
    private Integer selfThawingCount;
    // 自行解冻金额
    private BigDecimal selfThawingAmount;
    // 卡费扣除张数
    private Integer cardFeeDeductionCount;
    // 卡费扣除金额
    private BigDecimal cardFeeDeductionAmount;

}
