package com.cloud.stagging.core.security.service;

import com.cloud.stagging.core.security.pojo.AuthUserDetails;
import com.cloud.stagging.core.security.user.service.IAuthUserService;
import com.cloud.stagging.pojo.user.AuthMenuFunction;
import com.cloud.stagging.pojo.user.AuthUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * SpringSecurity对应用户业务实现. 注意有AuthUserService的区别
 * @author ChengYun
 * @date 2020/4/12  Vesion 1.0
 */
@Component
public class AuthUserDetailsServiceImpl implements UserDetailsService {

    private final IAuthUserService authUserService ;

    @Autowired
    public AuthUserDetailsServiceImpl(IAuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @Override
    public AuthUserDetails loadUserByUsername(String username) {
        //从数据库查询用户是否存在。用户名纯在则组装SpringSecurity安全用户对象
        AuthUser authUser = authUserService.loadUserByUsername(username);
        if(null != authUser){
            AuthUserDetails safeUserDetails = new AuthUserDetails();
            BeanUtils.copyProperties(authUser,safeUserDetails);
            return safeUserDetails;
        }
        return null ;
    }

    /**
     * 根据用户ID，获得此用户对应的角色
     * @param userId 用户ID
     * @return List<AuthMenuFunction>
     */
    public List<AuthMenuFunction> selectRoleMenuFunctionByUserId(Long userId) {
        return authUserService.selectRoleMenuFunctionByUserId(userId);
    }
}
