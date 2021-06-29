package com.cloud.stagging.pojo.user;

import lombok.Data;

/**
 * 角色与权限功能点关系表DO
 * @author ChengYun
 * @date 2020/4/12  Vesion 1.0
 */
@Data
public class AuthRoleMenuFunction {
    /**
     * ID
     */
    private Long id;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 权限ID
     */
    private Long menuId;
}
