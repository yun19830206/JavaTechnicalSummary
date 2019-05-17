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
    @Value("${login.url:/#/login}")
    private String loginUrl;

    @Autowired
    private LoginInterceptor loginInterceptor ;

    /**
     * 重写添加拦截器方法并添加配置拦截器.
     * /demo/*  代表匹配/demo/下面的第一层目录，无法匹配下面的多级目录。
     * /demo/** 代表匹配/demo/下面的第一层目录 和 下面任何级子目录。
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(loginUrl)
                .excludePathPatterns("/error")
                .excludePathPatterns("/aiassistant/user/wx/*")
                .excludePathPatterns("/aiassistant/user/login/*")
                .excludePathPatterns("/demo/web/cache/**");
    }
}
