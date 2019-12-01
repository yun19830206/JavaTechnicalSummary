package com.cloud.stagging.core.cache;

/**
 * 缓存操作接口
 * @author ChengYun
 * @date 2019/5/9  Vesion 1.0
 */
public interface ICacheInterface {

    /**
     * 将Key、Value放入缓存
     * @param key                 缓存的key,规范为:域:资源:业务
     * @param value               缓存的对象
     * @param timeOutSeconds      缓存多少秒
     * @param <T>                 泛型
     */
    <T> void setValue(String key, T value, long timeOutSeconds);

    /**
     * 根据Key来获得缓存值
     * @param key           缓存的key,规范为:域:资源:业务
     * @param clazz         返回数据类型
     * @param <T>           泛型
     * @return              返回值
     */
    <T> T getValue(String key, Class<T> clazz);
}
