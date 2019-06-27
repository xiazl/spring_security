package com.cims.business.statement.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author belly
 * @create 5/12/19 5:34 PM
 * @description 计算问题卡应付款对象
 */

@Getter
@Setter
public class CalcInProblemVO {
    // 卡号
    private String cardNo;
    // 渠道Id
    private Integer channelId;
    // 计费天数
    private BigDecimal days;
    // 入库总天数
    private Integer totalDays;
    // 每天单价
    private BigDecimal price;

    // 应付款
    private BigDecimal totalAmount;

    public BigDecimal getTotalAmount() {
        if (days.compareTo(BigDecimal.ZERO) <= 0 || price.compareTo(BigDecimal.ZERO) == 0){
            return BigDecimal.ZERO;
        }
        return days.multiply(price);
    }
}
