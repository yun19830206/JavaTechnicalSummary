package com.cloud.aiassistant.core.exception;

import lombok.Data;

/**
 * 表单设计器业务异常包裹类
 * @author ChengYun
 * @date 2019/4/30  Vesion 1.0
 */
public class FormDesignBusinessException extends RuntimeException {

    /** 业务描述信息 */
    private String errorString ;

    public FormDesignBusinessException(String errorString){
        super(errorString);
        this.errorString = errorString;
    }
}
