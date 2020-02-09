package com.cloud.experience.cachebreakdown.cache.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.experience.cachebreakdown.cache.ICacheInterface;
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
        log.debug("Redis Cache Log: get {} from cache success, return{}. ",key, returnValue.getClass());
        return returnValue;
    }

}
