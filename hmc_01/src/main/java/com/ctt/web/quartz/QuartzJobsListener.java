package com.ctt.web.quartz;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.ctt.web.quartz.job.TestJob;
import org.quartz.SchedulerException;
import org.quartz.ee.servlet.QuartzInitializerListener;


/**
 * @author Administrator
 */
public class QuartzJobsListener extends QuartzInitializerListener {

	@Override
    public void contextDestroyed(ServletContextEvent context){
        QuartzJobsFactory factory = QuartzJobsFactory.getInstance();
        try {
			factory.getScheduler().shutdown(true);
		}catch (SchedulerException e) {
			e.printStackTrace();
		}
    }

    /**
               *  容器初始化时操作
     */
    @Override
    public void contextInitialized(ServletContextEvent event){
    	System.setProperty("org.terracotta.quartz.skipUpdateCheck","true");
        super.contextInitialized(event);
        ServletContext context = event.getServletContext();
        QuartzJobsFactory factory = QuartzJobsFactory.getInstance();
        try{
            factory.getScheduler().getContext().put("context", context);
        }catch (SchedulerException e){
        }
        QuartzJobsManage taskQuartzManager = QuartzJobsManage.getInstance();
        //添加定时任务
//        taskQuartzManager.addJob("test","testKey", TestJob.class, "0 */1 * * * ?");
    }
}
