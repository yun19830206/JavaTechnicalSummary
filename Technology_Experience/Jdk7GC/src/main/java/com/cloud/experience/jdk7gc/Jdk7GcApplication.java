package com.cloud.experience.jdk7gc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Cloud
 */
@SpringBootApplication
@EnableScheduling
public class Jdk7GcApplication {

	public static void main(String[] args) {
		SpringApplication.run(Jdk7GcApplication.class, args);
	}
}
