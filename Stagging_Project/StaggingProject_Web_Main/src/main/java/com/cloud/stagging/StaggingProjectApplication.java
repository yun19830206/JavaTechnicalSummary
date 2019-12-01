package com.cloud.stagging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * 脚手架SpringBoot工程主应用程序入口
 * @author Cloud
 * @date 2019/9/13  Vesion 1.0
 */
//@ComponentScan(basePackages = "com.cloud.stagging")  //扫描包配置：如果不是相同路径，则需要配置
@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
public class StaggingProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(StaggingProjectApplication.class, args);
	}
}
