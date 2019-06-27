package com.cims.framework.security;

import com.alibaba.fastjson.JSONObject;
import com.cims.framework.response.ResponseData;
import com.cims.framework.response.Result;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author baidu
 * @date 2019-04-14 17:13
 * @description 登录失败处理
 **/

@Component
public class CustomerAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        String errorMsg;
        if (e instanceof BadCredentialsException) {
            errorMsg = "密码错误";
        } else {
            errorMsg = e.getMessage();
        }

        PrintWriter out = httpServletResponse.getWriter();
        Result result = ResponseData.failed(errorMsg);
        out.write(JSONObject.toJSONString(result));
        out.flush();
        out.close();
    }
}
