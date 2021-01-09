package com.ctt;

import lombok.EqualsAndHashCode;
import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.annotation.MapperScan;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@SpringBootApplication
@EnableScheduling
@ComponentScan(value = "com.ctt.*")
@MapperScan(basePackages = "com.ctt.web.mapper")
public class HmcApplication {

//    interface  TestService{
//        void test();
//    }

    public static void main(String[] args) throws IOException, InterruptedException {
//        Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),new Class[]{TestService.class},new InvocationHandler() {
//
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                return method.invoke(proxy,args);
//            }
//        });
//        ReentrantLock reentrantLock = new ReentrantLock();
//        reentrantLock.lock();
//        reentrantLock.unlock();
//        Condition condition = reentrantLock.newCondition();
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(0).setPassword("123456");
//        RedissonClient client = Redisson.create(config);
//        RLock lock = client.getLock("lock");
//        boolean locked = lock.tryLock(15, TimeUnit.SECONDS);
//        Thread.sleep(10 * 1000);
//        lock.unlock();
        SpringApplication.run(HmcApplication.class, args);
    }

}
