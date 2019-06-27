package com.cims.business.user.controller;

import com.cims.business.user.entity.Role;
import com.cims.business.user.entity.User;
import com.cims.business.user.service.UserService;
import com.cims.business.user.vo.*;
import com.cims.common.Constants;
import com.cims.common.context.UserContext;
import com.cims.common.util.RSAUtils;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;
import com.cims.framework.response.ResponseData;
import com.cims.framework.response.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;


/**
 * @author baidu
 * @date 2019/4/14 下午1:59
 * @description 用户模块
 **/

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 用户列表
     * @return
     * @throws BizException
     */
    @RolesAllowed({Constants.ADMIN})
    @RequestMapping(value = "/list")
    public Result list(UserConditionVO conditionVO, PageBean pageBean) {
        PageDataResult<UserRoleVO> userList = userService.list(conditionVO,pageBean);
        return ResponseData.success(userList);
    }

    /**
     *  获取角色列表
     * @return
     */
    @RequestMapping(value = "/listRole", method = RequestMethod.GET)
    public Result listRoles() {
        List<Role> roles = userService.listRoles();

        return ResponseData.success(roles);
    }

    /**
     * 添加用户
     * @param userVO
     * @return
     */
    @RolesAllowed({Constants.ADMIN})
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result saveUser(@Valid @RequestBody UserVO userVO) throws BizException {
        userService.addUser(userVO);

        return ResponseData.success();
    }

    /**
     * 修改用户
     * @param userVO
     * @return
     * @throws BizException
     */
    @RolesAllowed({Constants.ADMIN})
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result editUserSave(@RequestBody UserVO userVO) throws BizException {
        if(userVO.getId() == null){
            throw new BizException("id不能为空");
        }

        if(CollectionUtils.isEmpty(userVO.getRoleIds())){
            throw new BizException("roleIds不能为空");
        }
        userService.updateUser(userVO);
        return ResponseData.success();

    }

    /**
     * 删除用户
     * @param ids
     * @return
     */
    @RolesAllowed({Constants.ADMIN})
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result deleteUsers(@RequestParam(value = "ids")List<Long> ids) {
        userService.deleteBatchUser(ids);
        return ResponseData.success();

    }

    /**
     * 停用
     * @param ids
     * @return
     */
    @RolesAllowed({Constants.ADMIN})
    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    public Result disableUsers(@RequestParam(value = "ids")List<Long> ids) {
        userService.disableBatchUser(ids);
        return ResponseData.success();

    }

    /**
     * 启用
     * @param ids
     * @return
     */
    @RolesAllowed({Constants.ADMIN})
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public Result enableUsers(@RequestParam(value = "ids")List<Long> ids) {
        userService.enableBatchUser(ids);
        return ResponseData.success();

    }

    /**
     * 获取用户信息
     * @return
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public Result getUserInfo() {
        Long userId = UserContext.getUserId();
        UserInfoVO user = userService.getUserByUserId(userId);
        return ResponseData.success(user);
    }

    /**
     * 修改密码
     * @param passwordVO
     * @return
     * @throws BizException
     */
    @RequestMapping(value = "/editPassword", method = RequestMethod.POST)
    public Result editPassword(@RequestBody @Valid PasswordVO passwordVO) throws BizException {
        User user = userService.getUserById(passwordVO.getId());
        if (!passwordEncoder.matches(passwordVO.getPassword(), user.getPassword())) {
            throw new BizException("旧密码错误");
        }

        String newPassword = RSAUtils.getDecrypted(passwordVO.getNewPassword());
        String secretKey = passwordEncoder.encode(newPassword);
        user.setPassword(secretKey);
        userService.updateUserPassword(user);

        return ResponseData.success();
    }

    /**
     * 重置密码
     * @param passwordVO
     * @return
     * @throws BizException
     */
    @RolesAllowed({Constants.ADMIN})
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public Result resetPassword(@RequestBody PasswordVO passwordVO) throws BizException {
        userService.resetPassword(passwordVO.getId());
        return ResponseData.success();
    }

}
