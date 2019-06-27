package com.cims.business.statement.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author belly
 * @create 4/23/19 5:50 PM
 * @description 计算未出卡和已出卡应付款对象
 */

@Getter
@Setter
public class CalcInUnusedAndUsedVO {

    // 渠道Id
    private Integer channelId;
    // 计费天数
    private BigDecimal days;
    // 每天单价
    private BigDecimal price;

    // 应付款
    private BigDecimal totalAmount;

    public BigDecimal getTotalAmount() {
        if (days.compareTo(BigDecimal.ZERO) <= 0){
            return BigDecimal.ZERO;
        }
        return days.multiply(price);
    }
}
