package com.cloud.stagging.core.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置类
 * @author ChengYun
 * @date 2020/4/13  Vesion 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JWTConfig {
    /**
     * 密钥KEY
     */
    private String secret;
    /**
     * TokenKey
     */
    private String tokenHeader;
    /**
     * Token前缀字符
     */
    private String tokenPrefix;

    /**
     * 过期时间
     */
    private Integer expiration;
    /**
     * 不需要认证的接口
     */
    private String antMatchers;

}
