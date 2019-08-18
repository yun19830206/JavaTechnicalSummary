package com.cloud.aiassistant.core.cache.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.aiassistant.core.cache.ICacheInterface;
import com.cloud.aiassistant.pojo.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 操作Redis缓存接口实现类<br/>
 * https://blog.csdn.net/z_alvin/article/details/79321330
 * @author ChengYun
 * @date 2019/5/9  Vesion 1.0
 */
@Component
@Slf4j
public class RedisCacheImpl implements ICacheInterface {

    @Autowired
    private StringRedisTemplate redisTemplate ;
    // redisTemplate.opsForValue();//操作字符串      redisTemplate.opsForValue().get("name")
    // redisTemplate.opsForHash();//操作hash        redisTemplate.opsForHash().entries("map1");.opsForHash().values("map1");.opsForHash().keys("map1");.opsForHash().get("map1", "key1");
    // redisTemplate.opsForList();//操作list        (List<String>) redisTemplate.opsForList().leftPop("listkey1");
    // redisTemplate.opsForSet();//操作set          redisTemplate.opsForSet().members("set1")
    // redisTemplate.opsForZSet();//操作有序set


    @Override
    public <T> void setValue(String key, T value, long timeOutSeconds) {
        if(null == key || key.length()<1 || null == value ){
            return ;
        }
        String valueJson = JSONObject.toJSONString(value);
        redisTemplate.opsForValue().set(key,valueJson,timeOutSeconds, TimeUnit.SECONDS);
        log.debug("Redis Cache Log: set <{},{}> into cache in {} seconds success.",key,value.getClass(),timeOutSeconds);
    }

    @Override
    public <T> T getValue(String key, Class<T> clazz) {
        if(null == key || key.length()<1 ){
            return null ;
        }
        String stringValue = redisTemplate.opsForValue().get(key);
        if(null == stringValue || stringValue.length()<1){
            return null ;
        }
        T returnValue = JSONObject.parseObject(stringValue,clazz);
        log.debug("Redis Cache Log: get {} from cache success, return{}.",key, returnValue.getClass());
        return returnValue;
    }


    /************************如下接口非公开，仅用于验证Java存储Redis各种数据特征的功能(一般不开放此种使用方法)*****************/
    /** TODO 需要把Set List Hash 的放入值Value替换成 String在试试，或者转成JSONString*/
    public void setMapDemo(){
        /** 报错验证不成功：java.lang.ClassCastException: com.cloud.aiassistant.stagging.pojo.User cannot be cast to java.lang.String */
        log.debug("###############验证opsForHash操作Map类型对象##############");
        Map<String,User> userMap = new HashMap<>();
        userMap.put("程云",new User("程云",36L));
        userMap.put("孟丽丽",new User("孟丽丽",37L));
        userMap.put("张媛媛",new User("张媛媛",26L));
        log.debug("放入缓存Map对象为：{}",userMap);
        redisTemplate.opsForHash().putAll("cache:demo:type:map",userMap);
        //opsForHash()操作方法，必须额外指派失效时间
        redisTemplate.expire("cache:demo:type:map",60*5,TimeUnit.SECONDS);
        log.debug("获得缓存Map对象合集为：{}",redisTemplate.opsForHash().entries("cache:demo:type:map"));
        log.debug("获得缓存Map对象Value合集为：{}",redisTemplate.opsForHash().values("cache:demo:type:map"));
        log.debug("获得缓存Map对象Key合集为：{}",redisTemplate.opsForHash().keys("cache:demo:type:map"));
        log.debug("获得缓存Map对象程云key对应的Value值：{}",redisTemplate.opsForHash().get("cache:demo:type:map","程云"));
    }

    public void setListDemo(){
        /** 报错验证不成功：java.lang.ClassCastException: com.cloud.aiassistant.stagging.pojo.User cannot be cast to java.lang.String */
        log.debug("###############验证opsForList操作List类型对象##############");
        List<User> userList = new LinkedList<>();
        userList.add(new User("程云",36L));
        userList.add(new User("孟丽丽",37L));
        userList.add(new User("张媛媛",26L));
        log.debug("放入缓存List对象为：{}",userList);
        redisTemplate.opsForList().leftPushAll("cache:demo:type:list",(Collection)userList);
        log.debug("获得缓存List对象合集为：{}",redisTemplate.opsForList().leftPop("cache:demo:type:list"));

        redisTemplate.boundSetOps("sdf");
    }
}
