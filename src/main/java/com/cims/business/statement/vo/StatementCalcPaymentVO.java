package com.cims.business.statement.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author belly
 * @create 4/23/19 5:46 PM
 * @description 渠道应付款
 */

@Getter
@Setter
public class StatementCalcPaymentVO {
    // 渠道Id
    private Integer channelId;
    // 应付款
    private BigDecimal amount;

    // 渠道名称
    private String channelName;
}
