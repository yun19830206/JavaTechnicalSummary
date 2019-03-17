package com.cloud.aiassistant.user.dao;

import com.cloud.aiassistant.pojo.user.User;
import com.cloud.aiassistant.user.pojo.WxBindUserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Use用户Dao
 */
@Mapper
public interface UserMapper {

    User selectByPrimaryKey(Long id);

    /** 根据用户名、密码查询用户 */
    User selectByUserNamePassword(@Param("userName") String userName, @Param("password") String password);

    /** 根据WxBindUserDTO绑定用户的OpenId，本质是更新user.id的 */
    void bindWxOpenId(WxBindUserDTO wxBindUserDTO);

    /** 根据微信openid，获得绑定此openid的用户信息 */
    User selectByOpenid(@Param("openid") String openid);

    /** 返回本租户的所有用户信息 */
    List<User> listAllUser(@Param("tenantId") Long tenantId);
}