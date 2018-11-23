package com.cloud.experience.jdk7gc.web;

import com.cloud.experience.jdk7gc.pojo.ApiResponse;
import com.cloud.experience.jdk7gc.service.BusinessService;
import lombok.Data;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.omg.SendingContext.RunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * 此Controller模拟一个业务对象接口，只有一个Restful接口，调用一次会往一个全集容器中增加一个10M的对象，这个容器达到100个是会丢弃GC跟引用(也就是可以被GC回收)
 * @author ChengYun
 * @date 2018/10/26 Version 1
 */
@RestController
@RequestMapping("/jdk7gcperformance")
@Slf4j
public class BusinessController {

    private final BusinessService businessService ;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
        //查看JVM当前运行的GC类型
        List<GarbageCollectorMXBean> garbageCollectorMXBeanList = ManagementFactory.getGarbageCollectorMXBeans();
        for(GarbageCollectorMXBean garbageCollectorMXBean:garbageCollectorMXBeanList){
            String gcName = garbageCollectorMXBean.getName();
            log.debug("垃圾回收器的名称：" + gcName);
            log.debug("垃圾回收器已回收的总次数：" + garbageCollectorMXBean.getCollectionCount());
            log.debug("垃圾回收器已回收的总时间：" + garbageCollectorMXBean.getCollectionTime()/1000+"秒");
        }
    }

    /** 模拟一个Http接口，会产生不会立马被GC回收的对象 */
    @GetMapping("/dobusiness")
    public ApiResponse doBusiness(){

        long startTime = System.currentTimeMillis();
        businessService.doBusiness();
        long endTime = System.currentTimeMillis();
       return new ApiResponse.ApiResponseBuilder().code(ApiResponse.DEFAULT_CODE).message("调用成功").data((endTime-startTime)+"ms").build();
    }
}
