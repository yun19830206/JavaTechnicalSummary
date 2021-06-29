package com.cloud.stagging.pojo.user;

import lombok.Data;

/**
 * 权限功能点实体DO
 * @author ChengYun
 * @date 2020/4/12  Vesion 1.0
 */
@Data
public class AuthMenuFunction {

    /**
     * 权限ID
     */
    private Long menuId;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限标识
     */
    private String permission;
}
