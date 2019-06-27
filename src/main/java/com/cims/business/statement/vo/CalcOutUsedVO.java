package com.cims.business.statement.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author belly
 * @create 4/24/19 10:22 PM
 * @description 计算启用卡应收款对象
 */

@Getter
@Setter
public class CalcOutUsedVO {
    // 项目Id
    private Integer projectId;
    // 计费天数
    private BigDecimal days;
    // 每天价格
    private BigDecimal price;


    // 计费价格
    private BigDecimal totalAmount;

    public BigDecimal getTotalAmount(){
        if (days.compareTo(BigDecimal.ZERO) <= 0){
            return BigDecimal.ZERO;
        }
        return days.multiply(price);
    }
}
