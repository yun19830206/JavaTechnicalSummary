package com.cloud.stagging.pojo.user;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 用户DO
 * @author Cloud
 * @date 2019/3/7 Version 1
 */
@Data
public class User {

    /** 放入Session中key的唯一标识 */
    public static final String SESSION_KEY_USER = "user" ;
    public static final String SESSION_KEY_WX_USER = "wxUser" ;

    /** 主键ID */
    private Long id;

    /** 用户名 */
    private String userName;

    /** 年龄 */
    private Long age ;

    /** 密码，MD5加密存储 */
    private String password;

    /** 创建日期(类型对应Java中TimeStamp,JS中相同字符串)2019-03-07 19:19:38 */
    private Timestamp createTime;

    /** 创建用户 */
    private Long createUser;

    /** 租户ID */
    private Long tenantId;

    /** 公众号中绑定的微信openid */
    private String wxOpenid ;

    public User(String name, Long age) {
        userName = name ;
        this.age = age ;
    }
}