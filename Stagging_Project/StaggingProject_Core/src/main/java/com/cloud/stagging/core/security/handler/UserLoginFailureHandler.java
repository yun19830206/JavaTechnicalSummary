package com.cloud.stagging.core.security.handler;

import com.cloud.stagging.core.utils.AjaxJsonResponseUtil;
import com.cloud.stagging.pojo.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 编写登录失败处理类
 * @author ChengYun
 * @date 2020/4/12  Vesion 1.0
 */
@Component
@Slf4j
public class UserLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        // 这些对于操作的处理类可以根据不同异常进行不同处理
        if (e instanceof UsernameNotFoundException){
            log.info("【登录失败】"+e.getMessage());
            AjaxJsonResponseUtil.responseJson(response, AjaxResponse.failed(null,"用户名不存在"));
        }
        if (e instanceof LockedException){
            log.info("【登录失败】"+e.getMessage());
            AjaxJsonResponseUtil.responseJson(response, AjaxResponse.failed(null,"用户被冻结"));
        }
        if (e instanceof BadCredentialsException){
            log.info("【登录失败】"+e.getMessage());
            AjaxJsonResponseUtil.responseJson(response, AjaxResponse.failed(null,"用户名密码不正确"));
        }
        AjaxJsonResponseUtil.responseJson(response, AjaxResponse.failed(null,"登录失败"));
    }
}
