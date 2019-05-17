package com.cloud.aiassistant.stagging.web;

import com.cloud.aiassistant.pojo.common.AjaxResponse;
import com.cloud.aiassistant.stagging.core.cache.impl.RedisCacheImpl;
import com.cloud.aiassistant.stagging.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Redis缓存使用Demo
 * @author ChengYun
 * @date 2019/5/10  Vesion 1.0
 */
@RestController
@RequestMapping("/demo/web/cache")
@Slf4j
public class UseCacheDemo {
    public static final String CACHE_STRING_KEY = "cache:demo:string";
    public static final String CACHE_OBJECT_KEY = "cache:demo:object";
    public static final String CACHE_LIST_KEY = "cache:demo:list";
    public static final String CACHE_MAP_KEY = "cache:demo:map";
    public static final String CACHE_PACKAGE_OBJECT_KEY = "cache:demo:packageobject";


    @Autowired
    private RedisCacheImpl redisCache ;

    @RequestMapping("/setGetCacheValue")
    public AjaxResponse setGetCacheValue(){
        StringBuilder result = new StringBuilder();

        //1:缓存操作对象String：放入缓存，去除缓存，成功
        String robotName = "Chat Robot Instance 1." ;
        log.debug("###############");
        log.debug("放入缓存对象为：{}",robotName);
        result.append("[").append("放入缓存对象为：").append(robotName).append("]");
        redisCache.setValue(CACHE_STRING_KEY,robotName,60*5);
        robotName = redisCache.getValue(CACHE_STRING_KEY,String.class);
        log.debug("取得缓存对象为：{}",robotName);
        result.append("[").append("取得缓存对象为:").append(robotName).append("]");

        //2:缓存操作对象User：放入缓存，去除缓存，成功
        result.append("###############");
        log.debug("###############");
        User user = new User("程云",36) ;
        log.debug("放入缓存对象为：{}",user);
        result.append("[").append("放入缓存对象为：").append(user).append("]");
        redisCache.setValue(CACHE_OBJECT_KEY,user,60*5);
        user = redisCache.getValue(CACHE_OBJECT_KEY,User.class);
        log.debug("取得缓存对象为：{}",user);
        result.append("[").append("取得缓存对象为:").append(user).append("]");

        //3:缓存操作对象User的List：放入缓存，去除缓存，成功
        result.append("###############");
        log.debug("###############");
        List<User> userList = new LinkedList<>();
        userList.add(new User("程云",36));
        userList.add(new User("孟丽丽",37));
        userList.add(new User("张媛媛",26));
        log.debug("放入缓存对象为：{}",userList);
        result.append("[").append("放入缓存对象为：").append(userList).append("]");
        redisCache.setValue(CACHE_LIST_KEY,userList,60*5);
        userList = redisCache.getValue(CACHE_LIST_KEY,List.class);
        log.debug("取得缓存对象为：{}",userList);
        result.append("[").append("取得缓存对象为:").append(userList).append("]");

        //4:缓存操作对象User的Map:放入缓存，去除缓存，成功
        result.append("###############");
        log.debug("###############");
        Map<String,User> userMap = new HashMap<>();
        userMap.put("程云",new User("程云",36));
        userMap.put("孟丽丽",new User("孟丽丽",37));
        userMap.put("张媛媛",new User("张媛媛",26));
        log.debug("放入缓存对象为：{}",userMap);
        result.append("[").append("放入缓存对象为：").append(userMap).append("]");
        redisCache.setValue(CACHE_MAP_KEY,userMap,60*5);
        userMap = redisCache.getValue(CACHE_MAP_KEY,Map.class);
        log.debug("取得缓存对象为：{}",userMap);
        result.append("[").append("取得缓存对象为:").append(userMap).append("]");

        //5:两层对象封装:放入缓存，去除缓存，成功
        result.append("###############");
        log.debug("###############");
        User userAjax = new User("程云",36);
        AjaxResponse<User> successUserAjax = AjaxResponse.success(userAjax, "创建成功");
        log.debug("放入缓存对象为：{}",successUserAjax);
        result.append("[").append("放入缓存对象为：").append(successUserAjax).append("]");
        redisCache.setValue(CACHE_PACKAGE_OBJECT_KEY,successUserAjax,60*5);
        successUserAjax = redisCache.getValue(CACHE_PACKAGE_OBJECT_KEY,AjaxResponse.class);
        log.debug("取得缓存对象为：{}",successUserAjax);
        result.append("[").append("取得缓存对象为:").append(successUserAjax).append("]");

//        try {
//            Thread.sleep(59*5*1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        log.debug("========================================");
//        log.debug("取得缓存对象为：{}",redisCache.getValue(CACHE_STRING_KEY,String.class));
//        log.debug("取得缓存对象为：{}",redisCache.getValue(CACHE_OBJECT_KEY,User.class));
//        log.debug("取得缓存对象为：{}",redisCache.getValue(CACHE_LIST_KEY,List.class));
//        log.debug("取得缓存对象为：{}",redisCache.getValue(CACHE_MAP_KEY,Map.class));
//        log.debug("取得缓存对象为：{}",redisCache.getValue(CACHE_PACKAGE_OBJECT_KEY,AjaxResponse.class));

        return AjaxResponse.success(result);
    }



}
