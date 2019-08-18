package com.cloud.aiassistant.pojo.wxsdh;

import lombok.Data;

/**
 * 微信推送消息返回结果POJO
 * @author ChengYun
 * @date 2019/3/9  Vesion 1.0
 */
@Data
public class WxPushMessageResonseVO {

    /** 微信推送消息返回：状态码 枚举值 */
    public static Long WX_PUSH_MESS_RETURN_OK = 0L ;
    public static Long WX_PUSH_MESS_RETURN_OUT_TIME = 45015L ;
    public static Long WX_PUSH_MESS_RETURN_INVALID_OPENID = 40003L ;
    public static Long WX_PUSH_MESS_RETURN_INVALID_TOKEN = 40001L ;

    /**  微信推送消息返回：状态码 */
    private Long errcode	;

    /** 微信推送消息返回：状态码 描述信息 */
    private String errmsg	;

    public WxPushMessageResonseVO(){};

    public WxPushMessageResonseVO(Long errcode,String errmsg){
        this.errcode = errcode ;
        this.errmsg = errmsg ;
    }

}
