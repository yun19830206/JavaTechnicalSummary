package com.cloud.aiassistant.core.config;

import com.cloud.aiassistant.pojo.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局Controller接口异常处理
 */
@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {


    /**
     * 简单粗暴的统一处理异常，返回错误Ajax对象，后续可以增加各种详细的异常：404、文件转换错误、参数转换错误等更加细致的处理
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public AjaxResponse handleException(Exception e) {
        log.error("全局异常处理，异常为：",e);
        String exceptionMessage = e.getMessage();
        if(null != exceptionMessage && exceptionMessage.length()>100){
            exceptionMessage = exceptionMessage.substring(0,100);
        }
        if(null == exceptionMessage || exceptionMessage.length()<1 ){
            exceptionMessage = "后台服务器异常，请联系管理员";
        }
        return AjaxResponse.failed(null, exceptionMessage);
    }
}
