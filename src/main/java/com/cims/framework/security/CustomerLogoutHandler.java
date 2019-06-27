package com.cims.framework.security;

import com.alibaba.fastjson.JSONObject;
import com.cims.framework.response.ResponseData;
import com.cims.framework.response.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author baidu
 * @date 2019-04-14 17:17
 * @description 登出处理
 **/

@Component
public class CustomerLogoutHandler implements LogoutSuccessHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerLogoutHandler.class);

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        Result result = ResponseData.success();
        out.write(JSONObject.toJSONString(result));
        out.flush();
        out.close();
    }
}
