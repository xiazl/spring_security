package com.cims.business.card.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


@Setter
@Getter
public class CardFreeze {
    private Long id;

    private String cardNo;

    private Date freezeDate;

    private Integer freezeType;

    private String reason;

    private BigDecimal amount;

    private Integer processStatus;

    private BigDecimal overHead;

    private BigDecimal paybackAmount;

    private String department;

    private String account;

    private Date finishDate;

    private Integer processMethod;

    private String comment;

    private Long createUserId;

    private Date createTime;

}