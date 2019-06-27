package com.cims.framework.security;

import com.alibaba.fastjson.JSONObject;
import com.cims.framework.response.ResponseData;
import com.cims.framework.response.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author baidu
 * @date 2019-04-14 17:12
 * @description 登录成功处理
 **/

@Component
public class CustomerAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        PrintWriter out = httpServletResponse.getWriter();
        Result result = ResponseData.success(userDetails);
        out.write(JSONObject.toJSONString(result));
        out.flush();
        out.close();
    }
}
