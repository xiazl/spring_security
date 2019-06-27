package com.cims.business.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author baidu
 * @date 2019/4/14 下午12:30
 * @description 角色
 **/

@Mapper
public interface RoleMapper {
    /**
     * 查询角色
     * @param userId
     * @return 角色
     */
    List<String> getRoleByUserId(Long userId);

}
