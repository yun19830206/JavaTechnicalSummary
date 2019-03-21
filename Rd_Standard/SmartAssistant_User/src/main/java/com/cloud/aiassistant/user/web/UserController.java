package com.cloud.aiassistant.user.web;


import com.cloud.aiassistant.pojo.common.AjaxResponse;
import com.cloud.aiassistant.pojo.common.CommonSuccessOrFail;
import com.cloud.aiassistant.pojo.user.User;
import com.cloud.aiassistant.user.pojo.WxBindUserDTO;
import com.cloud.aiassistant.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 本Controller的作用：SpringMVC请求参数获取的几种方法。
 * @author ChengYun
 * @date 2019/3/9 Version 1
 */
@RestController
@Slf4j
@RequestMapping("/aiassistant/user")
public class UserController {

    /** 用于微信交互的appid */
    @Value("${wx.appid:wxa9b9e33e4f8dfbde}")
    private String wxAppId;

    /** 用于微信交互的AppSecret */
    @Value("${wx.appSecret:67500831dd15952c8167cef47d5a3bd4}")
    private String wxAppSecret;

    @Autowired
    private UserService userService ;

    /**
     * 入参：User.name,User.password,User.wxCode
     * post方式(Content-Type: application/json) 可以获得参数的值
     */
    @RequestMapping("/wx/binding")
    public AjaxResponse bindWxCode2User(@RequestBody WxBindUserDTO wxBindUserDTO){
        CommonSuccessOrFail commonSuccessOrFail = userService.bindWxCode2User(wxBindUserDTO,wxAppId,wxAppSecret);
        if(null != commonSuccessOrFail && CommonSuccessOrFail.CODE_ERROR == commonSuccessOrFail.getResultCode()){
            return AjaxResponse.failed(null,commonSuccessOrFail.getResultDesc());
        }else{
            return AjaxResponse.success(null,commonSuccessOrFail.getResultDesc());
        }
    }

    /**
     * 根据微信openid获得User对象。 用于给机器人做第三方认证使用。
     * post方式(Content-Type: application/json) 可以获得参数的值, 或者Get方式
     */
    @RequestMapping("/wx/getwxuser")
    public AjaxResponse getWxUser(String openid){
        if(null == openid) { return AjaxResponse.failed(null,"微信OpenId不能为空!");}
        User user = userService.getWxUser(openid);
        if(null == user){
            return AjaxResponse.failed(null,"不存在此用户");
        }else{
            return AjaxResponse.success(user);
        }
    }

    /**
     * 获得本租户的所有用户信息
     */
    @RequestMapping("/list/users")
    public AjaxResponse listAllUsers(){
        List<User> userList = userService.listAllUsers();
        return AjaxResponse.success(userList);
    }


}
