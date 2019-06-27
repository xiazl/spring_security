package com.cims.business.user.vo;

import com.cims.business.user.entity.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

/**
 * @author baidu
 * @date 2019/4/14 下午12:40
 * @description
 **/

@Setter
@Getter
public class UserRoleVO {

    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String nickname;
    @NotEmpty
    private String password;

    private Integer isDisable;

    private Date createTime;

    private Date updateTime;

    private Long createUserId;

    private Long updateUserId;
    @NotEmpty
    private List<Role> roles;

    private String createUser;

    private String updateUser;

    private List<Long> warehouseIds;
}
