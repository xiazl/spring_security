package com.cims.framework.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author baidu
 * @date 2019-04-14 17:14
 * @description 权限认证不通过
 **/

@Service
public class CustomerAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) {

        // 无权限返回403状态码
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}
