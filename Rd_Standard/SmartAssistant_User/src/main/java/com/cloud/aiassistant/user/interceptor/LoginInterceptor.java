package com.cloud.aiassistant.user.interceptor;

import com.cloud.aiassistant.core.wxsdk.WxApiComponent;
import com.cloud.aiassistant.pojo.common.CommonSuccessOrFail;
import com.cloud.aiassistant.pojo.user.User;
import com.cloud.aiassistant.user.service.UserService;
import jdk.nashorn.internal.objects.annotations.Property;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    /** 用于微信交互的appid */
    @Value("${wx.appid:wxa9b9e33e4f8dfbde}")
    private String wxAppId;

    /** 用于微信交互的AppSecret */
    @Value("${wx.appSecret:67500831dd15952c8167cef47d5a3bd4}")
    private String wxAppSecret;

    /** 登入URL */
    @Value("${login.url:/login.html}")
    private String loginUrl;

    /** 每个线程的接口开始时间，用于计算接口调用时间 */
    private static ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    @Autowired
    private WxApiComponent wxApiComponent ;
    
    @Autowired
    private UserService userService ;

    /** 客户端请求合法拦截器 */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        startTime.set(System.currentTimeMillis());
        //1: 完成正常用户名与密码的登入权限操作。
        HttpSession session=request.getSession(true);
        User user=(User) session.getAttribute(User.SESSION_KEY_USER);

        //2：如果session判断权限失败。再次判断微信权限,规律是：在微信H5页面请求的任何一个Ajax接口，都必须在URL的最后带上?wxCode=wxCode
        if(user == null || null == user.getId()){
            String wxCode = request.getParameter("wxCode") ;
            if(null != null && wxCode.length()>0){
                user = doWxAuth(wxCode,session);
            }
        }
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
        request.getSession().removeAttribute(User.SESSION_KEY_WX_USER);
    }

    /** 使用微信wxCode做认证。 */
    private User doWxAuth(String wxCode, HttpSession session) {
        //1：用wxCode获得openId
        String wxOpenId = wxApiComponent.getOpenIdByCode(wxAppId,wxAppSecret,wxCode);
        if(null == wxOpenId || wxOpenId.isEmpty()){
            return null ;
        }
        //2：根据openId，获得User对象
        User wxUser = userService.getWxUser(wxOpenId);
        if(null != wxUser){
            session.setAttribute(User.SESSION_KEY_WX_USER,wxUser);
            return wxUser;
        }
        return null ;
    }


}
