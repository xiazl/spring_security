package com.cims.business.card.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


@Setter
@Getter
public class CardMaintain {
    private Long id;

    private String cardNo;

    private Date date;

    private Integer status;

    private Integer disableType;

    private Long freezeUser;

    private Integer freezeType;

    private String reason;

    private BigDecimal freezeAmount;

    private Integer processStatus;

    private BigDecimal overHead;

    private BigDecimal paybackAmount;

    private String department;

    private String account;

    private Date finishDate;

    private Integer processMethod;

    private String problemDesc;

    private String comment;

    private Long createUserId;

    private Date createTime;

}