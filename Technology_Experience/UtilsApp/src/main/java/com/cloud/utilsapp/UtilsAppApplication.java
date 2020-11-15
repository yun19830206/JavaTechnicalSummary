package com.cloud.utilsapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class UtilsAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(UtilsAppApplication.class, args);
    }

}
