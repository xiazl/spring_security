package com.cims.business.user.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;


/**
 * @author baidu
 * @date 2019/4/14 下午12:41
 * @description
 **/

@Setter
@Getter
public class UserVO {
    private Long id;  // 用户id
    @NotEmpty
    private List<Long> roleIds;     // 角色id

    private List<Long> warehouseIds; // 仓库id
    @NotEmpty
    private String username;
    @NotEmpty
    private String nickname;
    @NotEmpty
    private String password;

}
