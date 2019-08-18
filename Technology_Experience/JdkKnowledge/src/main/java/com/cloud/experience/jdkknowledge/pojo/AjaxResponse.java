package com.cloud.experience.jdkknowledge.pojo;

import java.io.Serializable;

/**
 * Ajax请求通用返回结果类型
 * @author ChengYun
 * @date 2018/12/20 Version 1
 */
public class AjaxResponse<T> implements Serializable{

    private static final long serialVersionUID = -8037998058337071948L;

    /** 状态码：200 业务成功， 500业务失败 */
    private static final int CODE_SUCCESS = 200 ;
    private static final int CODE_ERROR = 500 ;
    private static final int CODE_NO_LOGIN = 505 ;

    /** 返回结果状态码， 200成功，500内部错误 */
    private int code ;
    /** 返回结果描述 */
    private String message ;
    /** 返回结果 调试信息 */
    private String debugMessage ;
    /** 返回结果数据 */
    private transient T data ;

    public AjaxResponse(){}

    public AjaxResponse(int code, String message, String debugMessage, T data) {
        this.code = code;
        this.message = message;
        this.debugMessage = debugMessage;
        this.data = data;
    }

    /** 获得Message方法：如果有值直接返回，没有值通过code码给默认值 */
    public String getMessage() {
        if(message == null ){
            return  code == CODE_SUCCESS ? "请求成功" : "请求失败" ;
        }
        return message ;
    }

    /** Ajax成功返回静态方法 */
    public static <T> AjaxResponse<T> success(){
        return new AjaxResponse<>(CODE_SUCCESS,null,null,null);
    }
    public static <T> AjaxResponse<T> success(T data){
        return new AjaxResponse<>(CODE_SUCCESS,null,null,data);
    }
    public static <T> AjaxResponse<T> success(T data, String message){
        return new AjaxResponse<>(CODE_SUCCESS,message,null,data);
    }

    /** Ajax失败返回静态方法 */
    public static <T> AjaxResponse<T> failed(){
        return new AjaxResponse<>(CODE_ERROR,null,null,null);
    }
    public static <T> AjaxResponse<T> failed(T data){
        return new AjaxResponse<>(CODE_ERROR,null,null,data);
    }
    public static <T> AjaxResponse<T> failed(T data, String message){
        return new AjaxResponse<>(CODE_ERROR,message,null,data);
    }

    /** 没有登入 */
    public static AjaxResponse noAuth() {
        return new AjaxResponse<>(CODE_NO_LOGIN,null,"没有权限，请先登入",null);
    }


    /** ======================如下为较少改动 get set方法区================ */
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getDebugMessage() {
        return debugMessage;
    }
    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }


}
