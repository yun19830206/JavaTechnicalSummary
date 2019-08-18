package com.cloud.aiassistant.pojo.wxsdh;

import lombok.Data;

/**
 * WX AccessToken返回结构
 * @author ChengYun
 * @date 2019/4/11  Vesion 1.0
 */
@Data
public class WxAccessToken {

    /** 微信返回的 */
    private String access_token ;

    /** 失效时间 秒 */
    private int expires_in ;

    /** 获得accessToken的开始时间 */
    private long startTime ;

}
