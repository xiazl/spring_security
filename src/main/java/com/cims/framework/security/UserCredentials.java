package com.cims.framework.security;

import com.cims.business.user.entity.User;
import com.cims.business.user.mapper.RoleMapper;
import com.cims.business.user.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author baidu
 * @date 2019/4/14 下午3:55
 * @description ${TODO}
 **/

@Component
public class UserCredentials implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCredentials.class);

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public CustomerUser loadUserByUsername(String username) throws UsernameNotFoundException {
        User entity = userMapper.getUser(null,username);
        if(entity == null){
            throw new ProviderNotFoundException("用户不存在");
        }

        List<String> roleString = roleMapper.getRoleByUserId(entity.getId());
        CustomerUser user = new CustomerUser(entity.getId(),username, entity.getNickname(),entity.getPassword(), AuthorityUtils.
                createAuthorityList(roleString.toArray(new String[]{})));

        return user;
    }
}
