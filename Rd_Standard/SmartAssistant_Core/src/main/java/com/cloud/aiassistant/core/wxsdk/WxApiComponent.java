package com.cloud.aiassistant.core.wxsdk;

import com.alibaba.fastjson.JSONObject;
import com.cloud.aiassistant.core.utils.RestTemplateUtils;
import com.cloud.aiassistant.pojo.wxsdh.WxAccessToken;
import com.cloud.aiassistant.pojo.wxsdh.WxPushMessageResonseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    /** 用于微信交互的appid */
    @Value("${wx.appid:wxa9b9e33e4f8dfbde}")
    private String wxAppId;

    /** 用于微信交互的AppSecret */
    @Value("${wx.appSecret:67500831dd15952c8167cef47d5a3bd4}")
    private String wxAppSecret;

    private WxAccessToken wxAccessToken;

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

    /** 对外暴露接口的，获得微信AccessToken */
    public WxAccessToken getWxAccessToken() {
        //1：AccessToken不存在，获得新Token返回
        if(wxAccessToken == null){
            getPrivateWxAccessToken();
            log.info("微信Token为空，新获得的Token为：{}",wxAccessToken);
        }

        //2: 如果Token超时了，获得新Token返回
        long startTimeSecond = wxAccessToken.getStartTime()/1000;
        long nowSecond = System.currentTimeMillis()/1000;

        if((nowSecond - startTimeSecond) > wxAccessToken.getExpires_in()){
            log.info("微信Token失效,原Token{},现在时刻{},将获得新Token",wxAccessToken,nowSecond);
            getPrivateWxAccessToken();
            log.info("微信Token失效获得的新Token为：{}",wxAccessToken);
        }

        return wxAccessToken;
    }

    /** 私有获得微信AccessToken */
    private void getPrivateWxAccessToken() {
        //1:获得微信原生AccessToken
        StringBuilder url = new StringBuilder("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=").append(wxAppId).append("&secret=").append(wxAppSecret);
        WxAccessToken oneWxAccessToken = RestTemplateUtils.get(restTemplate, url.toString(), null, WxAccessToken.class);

        //2:写入获得Token的开始时间
        if(null != oneWxAccessToken && null != oneWxAccessToken.getAccess_token()){
            oneWxAccessToken.setStartTime(System.currentTimeMillis());
        }
        wxAccessToken = oneWxAccessToken;
    }

    /** 强制获得微信AccessToken,不管是否超期、有效 */
    public WxAccessToken forceGetWxAccessToken() {
        //1:获得微信原生AccessToken
        StringBuilder url = new StringBuilder("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=").append(wxAppId).append("&secret=").append(wxAppSecret);
        WxAccessToken oneWxAccessToken = RestTemplateUtils.get(restTemplate, url.toString(), null, WxAccessToken.class);

        //2:写入获得Token的开始时间
        if(null != oneWxAccessToken && null != oneWxAccessToken.getAccess_token()){
            oneWxAccessToken.setStartTime(System.currentTimeMillis());
        }
        wxAccessToken = oneWxAccessToken;
        log.info("强制获得微信AccessToken：{}",wxAccessToken);
        return oneWxAccessToken ;
    }

    /**
     * 给微信公众号推送消息
     * @param toUserOpenId
     * @param content
     */
    public WxPushMessageResonseVO pushTextMessageToWx(String toUserOpenId, String content){
        if(null == toUserOpenId || null == content || content.length()<1){
            return new WxPushMessageResonseVO(-1L,"推送消息访客或者推送消息内容为空，不能完成推送消息操作。");
        }

        //组装文本微信JSONObject
        JSONObject wxTextMessageJson = createWxTextJson(toUserOpenId, content);

        StringBuilder url = new StringBuilder("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=").append(getWxAccessToken().getAccess_token());
        WxPushMessageResonseVO wxPushMessageResonse = RestTemplateUtils.post(restTemplate, url.toString(), wxTextMessageJson, WxPushMessageResonseVO.class);
        //如果是 AccessToken失效，需要强制重新获得AccessToken，再发送消息一次
        if(WxPushMessageResonseVO.WX_PUSH_MESS_RETURN_INVALID_TOKEN.equals(wxPushMessageResonse.getErrcode())){
            this.forceGetWxAccessToken();
            url = new StringBuilder("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=").append(getWxAccessToken().getAccess_token());
            wxPushMessageResonse = RestTemplateUtils.post(restTemplate, url.toString(), wxTextMessageJson, WxPushMessageResonseVO.class);
        }

        log.info("微信推送消息结果：{}",wxPushMessageResonse);

        return wxPushMessageResonse;
    }

    /**  组装文本微信JSONObject */
    private JSONObject createWxTextJson(String toUserOpenId, String content) {
        JSONObject wxTextMessageJson = new JSONObject();
        wxTextMessageJson.put("touser",toUserOpenId);
        wxTextMessageJson.put("msgtype","text");

        JSONObject contentJson = new JSONObject();
        contentJson.put("content",content);
        wxTextMessageJson.put("text",contentJson);

        return wxTextMessageJson;
    }

    /**
     * 微信公众号交互 状态返回说明
     *   {
         "errcode": 0,     正常
         "errmsg": "ok"
         }
         {
         "errcode": 45015,   访客超过24小时没与公众号交互，不能发消息
         "errmsg": "response out of time limit or subscription is canceled hint: [tMHmTa05993948]"
         }
         {
         "errcode": 40003,   访客openid不合法
         "errmsg": "invalid openid hint: [NiEbma06403941]"
         }
         {
         "errcode": 40001,  accessToken不合法
         "errmsg": "invalid credential, access_token is invalid or not latest hint: [csqoTa0665vr31!]"
         }
     */
}
