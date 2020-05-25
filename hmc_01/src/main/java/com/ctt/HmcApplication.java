package com.ctt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(value = "com.ctt.*")
@MapperScan(basePackages = "com.ctt.web.mapper")
public class HmcApplication {

    public static void main(String[] args) {
        SpringApplication.run(HmcApplication.class, args);
    }

}
