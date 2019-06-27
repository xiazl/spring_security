package com.cims.business.card.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class CardReturn {
    private Long id;

    private String cardNo;

    private BigDecimal verifyAmount;

    private BigDecimal diffAmount;

    private Date returnDate;

    private String verifyUser;

    private String comment;

    private Long createUserId;

    private Date createTime;

}