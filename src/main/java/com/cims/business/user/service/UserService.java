package com.cims.business.user.service;

import com.cims.business.user.entity.Role;
import com.cims.business.user.entity.User;
import com.cims.business.user.vo.UserConditionVO;
import com.cims.business.user.vo.UserInfoVO;
import com.cims.business.user.vo.UserRoleVO;
import com.cims.business.user.vo.UserVO;
import com.cims.framework.exception.BizException;
import com.cims.framework.page.PageBean;
import com.cims.framework.page.PageDataResult;

import java.util.List;

/**
 * @author baidu
 * @date 2019/4/14 下午12:50
 * @description
 **/

public interface UserService {
    /**
     * 用户列表
     * @param conditionVO
     * @param pageBean
     * @return
     */
    PageDataResult<UserRoleVO> list(UserConditionVO conditionVO, PageBean pageBean);

    /**
     * 用户查询
     * @param userId
     * @return
     */
    User getUserById(Long userId);
    
    /**
     * 用户查询
     * @param userName
     * @return
     */
    User getUserByUsername(String userName);

    /**
     * 添加用户
     * @param userVO
     */
     void addUser(UserVO userVO) throws BizException;

    /**
     * 查询所有角色
     * @return
     */
     List<Role> listRoles();

    /**
     * 获取用户信息
     * @param userId
     * @return
     * @
     */
    UserInfoVO getUserByUserId(Long userId) ;

    /**
     * 批量停用 用户
     * @param ids
     * @
     */
    void disableBatchUser(List<Long> ids);

    /**
     * 批量启用 用户
     * @param ids
     * @
     */
    void enableBatchUser(List<Long> ids);

    /**
     * 批量删除用户
     * @param ids
     * @
     */
    void deleteBatchUser(List<Long> ids);

    /**
     * 更新用户信息
     * @param userVO
     */
    void updateUser(UserVO userVO) throws BizException;


    /**
     * 更新密码
     * @param user
     * @
     */
    void updateUserPassword(User user) ;

    /**
     * 重置密码
     * @param id user ID
     * @
     */
    void resetPassword(Long id) throws BizException;


}
