package com.cloud.aiassistant.user.service;

import com.cloud.aiassistant.core.utils.EncryptionUtils;
import com.cloud.aiassistant.core.utils.SessionUserUtils;
import com.cloud.aiassistant.core.wxsdk.WxApiComponent;
import com.cloud.aiassistant.pojo.common.CommonSuccessOrFail;
import com.cloud.aiassistant.pojo.user.User;
import com.cloud.aiassistant.user.dao.UserMapper;
import com.cloud.aiassistant.user.pojo.WxBindUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户Service
 * @author ChengYun
 * @date 2019/3/9 Vesion 1.0
 */
@Service
@Transactional
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userDao ;

    @Autowired
    private WxApiComponent wxApiComponent ;

    @Autowired
    private HttpSession session ;

    /** 缓存已有用户信息，不需要密码，仅用于根据ID，获得常用用户名等信息。 切记有人员删除、更新的时候，此Map要更新 */
    private Map<Long,User> userMapCache = new ConcurrentHashMap<>();


    /**
     * 根据入参：User.name,User.password,User.wxCode,微信wxAppId、wxAppSecret 验证用户是否存在，并且绑定用户
     * @param wxBindUserDTO  入参：User.name,User.password,User.wxCode
     * @param wxAppId 微信wxAppId
     * @param wxAppSecret 微信wxAppSecret
     * @return  CommonSuccessOrFail
     */
    public CommonSuccessOrFail bindWxCode2User(WxBindUserDTO wxBindUserDTO, String wxAppId, String wxAppSecret) {
        /**
         * 1：根据用户名、密码 验证用户是否存在，不存在直接返回业务失败
         * 2：根据微信wxAppId、wxAppSecret 和 WxBindUserDTO.wxCode调用微信接口获得微信OpenId，失败直接返回业务失败
         * 3：绑定 User与Openid的关系，入库，返回业务成功。
         */
        //1：根据用户名、密码 验证用户是否存在，不存在直接返回业务失败
        try {
            wxBindUserDTO.setPassword(EncryptionUtils.getMD5EncryptString(wxBindUserDTO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("用户{}密码{}MD5加密出现异常：",wxBindUserDTO.getUserName(),wxBindUserDTO.getPassword(),e);
            return CommonSuccessOrFail.fail("用户名密码错误，请核实后再绑定");
        }
        User user = userDao.selectByUserNamePassword(wxBindUserDTO.getUserName(),wxBindUserDTO.getPassword());
        if(null == user){
            return CommonSuccessOrFail.fail("用户不存在，或者用户名密码错误，请核实后再绑定");
        }
        wxBindUserDTO.setId(user.getId());

        //2：根据微信wxAppId、wxAppSecret 和 WxBindUserDTO.wxCode调用微信接口获得微信OpenId，失败直接返回业务失败
        String wxOpenId = wxApiComponent.getOpenIdByCode(wxAppId,wxAppSecret,wxBindUserDTO.getWxCode());
        if(null == wxOpenId || wxOpenId.isEmpty()){
            return CommonSuccessOrFail.fail("绑定失败，请联系管理员");
        }

        //3：绑定 User与Openid的关系，入库，返回业务成功。
        wxBindUserDTO.setWxOpenId(wxOpenId);
        userDao.bindWxOpenId(wxBindUserDTO);

        return CommonSuccessOrFail.success("绑定成功，请返回让小助手热情为你服务吧。");
    }

    /**
     * 根据微信openid，获得绑定此openid的用户信息
     * @param openid openid
     * @return User
     */
    public User getWxUser(String openid) {
        User user = userDao.selectByOpenid(openid);
        if(null != user){
            user.setPassword("****");
        }
        return user;
    }

    /**
     * 根据用户名 与 密码，获得用户，以判断用户是否存在
     * @param needLoginUser user
     * @return User
     */
    public User login(User needLoginUser) {
        try {
            needLoginUser.setPassword(EncryptionUtils.getMD5EncryptString(needLoginUser.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("用户{}密码{}MD5加密出现异常：",needLoginUser.getUserName(),needLoginUser.getPassword(),e);
            return null ;
        }
        User loginedUser = userDao.selectByUserNamePassword(needLoginUser.getUserName(),needLoginUser.getPassword());
        if(null != loginedUser){
            loginedUser.setPassword("****");
        }

        return loginedUser ;
    }

    /** 返回本租户的所有用户信息 */
    public List<User> listAllUsers() {
        if(null == userMapCache || userMapCache.size()<1){
            getAllUserMapCache();
        }
        List<User> userList = new ArrayList<>();
        userMapCache.forEach((userId,user) -> userList.add(user));
        return userList;
    }

    /** 初始化所有用户缓存Map数据 */
    private void getAllUserMapCache() {
        userMapCache.clear();

        User logineduser = SessionUserUtils.getUserFromSession(session) ;
        List<User> userList = userDao.listTenantAllUser(logineduser.getTenantId());
        //去除敏感信息
        userList.forEach(user -> {
            user.setPassword("******");
            userMapCache.put(user.getId(),user);
        });
    }

    /** 根据用户ID，获得用户对象 */
    public User getUserById(Long userId){
        if(userMapCache.size()<1){
            getAllUserMapCache();
        }
        return userMapCache.get(userId);
    }

    /** 修改密码功能 */
    public void updatePassword(String newPassword) throws Exception {
        if(null == newPassword || newPassword.length() < 1){
            return ;
        }

        User user = SessionUserUtils.getUserFromSession(session);
        String passwordEncode = EncryptionUtils.getMD5EncryptString(newPassword);
        userDao.updatePassword(user.getId(),passwordEncode);
        return;
    }

    /** 清除所有用户缓存数据 */
    public void clearAllUserMapCache(){
        userMapCache = new ConcurrentHashMap<>();
    }
}
