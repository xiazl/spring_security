package com.cims;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author baidu
 * @date 2019-04-14 18:09
 * @description TODO
 **/
public class PasswordTest {

    @Test
    public void newPass(){
        String userName = "admin";
        System.out.println("pass: "+new BCryptPasswordEncoder().encode("123456"));
    }
}
