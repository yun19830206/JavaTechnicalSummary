package com.cloud.stagging.core.exception;

/**
 * 参数校验失败异常
 * @author ChengYun
 * @date 2020/4/8  Vesion 1.0
 */
public class ParameterValidatorException extends RuntimeException {

    /** 业务描述信息 */
    private String validatorString ;

    public ParameterValidatorException(String validatorString){
        super(validatorString);
        this.validatorString = validatorString;
    }

    public String getValidatorString() {
        return validatorString;
    }
}
