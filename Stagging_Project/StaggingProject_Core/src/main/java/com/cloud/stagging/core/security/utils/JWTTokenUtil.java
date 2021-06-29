package com.cloud.stagging.core.security.utils;

import com.alibaba.fastjson.JSON;
import com.cloud.stagging.core.initbean.SpringContextHolder;
import com.cloud.stagging.core.security.config.JWTConfig;
import com.cloud.stagging.core.security.pojo.AuthUserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT工具类
 * @author ChengYun
 * @date 2020/4/13  Vesion 1.0
 */
@Slf4j
public class JWTTokenUtil {

    /**
     * 私有化构造器
     */
    private JWTTokenUtil(){}

    /**
     * 生成AccessToken
     * @Param  authUserDetails 用户安全实体
     * @Return AccessToken 字符串
     */
    public static String createAccessToken(AuthUserDetails authUserDetails){

        JWTConfig jwtConfig = SpringContextHolder.getBean(JWTConfig.class);

        // 登陆成功生成JWT
        String token = Jwts.builder()
                // 放入用户名和用户ID
                .setId(authUserDetails.getUserId()+"")
                // 主题
                .setSubject(authUserDetails.getUsername())
                // 签发时间
                .setIssuedAt(new Date())
                // 签发者
                .setIssuer("Cloud")
                // 自定义属性 放入用户拥有权限
                .claim("authorities", JSON.toJSONString(authUserDetails.getAuthorities()))
                // 失效时间
                .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                // 签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
                .compact();
        return token;
    }
}
