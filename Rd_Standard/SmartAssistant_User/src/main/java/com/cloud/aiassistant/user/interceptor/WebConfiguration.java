package com.cloud.aiassistant.user.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web相关配置类
 * @author ChengYun
 * @date 2019/3/10  Vesion 1.0
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    /** 登入URL */
    @Value("${login.url:/login.html}")
    private String loginUrl;

    @Autowired
    private LoginInterceptor loginInterceptor ;

    /**
     * 重写添加拦截器方法并添加配置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/aiassistant/user/wx/*","/aiassistant/user/login/*","/error",loginUrl);
    }
}
