package com.cims.business.card.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author baidu
 * @date 2019-04-17 21:20
 * @description TODO
 **/

@Setter
@Getter
public class UserWarehouse {
    private Long id;

    private Long userId;

    private Long warehouseId;

    private Integer isDelete;

    private Long createUserId;

    private Date createTime;
}
