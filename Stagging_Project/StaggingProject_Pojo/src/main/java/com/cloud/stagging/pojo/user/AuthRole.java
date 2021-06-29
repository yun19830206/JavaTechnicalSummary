package com.cloud.stagging.pojo.user;

import lombok.Data;

/**
 * 系统权限对应实体表
 * @author ChengYun
 * @date 2020/4/12  Vesion 1.0
 */
@Data
public class AuthRole {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;
}
