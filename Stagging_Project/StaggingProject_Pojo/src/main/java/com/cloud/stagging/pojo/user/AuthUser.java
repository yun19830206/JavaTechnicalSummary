package com.cloud.stagging.pojo.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 登入用户对象
 * @author ChengYun
 * @date 2020/4/12  Vesion 1.0
 */
@Data
public class AuthUser implements Serializable {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态:NORMAL正常  PROHIBIT禁用
     */
    private String status;

    /**
     * 登入成功之后的 accessToken
     */
    private String accessToken;
}
