package com.cloud.stagging.pojo.user;

import lombok.Data;

/**
 * 角色与用户关系表DO
 * @author ChengYun
 * @date 2020/4/12  Vesion 1.0
 */
@Data
public class AuthRoleUser {
    /**
     * ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;
}
