package com.cloud.practice.springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 创建一个Spring Boot用于验证OOM类型的问题
 * Created by ChengYun on 2017/6/13 Vesion 1.0
 */
@SpringBootApplication
//public class OOMJavaHeapSpace extends SpringBootServletInitializer {   //这种是打包成war包，给Tomcat部署的情况
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(OOMJavaHeapSpace.class);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(OOMJavaHeapSpace.class, args);
//    }
//
//}
public class OOMJavaHeapSpace {

    public static void main(String[] args) {
        SpringApplication.run(OOMJavaHeapSpace.class, args);
    }

}
