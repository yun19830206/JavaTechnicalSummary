package com.cloud.aiassistant.core.wxsdk;

import com.cloud.aiassistant.core.utils.RestTemplateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 微信平台API交互Bean
 * @author ChengYun
 * @date 2019/3/9  Vesion 1.0
 */
@Component
@Slf4j
public class WxApiComponent {

    /** 获得微信网页AccessToken失败时返回Map的长度 */
    private static int WEB_WX_TOKEN_INVAILD_MAP_LENGTH = 2 ;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 根据wxAppId，wxAppSecret，wxCode，拿到
     * @param wxAppId wxAppId
     * @param wxAppSecret wxAppSecret
     * @param wxCode wxCode
     * @return getOpenIdByCode
     */
    public String getOpenIdByCode(String wxAppId, String wxAppSecret, String wxCode) {
        StringBuilder url = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/access_token?appid=");
        url.append(wxAppId).append("&secret=").append(wxAppSecret).append("&code=").append(wxCode).append("&grant_type=authorization_code");

        Map webWxAccessTokenMap = RestTemplateUtils.get(restTemplate,url.toString(),null,Map.class);
        if(null == webWxAccessTokenMap || webWxAccessTokenMap.size() == WEB_WX_TOKEN_INVAILD_MAP_LENGTH){
            log.error("use wxAppId {}, wxAppSecret {}, wxCode {},get WXAccessToken invalid：{}",wxAppId,wxAppSecret,wxCode,webWxAccessTokenMap);
            return "" ;
        }
        return webWxAccessTokenMap.get("openid").toString();


    }
}
