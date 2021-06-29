package com.cloud.stagging.core.utils;

import com.alibaba.fastjson.JSON;
import com.cloud.stagging.pojo.common.AjaxResponse;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;

/**
 * 返回结果工具类
 * @author ChengYun
 * @date 2020/4/12  Vesion 1.0
 */
@Slf4j
public class AjaxJsonResponseUtil {
    /**
     * 私有化构造器
     */
    private AjaxJsonResponseUtil(){}

    /**
     * 使用response输出JSON
     * @param response ServletResponse
     * @param ajaxResponse AjaxResponse
     */
    public static void responseJson(ServletResponse response, AjaxResponse ajaxResponse){
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSON.toJSONString(ajaxResponse));
        } catch (Exception e) {
            log.error("【JSON输出异常】"+e);
        }finally{
            if(out!=null){
                out.flush();
                out.close();
            }
        }
    }
}
