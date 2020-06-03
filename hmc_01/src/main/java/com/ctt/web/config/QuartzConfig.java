package com.ctt.web.config;

import com.ctt.web.quartz.QuartzJobsListener;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
	
//	@Bean
//    public QuartzInitializerListener executorListener() {
//	    return new QuartzJobsListener();
//    }

//    @Bean(name="SchedulerFactory")
//     public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
//         SchedulerFactoryBean factory = new SchedulerFactoryBean();
//         factory.setQuartzProperties(quartzProperties());
//         return factory;
//     }

     /**
      * 加载Quartz配置
       *
       */
//     @Bean
//    public Properties quartzProperties() throws IOException {
//        //使用Spring的PropertiesFactoryBean对属性配置文件进行管理
//        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz_config.properties"));
//        propertiesFactoryBean.afterPropertiesSet();
//
//        Properties properties = propertiesFactoryBean.getObject();
//
//        // 账号密码解密f
//        Crypter crypter = CrypterFactory.getCrypter(CrypterFactory.AES_CBC);
//        String user = properties.getProperty("org.quartz.dataSource.qzDS.user");
//        if (user != null) {
//            user = crypter.decrypt(user);
//            properties.setProperty("org.quartz.dataSource.qzDS.user", user);
//        }
//        String password = properties.getProperty("org.quartz.dataSource.qzDS.password");
//        if (password != null) {
//            password = crypter.decrypt(password);
//            properties.setProperty("org.quartz.dataSource.qzDS.password", password);
//        }
//       return properties;
//    }

    /**
    * 通过SchedulerFactoryBean获取Scheduler的实例
    */
//    @Bean(name="Scheduler")
//    public Scheduler scheduler() throws IOException {
//        return schedulerFactoryBean().getScheduler();
//    }
}
