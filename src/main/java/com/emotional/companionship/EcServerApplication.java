package com.emotional.companionship;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 高端情感陪伴App后端服务主应用类
 */
@SpringBootApplication
@MapperScan("com.emotional.companionship.mapper")
public class EcServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcServerApplication.class, args);
    }
}   