package com.cims.framework.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsUtils;


/**
 * @author baidu
 * @date 2019/4/14 下午3:55
 * @description ${TODO}
 **/

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static Logger LOGGER = LoggerFactory.getLogger(SecurityConfiguration.class);

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private CustomerAccessDeniedHandler deniedHandler;
    @Autowired
    private CustomerAuthenticationFailureHandler failureHandler;
    @Autowired
    private CustomerAuthenticationSuccessHandler successHandler;
    @Autowired
    private CustomerLogoutHandler customerLogoutHandler;
    @Autowired
    private CustomerAuthenticationFilter authenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()     // 配置安全策略
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .anyRequest().authenticated()   //其余的所有请求都需要验证
                .and()
                .logout()
                .logoutUrl("/logout")            // 登出地址
                .logoutSuccessHandler(customerLogoutHandler)
                .permitAll()
                .and()
//                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class) // 验证码处理
                .formLogin()
                .loginProcessingUrl("/login")   // 自定义的登录处理
                .successHandler(successHandler)
                .failureHandler(failureHandler)
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(deniedHandler).authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new RBCryptPasswordEncoder();
        return encoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);  // user验证
    }

}
