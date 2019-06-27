package com.cims.business.user.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author baidu
 * @date 2019/4/14 下午12:37
 * @description 修改密码
 **/

@Getter
@Setter
public class PasswordVO {
    @NotNull
    private Long id;
    @NotNull
    private String password;
    @NotNull
    private String newPassword;
}
