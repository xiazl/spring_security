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
public class CardPurchaseAlipayUnuseVO extends CardPurchasePersonalUnuseVO{

    // 类型用户名
    private String typeUserName;
    // 类型登录密码
    private String typeLoginPassword;
    // 类型支付密码
    private String typePayPassword;
    // 备注、支付宝密保问题及答案
    private String comment;

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

    public void setComment(String comment) {
        Assert.hasText(comment, "备注字段不能为空");
        this.comment = comment;
    }
}
