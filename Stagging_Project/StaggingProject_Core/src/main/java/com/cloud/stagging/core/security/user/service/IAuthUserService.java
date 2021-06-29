package com.cloud.stagging.core.security.user.service;

import com.cloud.stagging.pojo.user.AuthMenuFunction;
import com.cloud.stagging.pojo.user.AuthRole;
import com.cloud.stagging.pojo.user.AuthUser;

import java.util.List;

/**
 * @author ChengYun
 * @date 2020/4/12  Vesion 1.0
 */
public interface IAuthUserService  {

    /**
     * 根据用户名查询用户
     * @param userName 用户名
     * @return AuthUser
     */
    AuthUser loadUserByUsername(String userName);


    /**
     * 根据用户ID，获得此用户对应的角色
     * @param userId 用户ID
     * @return List<AuthRole>
     */
    List<AuthMenuFunction> selectRoleMenuFunctionByUserId(Long userId);

    /**
     * 根据用户ID获得用户拥有的权限功能点的List
     * @param userId userId
     * @return List<AuthMenuFunction>
     */
    List<AuthMenuFunction> selectMenuFunctionsByUserId(Long userId);

    /**
     * 列举所有管理员账号信息
     * @return List<AuthUser>
     */
    List<AuthUser> listUsers();

    /**
     * 获得所有菜单功能点
     * @return List<AuthMenuFunction>
     */
    List<AuthMenuFunction> listAuthMenuFunctions();

    /**
     * 获得所有权限数据(非功能菜单数据)
     * @return List<AuthRole>
     */
    List<AuthRole> listAuths();
}
