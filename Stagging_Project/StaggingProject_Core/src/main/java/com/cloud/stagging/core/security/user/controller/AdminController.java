package com.cloud.stagging.core.security.user.controller;

import com.cloud.stagging.core.security.pojo.AuthUserDetails;
import com.cloud.stagging.core.security.user.service.IAuthUserService;
import com.cloud.stagging.core.security.utils.SecurityUtil;
import com.cloud.stagging.pojo.common.AjaxResponse;
import com.cloud.stagging.pojo.user.AuthMenuFunction;
import com.cloud.stagging.pojo.user.AuthRole;
import com.cloud.stagging.pojo.user.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 管理端Controller,用于模拟是否拥有管理权限
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final IAuthUserService authUserService;

    @Autowired
    public AdminController(IAuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    /**
     * [拥有ADMIN功能点可以访问]
     * 获得当前登入的管理员信息
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/info")
    public AjaxResponse userLogin(){
        AuthUserDetails userDetails = SecurityUtil.getUserInfo();
        return AjaxResponse.success(userDetails,"拥有ADMIN功能点可以访问");
    }

    /**
     * [拥有ADMIN功能点或者USER功能点可以访问]
     * 获得所有管理员信息
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @RequestMapping(value = "/user/list")
    public AjaxResponse listUsers(HttpServletRequest request){
        List<AuthUser> authUserList = authUserService.listUsers();
        request.getRemoteAddr(); request.getLocalAddr();
        return AjaxResponse.success(authUserList,"拥有用户或者管理员角色都可以查看");
    }

    /**
     * [拥有ADMIN功能点和USER功能点可以访问]
     * 获得所有功能点信息
     */
    @PreAuthorize("hasRole('ADMIN') and hasRole('USER')")
    @RequestMapping(value = "/menu/list")
    public AjaxResponse menuList(){
        List<AuthMenuFunction> sysMenuEntityList = authUserService.listAuthMenuFunctions();
        return AjaxResponse.success(sysMenuEntityList,"拥有用户和管理员角色都可以查看");
    }


    /**
     * [拥有sys:user:info权限可以访问] TODO待验证是且的关系还是或的关系
     * hasPermission 第一个参数是请求路径 第二个参数是权限表达式
     */
    @PreAuthorize("hasPermission('/admin/userList','sys:user:info')")
    @RequestMapping(value = "/userListWithPermission")
    public AjaxResponse userListWithPermission(){
        List<AuthUser> authUserList = authUserService.listUsers();
        return AjaxResponse.success(authUserList,"拥有sys:user:info权限都可以查看");
    }


    /**
     * [拥有ADMIN角色和sys:role:info权限可以访问]  TODO待验证是且的关系还是或的关系
     */
    @PreAuthorize("hasRole('ADMIN') and hasPermission('/admin/adminRoleList','sys:role:info')")
    @RequestMapping(value = "/adminRoleList")
    public AjaxResponse adminRoleList(){
        List<AuthRole> authRoleList = authUserService.listAuths();
        return AjaxResponse.success(authRoleList,"拥有ADMIN角色和sys:role:info权限可以访问");
    }
}