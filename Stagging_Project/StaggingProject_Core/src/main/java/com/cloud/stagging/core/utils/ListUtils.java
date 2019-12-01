package com.cloud.stagging.core.utils;

import java.util.List;

/**
 * List集合工具类
 * @author ChengYun
 * @date 2019/9/17  Vesion 1.0
 */
public class ListUtils {

    private ListUtils(){}

    /**
     * 判断一个结合是否为空。
     * @param list 集合
     * @return 为空获得null返回ture,不为空返回false
     */
    public static boolean isEmpty(List list){
        if(null == list || list.size() < 1 ){
            return true;
        }
        return  false ;
    }
}
