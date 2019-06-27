package com.cims.framework.security;

import com.cims.common.util.RSAUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author baidu
 * @date 2019-04-15 16:00
 * @description spring security 密码验证之前，RSA解密
 **/
public class RBCryptPasswordEncoder extends BCryptPasswordEncoder {

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String password = RSAUtils.getDecrypted(String.valueOf(rawPassword)); // RSA 解密
        return super.matches(password,encodedPassword);
    }

}
