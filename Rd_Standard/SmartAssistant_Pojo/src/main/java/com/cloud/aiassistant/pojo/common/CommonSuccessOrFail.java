package com.cloud.aiassistant.pojo.common;

import lombok.Data;

/**
 * 通用成功还是失败结果。适用于不需要返回VO的接口调用常用
 * @author ChengYun
 * @date 2019/3/9  Vesion 1.0
 */
@Data
public class CommonSuccessOrFail {

    /** 状态码：200 业务成功， 500业务失败 */
    public static final int CODE_SUCCESS = 200 ;
    public static final int CODE_ERROR = 500 ;

    /** 成功还是失败：200 业务成功， 500业务失败 */
    private int resultCode ;

    /** 成功或者失败的原因描述 */
    private String resultDesc ;

    public CommonSuccessOrFail(int resultCode, String resultDesc) {
        this.resultCode = resultCode;
        this.resultDesc = resultDesc;
    }

    /** 业务操作成功 */
    public static CommonSuccessOrFail success(String resultDesc){
        return new CommonSuccessOrFail(CODE_ERROR,resultDesc);
    }

    /** 业务操作失败，失败原因resultDesc，可能性较多 */
    public static CommonSuccessOrFail fail(String resultDesc){
        return new CommonSuccessOrFail(CODE_ERROR,resultDesc);
    }

    @Override
    public String toString() {
        return "CommonSuccessOrFail{" +
                "resultCode=" + resultCode +
                ", resultDesc='" + resultDesc + '\'' +
                '}';
    }
}
