package com.cims.business.user.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * @author baidu
 * @date 2019/4/14 下午12:20
 * @description 用户模块
 **/

@Setter
@Getter
public class User {

    private Long id;

    private String username;

    private String password;

    private String nickname;

    private byte isDisable;

    private byte isDelete;

    private Date createTime;

    private Date updateTime;

    private Long createUserId;

    private Long updateUserId;
}
