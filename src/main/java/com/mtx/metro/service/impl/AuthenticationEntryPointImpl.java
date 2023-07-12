package com.mtx.metro.service.impl;

import com.alibaba.fastjson.JSON;
import com.mtx.metro.utils.Result;
import com.mtx.metro.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.mtx.metro.constants.CodeConstants.CODE_PERMISSION_ERROR;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String json = JSON.toJSONString(Result.error(CODE_PERMISSION_ERROR,"认证失败请重新登录"));
        WebUtils.renderString(response,json);
    }
}
