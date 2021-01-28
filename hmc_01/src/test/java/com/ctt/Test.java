package com.ctt;

import com.ctt.web.bean.Photo;
import com.ctt.web.service.PhotoService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @Description
 * @auther HHF
 * @create 2020-06-02 下午 3:49
 */
public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.refresh();
        PhotoService service = context.getBean(PhotoService.class);


    }

    @Bean
    public PhotoService photoService(){
        return new PhotoService();
    }

}
