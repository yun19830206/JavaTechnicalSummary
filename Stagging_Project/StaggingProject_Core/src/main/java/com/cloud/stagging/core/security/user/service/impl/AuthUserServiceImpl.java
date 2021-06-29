package com.cloud.stagging.core.security.user.service.impl;

import com.cloud.stagging.core.security.user.service.IAuthUserService;
import com.cloud.stagging.pojo.user.AuthMenuFunction;
import com.cloud.stagging.pojo.user.AuthRole;
import com.cloud.stagging.pojo.user.AuthUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 登入用户Service
 * @author ChengYun
 * @date 2020/4/12  Vesion 1.0
 */
@Component
public class AuthUserServiceImpl implements IAuthUserService{

    @Override
    public AuthUser loadUserByUsername(String userName) {
        //暂时不与数据库打交道，模拟登入成功
        return new AuthUser();
    }

    @Override
    public List<AuthMenuFunction> selectRoleMenuFunctionByUserId(Long userId) {
        //暂时不与数据库打交道，模拟用户拥有的角色
        List<AuthMenuFunction> authRoleList = new ArrayList<>();
        authRoleList.add(new AuthMenuFunction());
        return authRoleList;
    }

    @Override
    public List<AuthMenuFunction> selectMenuFunctionsByUserId(Long userId) {
        List<AuthMenuFunction> authMenuFunctionList = new ArrayList<>();
        //1:获得此用户的角色List

        //2:获得角色对应的功能点(这里可以取内存缓存，以增加速度)
        authMenuFunctionList.add(new AuthMenuFunction());

        return authMenuFunctionList;
    }

    @Override
    public List<AuthUser> listUsers() {
        return new ArrayList<>();
    }

    @Override
    public List<AuthMenuFunction> listAuthMenuFunctions() {
        return new ArrayList<>();
    }

    @Override
    public List<AuthRole> listAuths() {
        return null;
    }
}
