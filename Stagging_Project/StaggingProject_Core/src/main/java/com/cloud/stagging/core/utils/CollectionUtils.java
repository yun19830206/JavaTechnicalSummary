package com.cloud.stagging.core.utils;

import java.util.Collection;
import java.util.List;

/**
 * 集合工具类
 * @author ChengYun
 * @date 2019/9/17  Vesion 1.0
 */
public class CollectionUtils {

    private CollectionUtils(){}

    /**
     * 判断一个结合是否为空。
     * @param conllection 集合
     * @return 为空获得null返回ture,不为空返回false
     */
    public static boolean isEmpty(Collection conllection){
        if(null == conllection || conllection.size() < 1 ){
            return true;
        }
        return  false ;
    }
}
