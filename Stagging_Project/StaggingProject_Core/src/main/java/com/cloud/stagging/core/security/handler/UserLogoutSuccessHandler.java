package com.cloud.stagging.core.security.handler;

import com.cloud.stagging.core.security.pojo.AuthUserDetails;
import com.cloud.stagging.core.utils.AjaxJsonResponseUtil;
import com.cloud.stagging.pojo.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 编写登出成功处理类
 * @author ChengYun
 * @date 2020/4/12  Vesion 1.0
 */
@Slf4j
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //登出成功务必 SpringSecurity让Session失效
        SecurityContextHolder.clearContext();
        AuthUserDetails loginUser =  (AuthUserDetails) authentication.getPrincipal();
        log.info("用户:{},登出成功,执行完 登出成功处理类处理类。",loginUser.getUsername());
        AjaxJsonResponseUtil.responseJson(response, AjaxResponse.success(null,"登出成功"));
    }
}
