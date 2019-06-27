package com.cims.common.context;

import com.cims.framework.security.CustomerUser;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * @author baidu
 * @date 2019-04-15 13:26
 * @description 用户信息获取
 **/

public class UserContext {

	/**
	 * 获取用户Id
	 * */
	public static Long getUserId(){
        return getUser().getId();
	}

	/**
	 * 获取用户名称
	 * */
	public static String getUserName(){
		return getUser().getUsername();
	}
	
	/**
	 * 获取用户类型
	 * */
	public static String getNickname(){
        return getUser().getNickname();
	}

	/**
	 * 获取用户信息
	 * @Return BaseUser
	 * */
	public static CustomerUser getUser(){
        CustomerUser user = (CustomerUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }

    /**
     * 用户权限判断
     * @Return boot值
     * */
    public static Boolean hasRoles(String... role){
        List<String> roles = UserContext.getUser().getRoles();
        for (String s : role){
            if (roles.contains(s)) {
                return true;
            }
        }
        return false;
    }
}
