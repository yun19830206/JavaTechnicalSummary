package com.cloud.aiassistant.user.web;

import com.cloud.aiassistant.pojo.common.AjaxResponse;
import com.cloud.aiassistant.pojo.user.User;
import com.cloud.aiassistant.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户登入登出Controller
 * @author ChengYun
 * @date 2019/3/10  Vesion 1.0
 */
@RestController
@Slf4j
@RequestMapping("/aiassistant/user")
public class UserLoginController {

    @Autowired
    private UserService userService ;

    @Autowired
    private HttpSession session ;

    /**
     * 入参：User.name,User.password,User.wxCode
     * post方式(Content-Type: application/json) 可以获得参数的值
     */
    @RequestMapping("/login/userlogin")
    public AjaxResponse login(@RequestBody User needLoginUser){
        User loginedUser = userService.login(needLoginUser);
        if(null != loginedUser && null != loginedUser.getId()){
            session.setAttribute(User.SESSION_KEY_USER,loginedUser);
            return AjaxResponse.success(loginedUser);
        }else{
            return AjaxResponse.failed(null,"登入失败，请核对用户名与密码");
        }
    }

    /**
     * 登出接口
     */
    @RequestMapping("/login/userlogout")
    public AjaxResponse logout(HttpServletRequest request){
        request.getSession().removeAttribute(User.SESSION_KEY_USER);
        return AjaxResponse.success();
    }
}
