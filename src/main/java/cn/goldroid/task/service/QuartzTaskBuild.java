/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.service;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

/**
 *
 * @author changshu.li
 */
public class QuartzTaskBuild {

	private final JobBuilder jobBuilder = JobBuilder.newJob();
	private final TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger().startNow();

	public static QuartzTaskBuild NEW() {
		return new QuartzTaskBuild();
	}

	public QuartzTaskBuild newJob(Class<? extends Job> jobclass) {
		jobBuilder.ofType(jobclass);
		return this;
	}

	public QuartzTaskBuild jobIdentity(String name, String group) {
		jobBuilder.withIdentity(name, group);
		return this;
	}

	public QuartzTaskBuild triggerIdentity(String name, String group) {
		triggerBuilder.withIdentity(name, group);
		return this;
	}

	public void withCronSchedule(String cronex) {
		triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cronex));
	}

	public void start(Scheduler sched) throws SchedulerException {
		sched.scheduleJob(jobBuilder.build(), triggerBuilder.build());
	}
}
