package com.cloud.experience.jdk7gc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * JDK8默认GC 与 CMS类型GC在WebApp中接口性能差异验证程序主入口
 * @author Cloud
 */
@SpringBootApplication
@EnableScheduling
public class Jdk7GcApplication {

	public static void main(String[] args) {
		SpringApplication.run(Jdk7GcApplication.class, args);
	}
}
