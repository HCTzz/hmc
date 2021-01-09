package com.ctt.web.config;

import com.ctt.web.interceptor.SqlLogInterceptor;
import io.lettuce.core.RedisClient;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Description
 * @auther HHF
 * @create 2020-05-21 下午 5:30
 */
@Configuration
public class GlobalConfig {

//    @Bean
//    ConfigurationCustomizer mybatisConfigurationCustomizer() {
//        return new ConfigurationCustomizer() {
//            @Override
//            public void customize(org.apache.ibatis.session.Configuration configuration) {
//                configuration.addInterceptor(new SqlLogInterceptor());
//            }
//        };
//    }

}
