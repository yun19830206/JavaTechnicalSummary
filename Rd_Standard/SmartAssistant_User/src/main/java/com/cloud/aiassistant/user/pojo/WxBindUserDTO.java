package com.cloud.aiassistant.user.pojo;

import lombok.Data;

/**
 * 微信身份认证绑定DTO
 * @author ChengYun
 * @date $time $date Vesion 1.0
 */
@Data
public class WxBindUserDTO {

    /** 用户主键ID，用户绑定成功后更新数据 */
    private Long id;

    /** 用户名 */
    private String userName ;

    /** 密码 */
    private String password ;

    /** 微信Auth认证原始code值，通过此code值可以获得openid */
    private String wxCode ;

    /** 微信Auth认证根据code值获得的openid */
    private String wxOpenId ;
}
