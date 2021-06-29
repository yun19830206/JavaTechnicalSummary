package com.cloud.stagging.core.security.provider;

import com.cloud.stagging.core.security.pojo.AuthUserDetails;
import com.cloud.stagging.core.security.user.service.IAuthUserService;
import com.cloud.stagging.pojo.user.AuthMenuFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 编写自定义PermissionEvaluator注解验证
 * @author ChengYun
 * @date 2020/4/13  Vesion 1.0
 */
@Slf4j
@Component
public class UserPermissionEvaluator implements PermissionEvaluator {

    private final IAuthUserService authUserService ;

    public UserPermissionEvaluator(IAuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    /**
     * 判断是否拥有某个功能权限：先获取登入用户、再拿到登入人权限集合、最后判断是否拥有权限
     * @param  authentication  用户身份
     * @param  targetUrl  请求路径
     * @param  permission 请求路径权限的唯一标识Code
     * @return boolean 是否通过
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object permission) {
        //1：获取已经登入的用户信息
        AuthUserDetails selfUserEntity =(AuthUserDetails) authentication.getPrincipal();

        //2：根据用户登入时候存在的权限集合，判断当前操作是否有权限，有权限返回true
        Collection<GrantedAuthority> authorities = selfUserEntity.getAuthorities();
        for(GrantedAuthority grantedAuthority : authorities){
            if(grantedAuthority != null && grantedAuthority.getAuthority().equals(permission.toString())){
                log.info("userName={},拥有请求权限点{},请求成功。",selfUserEntity.getUsername(),permission.toString());
                return true;
            }
        }

        //3：没有权限返回false
        log.info("userName={},没有请求权限点{},请求失败。",selfUserEntity.getUsername(),permission.toString());
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
