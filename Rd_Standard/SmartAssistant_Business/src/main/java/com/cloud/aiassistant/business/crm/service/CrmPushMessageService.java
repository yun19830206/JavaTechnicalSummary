package com.cloud.aiassistant.business.crm.service;

import com.cloud.aiassistant.core.wxsdk.WxApiComponent;
import com.cloud.aiassistant.pojo.user.User;
import com.cloud.aiassistant.pojo.wxsdh.WxPushMessageResonseVO;
import com.cloud.aiassistant.user.dao.UserMapper;
import com.cloud.aiassistant.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * CRM助手功能，专门给salse推送消息服务
 * @author ChengYun
 * @date 2019/4/12  Vesion 1.0
 */
@Transactional
@Service
@Slf4j
public class CrmPushMessageService {

    @Value("${crm.business.push.wx.allsales}")
    private String allVisitPushString;

    @Autowired
    private WxApiComponent wxApiComponent ;

    @Autowired
    private UserMapper userDao ;

    /** 统一给每一个销售人员，推送及时增加拜访记录 */
    public void pushAllSalesAddVisitInfo() {

        //获得所有人员信息
        List<User> userList = userDao.listTenantAllUser(200L);
        if(null == userList || userList.size()<1){
            return;
        }

        //绑定过WX的用户，统一给其推送消息
        userList.forEach(user ->{
            //user.getCreateUser().equals(1L)代表是销售人员，user.getWxOpenid()代表是关注了微信公众号的人
            if(user.getCreateUser().equals(1L) && user.getWxOpenid() != null && user.getWxOpenid().length()>0){
                this.pushWXMessage(user.getWxOpenid(),allVisitPushString);
            }
        });

    }

    /** 给某个访客、推送一条消息 */
    public WxPushMessageResonseVO pushWXMessage(String openId, String message) {
        if(null == openId || null == message || message.length()<1){
            return new WxPushMessageResonseVO(-1L,"推送消息访客或者推送消息内容为空，不能完成推送消息操作。");
        }

        return wxApiComponent.pushTextMessageToWx(openId,message);
    }
}
