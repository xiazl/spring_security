package com.cims.business.card.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class CardPurchase {
    // 主键id
    private Long id;
    // 卡片id
    private String cardNo;
    // 渠道
    private Integer channel;
    // 卡片余额
    private BigDecimal balance;
    // ckr信息
    private String ckr;
    // 每日成本价格
    private BigDecimal price;
    // 采购日期
    private Date purchaseDate;
    // 创建用户id
    private Long createUserId;
    // 创建日期
    private Date createTime;

}