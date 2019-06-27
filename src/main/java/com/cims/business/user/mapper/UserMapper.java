package com.cims.business.user.mapper;

import com.cims.business.user.entity.Role;
import com.cims.business.user.entity.User;
import com.cims.business.user.vo.UserConditionVO;
import com.cims.business.user.vo.UserInfoVO;
import com.cims.business.user.vo.UserRoleVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author baidu
 * @date 2019/4/14 下午12:35
 * @description
 **/

@Mapper
public interface UserMapper {

    /**
     * 用户列表
     * @param conditionVO
     * @return
     */
    List<UserRoleVO> list(@Param("conditionVO") UserConditionVO conditionVO);

    /**
     * 通过Id查询用户
     * @param id
     * @return
     */
    User getUser(@Param("id") Long id, @Param("username") String username);


    /**
     * 新增用户
     * @param user
     * @return
     */
    long addUser(@Param("user") User user);

    /**
     * 插入用户角色关系记录
     * @param userId
     * @param roleId
     */
    void addUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 删除用户角色关系记录
     * @param userId
     */
    void deleteRoleByUserId(@Param("userId") Long userId);


    /**
     * 获取所有角色
     * @return
     */
    List<Role> listRoles();

    /**
     * 用户更新
     * @param user
     * @return
     */
    Integer updateUser(User user);

    /**
     * 用户查询
     * @param userId
     * @return
     */
    UserInfoVO getUserByUserId(@Param("userId") Long userId);

    /**
     * 批量停用
     * @param ids
     */
    void batchDisableUser(@Param("ids") List<Long> ids,@Param("updateUserId")Long updateUserId);

    /**
     * 批量启用
     * @param ids
     */
    void batchEnableUser(@Param("ids") List<Long> ids,@Param("updateUserId")Long updateUserId);

    /**
     * 批量删除
     * @param ids
     */
    void batchDeleteUser(@Param("ids") List<Long> ids,@Param("updateUserId")Long updateUserId);

    /**
     * 修改用户密码
     * @param user
     */
    void updateUserPassword(User user);

}
