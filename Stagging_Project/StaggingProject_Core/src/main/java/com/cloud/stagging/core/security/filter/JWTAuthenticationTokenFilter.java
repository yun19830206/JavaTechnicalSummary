package com.cloud.stagging.core.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.cloud.stagging.core.initbean.SpringContextHolder;
import com.cloud.stagging.core.security.config.JWTConfig;
import com.cloud.stagging.core.security.pojo.AuthUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JWT接口请求校验拦截器
 * 请求接口时会进入这里验证Token是否合法和过期
 * @author ChengYun
 * @date 2020/4/15  Vesion 1.0
 */
@Slf4j
public class JWTAuthenticationTokenFilter extends BasicAuthenticationFilter {

    public JWTAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        JWTConfig jwtConfig = SpringContextHolder.getBean(JWTConfig.class);

        // 获取请求头中JWT的Token
        String tokenHeader = request.getHeader(jwtConfig.getTokenHeader());
        if (null != tokenHeader && tokenHeader.startsWith(jwtConfig.getTokenPrefix())) {
            try {
                // 去除JWT前缀
                String token = tokenHeader.replace(jwtConfig.getTokenPrefix(), "");
                // 解析JWT
                Claims claims = Jwts.parser()
                        .setSigningKey(jwtConfig.getSecret())
                        .parseClaimsJws(token)
                        .getBody();
                // 获取用户名
                String username = claims.getSubject();
                String userId=claims.getId();
                if((!username.isEmpty()) &&(!userId.isEmpty())) {
                    // 获取角色
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    String authority = claims.get("authorities").toString();
                    if(!StringUtils.isEmpty(authority)){
                        List<Map<String,String>> authorityMap = JSONObject.parseObject(authority, List.class);
                        for(Map<String,String> role : authorityMap){
                            if(!StringUtils.isEmpty(role)) {
                                authorities.add(new SimpleGrantedAuthority(role.get("authority")));
                            }
                        }
                    }
                    //组装参数
                    AuthUserDetails authUserDetails = new AuthUserDetails();
                    authUserDetails.setUsername(claims.getSubject());
                    authUserDetails.setUserId(Long.parseLong(claims.getId()));
                    authUserDetails.setAuthorities(authorities);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authUserDetails, userId, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException e){
                log.info("Token过期");
            } catch (Exception e) {
                log.info("Token无效");
            }
        }
        filterChain.doFilter(request, response);
        return;
    }

}
