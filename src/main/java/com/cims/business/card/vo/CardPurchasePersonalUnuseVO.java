package com.cims.business.card.vo;

import com.cims.common.excel.annotation.ExcelTitle;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author belly
 * @create 4/18/19 5:10 PM
 * @description 个人银行未用卡片入库对象 - 用于验证必填字段
 */

@Getter
@Setter
public class CardPurchasePersonalUnuseVO extends CardPurchaseBaseVO{

    // 登陆密码
    private String loginPassword;
    // 支付密码
    private String payPassword;
    // U盾密码
    private String ukeyPassword;
    // 手机号
    private String phoneNo;
    // 手机密码
    private String phonePassword;
    //国际漫游到期时间
    private Date roamingEndDate;
    // 卡片余额
    private BigDecimal balance;
    // 日价格
    private BigDecimal price;

    public void setLoginPassword(String loginPassword) {
        Assert.hasText(loginPassword, "登陆密码字段不能为空");
        this.loginPassword = loginPassword;
    }

    public void setPayPassword(String payPassword) {
        Assert.hasText(payPassword, "支付密码字段不能为空");
        this.payPassword = payPassword;
    }

    public void setUkeyPassword(String ukeyPassword) {
        Assert.hasText(ukeyPassword, "U盾密码字段不能为空");
        this.ukeyPassword = ukeyPassword;
    }

    public void setPhoneNo(String phoneNo) {
        Assert.hasText(phoneNo, "手机号码字段不能为空");
        this.phoneNo = phoneNo;
    }

    public void setPhonePassword(String phonePassword) {
        Assert.hasText(phonePassword, "手机密码字段不能为空");
        this.phonePassword = phonePassword;
    }

    public void setRoamingEndDate(Date roamingEndDate) {
        Assert.notNull(roamingEndDate, "国际漫游到期日字段不能为空");
        this.roamingEndDate = roamingEndDate;
    }

    public void setBalance(BigDecimal balance) {
        Assert.notNull(balance, "余额字段不能为空");
        this.balance = balance;
    }

    public void setPrice(BigDecimal price) {
        Assert.notNull(balance, "日价格字段不能为空");
        this.price = price;
    }
}
