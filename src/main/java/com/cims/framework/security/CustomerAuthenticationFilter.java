package com.cims.framework.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author baidu
 * @date 2019/4/16 下午1:02
 * @description
 **/

@Component
public class CustomerAuthenticationFilter implements Filter {
    @Autowired
    private CustomerAuthenticationFailureHandler failureHandler;

    private static final String LOGIN_URL = "/login";		//登录页面地址

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;

        String verification = request.getParameter("verification_code");
        if ("POST".equalsIgnoreCase(req.getMethod()) && LOGIN_URL.equals(req.getServletPath())) {
            if (StringUtils.isEmpty(verification)) {
                failureHandler.onAuthenticationFailure(req,rep,new AuthenticationServiceException("验证码不能为空"));
                return;
            } else if (!verification.equalsIgnoreCase("1234")){
                failureHandler.onAuthenticationFailure(req,rep,new AuthenticationServiceException("验证码错误"));
                return;
            }
        }

        chain.doFilter(request, response);

    }

}
