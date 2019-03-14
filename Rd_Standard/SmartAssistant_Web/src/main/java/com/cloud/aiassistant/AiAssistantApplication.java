package com.cloud.aiassistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * ChatBot聊天机器人主入口
 * @author Cloud
 */
@SpringBootApplication
@EnableScheduling
@EnableTransactionManagement
//@ComponentScan(basePackages = "com.cloud.aiassistant")  //扫描包配置：如果不是相同路径，则需要配置
public class AiAssistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiAssistantApplication.class, args);
	}
}
