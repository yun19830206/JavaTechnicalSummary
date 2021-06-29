package com.cloud.stagging.core.security.provider;

import com.cloud.stagging.core.security.pojo.AuthUserDetails;
import com.cloud.stagging.core.security.service.AuthUserDetailsServiceImpl;
import com.cloud.stagging.core.security.user.service.IAuthUserService;
import com.cloud.stagging.pojo.user.AuthMenuFunction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 编写自定义登录验证类
 * @author ChengYun
 * @date 2020/4/12  Vesion 1.0
 */
@Slf4j
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final AuthUserDetailsServiceImpl authUserDetailsServiceImpl;

    @Autowired
    public UserAuthenticationProvider(AuthUserDetailsServiceImpl authUserDetailsServiceImpl, IAuthUserService authUserService) {
        this.authUserDetailsServiceImpl = authUserDetailsServiceImpl;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //1：获取表单输入中返回的用户名、密码PermissionEvaluator
        String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        log.info("userName={},password=****,登入验证开始。",userName);

        //2：从数据库查询用户是否存在 并做校验
        AuthUserDetails authUserDetails = authUserDetailsServiceImpl.loadUserByUsername(userName);
        if (authUserDetails == null) {
            log.info("userName={},password=****,结果：用户名不存在。",userName);
            throw new UsernameNotFoundException("用户名不存在");
        }
        //使用BCryptPasswordEncoder进行加密后的密码 判断是否相等
        if (!new BCryptPasswordEncoder().matches(password, authUserDetails.getPassword())) {
            log.info("userName={},password=****,结果：密码不正确。",userName);
            throw new BadCredentialsException("密码不正确");
        }
        // 还可以加一些其他信息的判断，比如用户账号已停用等判断
        if (AuthUserDetails.USER_STATUS_PROHIBIT.equals(authUserDetails.getStatus())){
            log.info("userName={},password=****,结果：该用户已被冻结。",userName);
            throw new LockedException("该用户已被冻结");
        }

        //3：获得本用户对应的角色功能点集合(注意是功能点而不是角色，角色是使用者创建的)，并包装成SpringSecurity角色对象GrantedAuthority
        Set<GrantedAuthority> authorities = new HashSet<>();
        // 查询用户角色
        List<AuthMenuFunction> roleMenuFunctionEntityList = authUserDetailsServiceImpl.selectRoleMenuFunctionByUserId(authUserDetails.getUserId());
        for (AuthMenuFunction authMenuFunction: roleMenuFunctionEntityList){
            authorities.add(new SimpleGrantedAuthority(authMenuFunction.getPermission()));
        }
        authUserDetails.setAuthorities(authorities);
        log.info("userName={},password=****,登入验证成功。",userName);
        //4：进行登录
        return new UsernamePasswordAuthenticationToken(authUserDetails, password, authorities);

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
