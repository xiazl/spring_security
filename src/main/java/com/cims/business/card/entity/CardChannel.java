package com.cims.business.card.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
public class CardChannel {
    private Integer id;

    private String channelName;

    private Integer isDelete;

    private Long createUserId;

    private Date createTime;

}