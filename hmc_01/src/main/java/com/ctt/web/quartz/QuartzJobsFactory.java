package com.ctt.web.quartz;

import org.quartz.impl.StdSchedulerFactory;

public class QuartzJobsFactory extends StdSchedulerFactory{

	private QuartzJobsFactory(){}
    private static final QuartzJobsFactory factory = new QuartzJobsFactory();
    
    public static QuartzJobsFactory getInstance() {
        return factory;
    }
	
}
