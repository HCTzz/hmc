package com.ctt.web.config;

import com.ctt.web.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description
 * @auther HHF
 * @create 2020-05-15 下午 4:42
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public AuthInterceptor authInterceptor(){
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则，/**表示拦截所有请求
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(authInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/user/login","/user/logout","/video/list"
                ,"/video/priviewVideo","/vlog/list","/vlog/getVlog","/photo/photoList"
                ,"/photo/photoList","/sysFile/filelist","/sysFile/getFile","/sysFile/priviewImg"
                );
    }


}
