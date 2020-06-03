package com.ctt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@SpringBootApplication
@EnableScheduling
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(value = "com.ctt.*")
@MapperScan(basePackages = "com.ctt.web.mapper")
public class HmcApplication {

//    interface  TestService{
//        void test();
//    }

    public static void main(String[] args) {
//        Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class[]{TestService.class},new InvocationHandler() {
//
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                return method.invoke(proxy,args);
//            }
//        });
        SpringApplication.run(HmcApplication.class, args);
    }

}
