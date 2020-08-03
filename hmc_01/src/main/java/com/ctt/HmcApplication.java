package com.ctt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
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
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@SpringBootApplication
@EnableScheduling
@ComponentScan(value = "com.ctt.*")
@MapperScan(basePackages = "com.ctt.web.mapper")
@EnableDiscoveryClient
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
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        reentrantLock.unlock();
        Condition condition = reentrantLock.newCondition();
        SpringApplication.run(HmcApplication.class, args);
    }

}
