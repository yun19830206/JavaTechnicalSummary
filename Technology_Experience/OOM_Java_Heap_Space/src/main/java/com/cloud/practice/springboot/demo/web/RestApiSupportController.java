package com.cloud.practice.springboot.demo.web;

import com.cloud.practice.springboot.demo.core.HoldThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChengYun on 2017/9/7 Version 1.0
 */
@RestController
public class RestApiSupportController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Map<String,Object> bigMemoryObject = new HashMap<>();

    /**
     * 创建一个不能被GC回收的大对象，这种操作方式，特别需要注意，在代码中需要避免
     */
    @RequestMapping(path = "/createOneCanotGCBigObject")
    @ResponseBody
    public Map createOneCanotGCBigObject() throws UnsupportedEncodingException {

        byte[] bytes = new byte[1028*1024*700];
        bigMemoryObject.put("bigObject",bytes);

        Map<String,String> retrunMap = new HashMap<>();
        retrunMap.put("result","创建不可回收大对象成功");
        logger.info("/createOneCanotGCBigObject(创建能被GC的大对象)调用成功");
        return retrunMap ;
    }

    /**
     * 创建一个不能被GC回收的小对象，这种操作方式，特别需要注意，在代码中需要避免
     */
    @RequestMapping(path = "/createOneCanotGC10MObject")
    @ResponseBody
    public Map createOneCanotGC10MObject() throws UnsupportedEncodingException {

        byte[] bytes = new byte[1028*1024*10];
        bigMemoryObject.put("smallObject"+System.currentTimeMillis(),bytes);

        Map<String,String> retrunMap = new HashMap<>();
        retrunMap.put("result","创建不可回收小对象10M成功");
        logger.info("/createOneCanotGC10MObject(创建不可回收小对象10M)调用成功");
        return retrunMap ;
    }

    /**
     * 创建一个能被GC回收的对象，这种对象的存货周期只存在方法体当中
     */
    @RequestMapping(path = "/createOneCanBeGCBigObject")
    @ResponseBody
    public Map createOneCanBeGCBigObject() throws UnsupportedEncodingException {

        byte[] bytes = new byte[1028*1024*700];

        Map<String,String> retrunMap = new HashMap<>();
        retrunMap.put("result","创建能回收大对象成功");

        logger.info("/createOneCanBeGCBigObject(创建能被GC的大对象)调用成功");
        return retrunMap ;
    }

    /**
     * 一个正常的接口
     */
    @RequestMapping(path = "/normalRequest")
    @ResponseBody
    public Map normalRequest() throws UnsupportedEncodingException {
        Map<String,String> retrunMap = new HashMap<>();
        retrunMap.put("result","正常小接口被调用");

        logger.info("/normalRequest(正常小接口被调用)调用成功");
        return retrunMap ;
    }

    /**
     * 创建一万个无法结束的线程的接口
     */
    @RequestMapping(path = "/debugCreateMaxThread")
    @ResponseBody
    public Map debugCreateMaxThread() throws UnsupportedEncodingException {

        for (int i = 0;i < 100000; i++) {
            logger.info("i = " + i);
            new Thread(new HoldThread()).start();
        }

        Map<String,String> retrunMap = new HashMap<>();
        retrunMap.put("result","创建一万个无法结束的线程的接口");
        return retrunMap ;
    }

    /**
     * 一个耗时50秒钟的接口
     */
    @RequestMapping(path = "/usedFiftySecondApi")
    @ResponseBody
    public Map usedFiftySecondApi() throws UnsupportedEncodingException, InterruptedException {

        Thread.sleep(1000*50);

        Map<String,String> retrunMap = new HashMap<>();
        retrunMap.put("result","一个耗时50秒钟的接口");
        return retrunMap ;
    }

    /**
     * 一个方法调用级别的会无限循环的接口调用
     */
    @RequestMapping(path = "/stackOver")
    @ResponseBody
    public Map stackOver() throws UnsupportedEncodingException, InterruptedException {

        this.stackOverFunction(0);

        Map<String,String> retrunMap = new HashMap<>();
        retrunMap.put("result","一个会方法调用级别的无限循环的接口调用");
        return retrunMap ;
    }

    private void stackOverFunction(int count) {
        logger.debug("stackOverFunction:"+count);
        count++;
        stackOverFunction(count);
    }


}
