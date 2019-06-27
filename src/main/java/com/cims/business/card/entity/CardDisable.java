package com.cims.business.card.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class CardDisable {
    private Long id;

    private String cardNo;

    private Integer disableType;

    private String comment;

    private Date disableDate;

    private Long createUserId;

    private Date createTime;
}