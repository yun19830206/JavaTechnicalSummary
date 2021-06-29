package com.cloud.stagging.core.security.handler;

import com.cloud.stagging.core.utils.AjaxJsonResponseUtil;
import com.cloud.stagging.pojo.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户未登录处理类
 * @author ChengYun
 * @date 2020/4/12  Vesion 1.0
 */
@Slf4j
@Component
public class UserAuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.info("执行到 用户未登录处理类。");
        AjaxJsonResponseUtil.responseJson(response, AjaxResponse.noLogin());
    }
}
