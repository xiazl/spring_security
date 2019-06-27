package com.cims.business.user.vo;

import com.cims.business.user.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author baidu
 * @date 2019-04-16 21:31
 * @description TODO
 **/

@Setter
@Getter
public class UserInfoVO {
    private Long id;

    private String username;

    private String nickname;

    private List<Role> roles;

    private List<Long> warehouseIds;
}
