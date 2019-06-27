package com.cims.business.statement.vo;

import com.cims.common.enums.FreezeTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author belly
 * @create 4/23/19 7:56 PM
 * @description 计算冻结卡应付款对象
 */


@Getter
@Setter
public class CalcInFreezeVO {

    // 卡号
    private String cardNo;
    // 渠道Id
    private Integer channelId;
    // 计费天数
    private BigDecimal days;
    // 每天单价
    private BigDecimal price;
    // 冻结原因
    private Integer freezeType;
    // 冻结金额
    private BigDecimal freezeAmount;
    // 处理状态
    private Integer processStatus;

    // 应付款
    private BigDecimal totalAmount;

    public BigDecimal getTotalAmount() {
        BigDecimal shouldPay;
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

        if (processStatus == null || freezeAmount == null){
            return shouldPay;
        }
        BigDecimal retainage;
        switch (processStatus){
            case 1:     // 处理中
                retainage = freezeAmount.multiply(new BigDecimal(0.15f));
                break;
            case 2:     // 未完成
                retainage = freezeAmount;
                break;
            case 5:     // 被代扣
                retainage = freezeAmount.multiply(new BigDecimal(0.5f));
                break;
            default:
                retainage = BigDecimal.ZERO;
        }
        return shouldPay.subtract(retainage);
    }
}
