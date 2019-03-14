package com.cloud.aiassistant.core.utils;


import com.cloud.aiassistant.pojo.common.PageResult;
import com.github.pagehelper.PageInfo;

/**
 * 分页查询工具类
 * @author ChengYun
 * @date 2018/12/19 Version 1
 */
public class PageHelperUtils {

    private PageHelperUtils(){}

    public static <T> PageResult<T> getPageResult(PageInfo<T> pageinfo){
        return new PageResult<>(pageinfo);
    }
}
