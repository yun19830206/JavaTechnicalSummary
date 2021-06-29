package com.cloud.stagging.core.security.handler;

import com.cloud.stagging.core.initbean.SpringContextHolder;
import com.cloud.stagging.core.security.config.JWTConfig;
import com.cloud.stagging.core.security.pojo.AuthUserDetails;
import com.cloud.stagging.core.security.utils.JWTTokenUtil;
import com.cloud.stagging.core.utils.AjaxJsonResponseUtil;
import com.cloud.stagging.pojo.common.AjaxResponse;
import com.cloud.stagging.pojo.user.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 编写登录成功处理类
 * @author ChengYun
 * @date 2020/4/12  Vesion 1.0
 */
@Slf4j
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //1:普通登入方式：获得登入成功对象
        AuthUserDetails loginUser =  (AuthUserDetails) authentication.getPrincipal();

        //2:附带AccessToken方式登入
        JWTConfig jwtConfig = SpringContextHolder.getBean(JWTConfig.class);
        String token = JWTTokenUtil.createAccessToken(loginUser);
        token = jwtConfig.getTokenPrefix() + token;
        loginUser.setAccessToken(token);

        //3:返回登入成功 AuthUserDetails 对象
        log.info("用户:{},登入成功,执行完成登入成功处理类。",loginUser.getUsername());
        AjaxJsonResponseUtil.responseJson(response,AjaxResponse.success(loginUser,"登录成功"));
    }
}
