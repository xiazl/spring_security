package com.cims.business.card.vo;

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
public class CardPurchaseBaseVO {

    // 类型
    private Integer mainType;
    // 银行名称
    private String bankName;
    // 户名
    private String owner;
    // 卡号
    private String cardNo;
    // 身份证号
    private String idNo;
    // 分类
    private Integer secondaryType;
    // 状态文本
    private Integer status;
    // 采购日期
    private Date purchaseDate;
    // ckr信息
    private String ckr;
    // 日价格
    private BigDecimal price;

    public void setMainTypeStr(Integer mainType) {
        Assert.notNull(mainType, "类型字段不能为空");
        this.mainType = mainType;
    }

    public void setBankName(String bankName) {
        Assert.hasText(bankName, "银行字段不能为空");
        this.bankName = bankName;
    }

    public void setOwner(String owner) {
        Assert.hasText(owner, "名字字段不能为空");
        this.owner = owner;
    }

    public void setCardNo(String cardNo) {
        Assert.hasText(cardNo, "卡号字段不能为空");
        this.cardNo = cardNo;
    }

    public void setIdNo(String idNo) {
        Assert.hasText(idNo, "身份证字段不能为空");
        this.idNo = idNo;
    }

    public void setSecondaryTypeStr(Integer secondaryType) {
        Assert.notNull(secondaryType, "分类字段不能为空");
        this.secondaryType = secondaryType;
    }

    public void setStatusStr(Integer status) {
        Assert.notNull(status, "状态字段不能为空");
        this.status = status;
    }

    public void setPurchaseDate(Date purchaseDate) {
        Assert.notNull(purchaseDate, "来卡日期字段不能为空");
        this.purchaseDate = purchaseDate;
    }

    public void setCkr(String ckr) {
        Assert.hasText(ckr, "ckr字段不能为空");
        this.ckr = ckr;
    }
    public void setPrice(BigDecimal price){
        Assert.notNull(price, "日价格不能为空");
        this.price = price;
    }
}
