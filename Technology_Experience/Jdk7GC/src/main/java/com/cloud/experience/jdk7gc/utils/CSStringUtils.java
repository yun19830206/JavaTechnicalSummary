package com.cloud.experience.jdk7gc.utils;

/**
 * 一个工具类，用于模拟真实的调用堆栈
 * @author ChengYun
 * @date 2018/10/27 Version 1
 */
public class CSStringUtils {

    private CSStringUtils() {
        /** 工具类，不允许New 自己 */
    }

    /** 模拟一个字符串拼接的工具类方法 */
    public static String connectString(long number , String string){
        return string==null?number+"":number+string ;
    }

}
