package com.cims.business.settlement.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class CardSettlement {
    private Long id;

    private String cardNo;

    private Integer status;

    private Date startDate;

    private Date stopDate;

    private Integer days;

    private Integer type;

    private BigDecimal price;

    private BigDecimal amount;

    private BigDecimal deductAmount;

    private BigDecimal realAmount;

    private Date paymentDate;

    private Long createUserId;

    private Date createTime;

}