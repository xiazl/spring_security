package com.cims.business.card.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Card {
    private Long id;

    private Integer mainType;

    private String bankName;

    private String owner;

    private String cardNo;

    private String province;

    private String city;

    private String branchName;

    private String idNo;

    private String loginPassword;

    private String payPassword;

    private String ukeyPassword;

    private String typeUserName;

    private String typeLoginPassword;

    private String typePayPassword;

    private Integer secondaryType;

    private Integer status;

    private String phoneNo;

    private String phonePassword;

    private Date roamingEndDate;

    private String comment;

    private Integer warehouseId;

    private Long createUserId;

    private Date createTime;

    private Long updateUserId;

    private Date updateTime;

}