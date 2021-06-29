package com.cloud.stagging.core.initbean;

import com.cloud.stagging.core.exception.ParameterValidatorException;
import com.cloud.stagging.pojo.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;

/**
 * 全局Controller接口异常处理
 */
@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    private static final int EXCEPTION_MESSAGE_LENGTH = 100 ;

    /**
     * 简单粗暴的统一处理异常，返回错误Ajax对象，后续可以增加各种详细的异常：404、文件转换错误、参数转换错误等更加细致的处理
     * @param e 最外层的异常Exception
     * @return AjaxResponse
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public AjaxResponse handleException(Exception e) {
        log.error("全局异常处理，异常为：{}",e);
        String exceptionMessage = e.getMessage();
        if(null != exceptionMessage && exceptionMessage.length()> EXCEPTION_MESSAGE_LENGTH){
            exceptionMessage = exceptionMessage.substring(0,100);
        }
        if(null == exceptionMessage || exceptionMessage.length()<1 ){
            exceptionMessage = "后台服务器异常，请联系管理员";
        }
        return AjaxResponse.failed(null, exceptionMessage);
    }

    /**
     * Hibernate Validator参数校验异常处理
     * @param exception MethodArgumentNotValidException异常
     * @return AjaxResponse
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public AjaxResponse validatorException(MethodArgumentNotValidException exception){
        return AjaxResponse.failed(null,exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    /**
     * 自定义参数校验异常处理
     * @param exception ParameterValidatorException异常
     * @return AjaxResponse
     */
    @ExceptionHandler(ParameterValidatorException.class)
    @ResponseBody
    public AjaxResponse validatorException(ParameterValidatorException exception){
        return AjaxResponse.failed(null,exception.getValidatorString());
    }
}
