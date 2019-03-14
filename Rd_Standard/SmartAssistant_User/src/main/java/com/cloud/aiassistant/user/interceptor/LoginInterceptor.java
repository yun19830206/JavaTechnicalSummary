package com.cloud.aiassistant.user.interceptor;

import com.cloud.aiassistant.pojo.user.User;
import jdk.nashorn.internal.objects.annotations.Property;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登入拦截器
 * @author ChengYun
 * @date 2019/3/10  Vesion 1.0
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    /** 登入URL */
    @Value("${login.url:/login.html}")
    private String loginUrl;

    /** 每个线程的接口开始时间，用于计算接口调用时间 */
    private static ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    /** 客户端请求合法拦截器 */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        startTime.set(System.currentTimeMillis());
        //log.debug("Request Url:{}",request.getRequestURI());
        HttpSession session=request.getSession(true);
        User user=(User) session.getAttribute(User.SESSION_KEY_USER);
        if(null == user || null == user.getId()){
            response.sendRedirect(loginUrl);
            return false ;
        }
        return true ;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        log.debug("Http Request : {}",request.getRequestURI());
        log.debug("    startTime:{}",startTime.get());
        log.debug("    ent  Time:{}",System.currentTimeMillis());
        log.debug("    used Time:{} MS",System.currentTimeMillis()-startTime.get());
        startTime.remove();
    }

}
