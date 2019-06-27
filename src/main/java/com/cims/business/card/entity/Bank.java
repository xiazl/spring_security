package com.cims.business.card.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author baidu
 * @date 2019-04-17 14:56
 * @description 银行实体类
 **/

@Setter
@Getter
public class Bank {
    private Long id;
    private String bankName;
    private Long createUserId;
    private Date createTime;
}
