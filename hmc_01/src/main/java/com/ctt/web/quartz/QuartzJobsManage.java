package com.ctt.web.quartz;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;


public class QuartzJobsManage {

	private static final QuartzJobsManage taskQuartzManager = new QuartzJobsManage();
	private static String JOB_GROUP_NAME = "HRM_JOBGROUP_NAME";
	private static String TRIGGER_GROUP_NAME = "HRM_TRIGGERGROUP_NAME";

	private QuartzJobsManage() {
	}

	public static QuartzJobsManage getInstance() {
		return taskQuartzManager;
	}

	/**
	 * 添加任务
	 */
	@SuppressWarnings("unchecked")
	public void addJob(String jobName,String triggerName, Class<? extends JncStateJob> cls, String time) {
		try {
			Scheduler sched = QuartzJobsFactory.getDefaultScheduler();
			//定义一个Trigger
			Trigger trigger = getTrigger(triggerName,time);
           //定义一个JobDetail
			JobDetail jobDetail = getJob(jobName, cls);
			sched.scheduleJob(jobDetail, trigger);
			if (!sched.isShutdown()) {
				sched.start();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 修改任务触发时间
	 * 
	 * @param jobName
	 * @param time
	 */
	@SuppressWarnings("unchecked")
	public void modifyJobTime(String triggerName, String time) {
		try {
			TriggerKey key = TriggerKey.triggerKey(triggerName, TRIGGER_GROUP_NAME);
			Scheduler sched = QuartzJobsFactory.getDefaultScheduler();
			Trigger trigger = getTrigger(triggerName, time);
			sched.rescheduleJob(key, trigger);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 移出定时任务
	 * @param jobName
	 */
	public void removeJob(String jobName) {
		try {
			Scheduler sched = QuartzJobsFactory.getDefaultScheduler();
			TriggerKey triggerkey = TriggerKey.triggerKey(jobName,JOB_GROUP_NAME);
			JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
			sched.pauseTrigger(triggerkey);
			sched.unscheduleJob(triggerkey);
			sched.deleteJob(jobKey);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private Trigger getTrigger(String triggerName,String time) {
		Trigger trigger = newTrigger().withIdentity(triggerName, TRIGGER_GROUP_NAME).startNow()
        		.withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
		return trigger;
	}
	
	private JobDetail getJob(String jobName,Class<? extends JncStateJob> cls) {
		JobDetail jobDetail = newJob(cls) 
                .withIdentity(jobName,JOB_GROUP_NAME) //定义name/group
                .build();
		return jobDetail;
	}
}
