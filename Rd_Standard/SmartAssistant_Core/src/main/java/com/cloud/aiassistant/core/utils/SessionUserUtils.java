package com.cloud.aiassistant.core.utils;

import com.cloud.aiassistant.pojo.user.User;

import javax.servlet.http.HttpSession;

/**
 * Session与User存储关系Utils
 * @author ChengYun
 * @date 2019/3/17  Vesion 1.0
 */
public class SessionUserUtils {

    private SessionUserUtils(){}

    /**
     * 从Session中获得
     * @param session
     * @return
     */
    public static User getUserFromSession(HttpSession session){
        if(null == session){return null ;}

        User user = null;
        //1:从 普通登入Session中获得User对象
        user=(User) session.getAttribute(User.SESSION_KEY_USER);
        if(null != user){
            return user ;
        }
        //2:从 微信授权Session中获得User对象
        user=(User) session.getAttribute(User.SESSION_KEY_WX_USER);
        return user ;
    }
}
