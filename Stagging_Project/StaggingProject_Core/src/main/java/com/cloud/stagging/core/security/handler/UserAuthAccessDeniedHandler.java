package com.cloud.stagging.core.security.handler;

import com.cloud.stagging.core.utils.AjaxJsonResponseUtil;
import com.cloud.stagging.pojo.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 暂无权限处理类
 * @author ChengYun
 * @date 2020/4/12  Vesion 1.0
 */
@Slf4j
@Component
public class UserAuthAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * 暂无权限返回结果
     * @param request 请求
     * @param response 返回
     * @param e 异常
     * @throws IOException 抛出IO异常
     * @throws ServletException  抛出异常
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        log.info("暂无权限处理类 执行完成。");
        AjaxJsonResponseUtil.responseJson(response, AjaxResponse.noAuth());
    }
}
