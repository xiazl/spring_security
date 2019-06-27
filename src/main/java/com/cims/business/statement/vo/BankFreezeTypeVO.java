package com.cims.business.statement.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author belly
 * @create 5/13/19 3:42 PM
 * @description 银行冻结类型统计
 */

@Getter
@Setter
public class BankFreezeTypeVO {
    // 银行名称
    private String bankName;
    // 合计数量
    private Integer totalCount;
    // 合计金额
    private BigDecimal totalAmount;
    // 风控数量
    private Integer riskControlCount;
    // 风控金额
    private BigDecimal riskControlAmount;
    // 故障数量
    private Integer malfunctionCount;
    // 故障金额
    private BigDecimal malfunctionAmount;
    // 人为数量
    private Integer artificialCount;
    // 人为金额
    private BigDecimal artificialAmount;
    // 司法数量
    private Integer judicialCount;
    // 司法金额
    private BigDecimal judicialAmount;
    // 疑似司法数量
    private Integer suspectedJusticeCount;
    // 疑似司法金额
    private BigDecimal suspectedJusticeAmount;
}
