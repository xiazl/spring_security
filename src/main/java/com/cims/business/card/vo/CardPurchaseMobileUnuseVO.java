package com.cims.business.card.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author belly
 * @create 4/18/19 5:10 PM
 * @description 手机银行未用卡片入库对象 - 用于验证必填字段
 */

@Getter
@Setter
public class CardPurchaseMobileUnuseVO {

    // 类型用户名
    private String typeUserName;
    // 类型登录密码
    private String typeLoginPassword;
    // 类型支付密码
    private String typePayPassword;
    // 手机号
    private String phoneNo;
    // 手机密码
    private String phonePassword;
    // 国际漫游到期时间
    private Date roamingEndDate;
    // 卡片余额
    private BigDecimal balance;
    // 日价格
    private BigDecimal price;

    public void setTypeUserName(String typeUserName) {
        Assert.hasText(typeUserName, "类型用户名字段不能为空");
        this.typeUserName = typeUserName;
    }

    public void setTypeLoginPassword(String typeLoginPassword) {
        Assert.hasText(typeLoginPassword, "类型登陆密码字段不能为空");
        this.typeLoginPassword = typeLoginPassword;
    }

    public void setTypePayPassword(String typePayPassword) {
        Assert.hasText(typePayPassword, "类型支付密码字段不能为空");
        this.typePayPassword = typePayPassword;
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
