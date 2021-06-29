package com.cloud.stagging.core.security.config;

import com.cloud.stagging.core.initbean.SpringContextHolder;
import com.cloud.stagging.core.security.filter.JWTAuthenticationTokenFilter;
import com.cloud.stagging.core.security.handler.*;
import com.cloud.stagging.core.security.provider.UserAuthenticationProvider;
import com.cloud.stagging.core.security.provider.UserPermissionEvaluator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

/**
 * 编写SpringSecurity核心配置类.
 * EnableGlobalMethodSecurity 开启权限注解,默认是关闭的
 * @author ChengYun
 * @date 2020/4/13  Vesion 1.0
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    /** 自定义登录成功处理器 */
    private final UserLoginSuccessHandler userLoginSuccessHandler;
    /** 自定义登录失败处理器 */
    private final UserLoginFailureHandler userLoginFailureHandler;
    /** 自定义注销成功处理器 */
    private final UserLogoutSuccessHandler userLogoutSuccessHandler;
    /** 自定义暂无权限处理器 */
    private final UserAuthAccessDeniedHandler userAuthAccessDeniedHandler;
    /** 自定义未登录的处理器 */
    private final UserAuthenticationEntryPointHandler userAuthenticationEntryPointHandler;
    /** 自定义登录逻辑验证器 */
    private final UserAuthenticationProvider userAuthenticationProvider;
    /** 注入JWTConfig */
    private final JWTConfig jwtConfig ;

    @Autowired
    public SpringSecurityConfig(JWTConfig jwtConfig, UserAuthenticationProvider userAuthenticationProvider, UserAuthenticationEntryPointHandler userAuthenticationEntryPointHandler, UserAuthAccessDeniedHandler userAuthAccessDeniedHandler, UserLogoutSuccessHandler userLogoutSuccessHandler, UserLoginFailureHandler userLoginFailureHandler, UserLoginSuccessHandler userLoginSuccessHandler) {
        this.jwtConfig = jwtConfig;
        this.userAuthenticationProvider = userAuthenticationProvider;
        this.userAuthenticationEntryPointHandler = userAuthenticationEntryPointHandler;
        this.userAuthAccessDeniedHandler = userAuthAccessDeniedHandler;
        this.userLogoutSuccessHandler = userLogoutSuccessHandler;
        this.userLoginFailureHandler = userLoginFailureHandler;
        this.userLoginSuccessHandler = userLoginSuccessHandler;
    }


    /**
     * 加密方式Bean
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 注入自定义PermissionEvaluator
     */
    @Bean
    public DefaultWebSecurityExpressionHandler userSecurityExpressionHandler(){
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(SpringContextHolder.getBean(UserPermissionEvaluator.class));
        return handler;
    }

    /**
     * 配置登录验证逻辑
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        //这里启用我们自己的登陆验证逻辑
        auth.authenticationProvider(userAuthenticationProvider);
    }
    /**
     * 配置security的控制逻辑
     * @param  http 请求
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //不进行权限验证的请求或资源(从配置文件中读取)
                .antMatchers(jwtConfig.getAntMatchers().split(",")).permitAll()
                //其他的需要登陆后才能访问
                .anyRequest().authenticated()
                .and()
                //配置未登录自定义处理类
                .httpBasic().authenticationEntryPoint(userAuthenticationEntryPointHandler)
                .and()
                //配置登录地址
                .formLogin()
                .loginProcessingUrl("/login/userLogin")
                //配置登录成功自定义处理类
                .successHandler(userLoginSuccessHandler)
                //配置登录失败自定义处理类
                .failureHandler(userLoginFailureHandler)
                .and()
                //配置登出地址
                .logout()
                .logoutUrl("/login/userLogout")
                //配置用户登出自定义处理类
                .logoutSuccessHandler(userLogoutSuccessHandler)
                .and()
                //配置没有权限自定义处理类
                .exceptionHandling().accessDeniedHandler(userAuthAccessDeniedHandler)
                .and()
                // 开启跨域
                .cors()
                .and()
                // 取消跨站请求伪造防护
                .csrf().disable();
        // 基于Token不需要session(如果是用普通账号密码登入，此处注释掉即可)
        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 禁用缓存
        http.headers().cacheControl();
        // 添加JWT过滤器
        http.addFilter(new JWTAuthenticationTokenFilter(authenticationManager()));
        log.info("SpringSecurity核心配置类 创建成功。");
    }

}
