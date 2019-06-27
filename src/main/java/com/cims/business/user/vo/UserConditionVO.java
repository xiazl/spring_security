package com.cims.business.user.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author baidu
 * @date 2019/4/14 下午12:34
 * @description
 **/

@Setter
@Getter
public class UserConditionVO {
    private String username;
    private String nickname;
    private Integer roleId;
    private String isDisable;
}
