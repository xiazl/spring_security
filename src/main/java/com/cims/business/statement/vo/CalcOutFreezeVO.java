package com.cims.business.statement.vo;

import com.cims.common.enums.FreezeTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author belly
 * @create 4/24/19 10:59 PM
 * @description 计算冻结卡应收款对象
 */

@Getter
@Setter
public class CalcOutFreezeVO {

    // 卡号
    private String cardNo;
    // 项目Id
    private Integer projectId;
    // 计费天数
    private BigDecimal days;
    // 每天单价
    private BigDecimal price;
    // 冻结原因
    private Integer freezeType;

    // 应收款
    private BigDecimal totalAmount;

    public BigDecimal getTotalAmount() {
        BigDecimal shouldPay;
        BigDecimal retainage;
        if (days.compareTo(BigDecimal.ZERO) <= 0){
            return BigDecimal.ZERO;
        }

        if (freezeType.equals(FreezeTypeEnum.ARTIFICIAL.getBackValue())){
            shouldPay =  BigDecimal.ZERO;
        } else {
            int remainder = days.intValue() % 30;
            if (remainder > 15){
                BigDecimal realDays = days.divide(new BigDecimal(30), 0, RoundingMode.UP).multiply(new BigDecimal(30));
                shouldPay = realDays.multiply(price);
            } else {
                shouldPay = days.multiply(price);
            }
        }
        return shouldPay;
    }
}
