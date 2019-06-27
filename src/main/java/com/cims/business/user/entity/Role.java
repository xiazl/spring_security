package com.cims.business.user.entity;


import lombok.Getter;
import lombok.Setter;

/**
 * @author baidu
 * @date 2019/4/14 下午12:30
 * @description 用户模块
 **/

@Setter
@Getter
public class Role {

    // 主键id
    private Long id;
    // 角色编码
    private String roleCode;
    // 角色名称
    private String roleName;
    // 角色描述
    private String roleDes;

}
