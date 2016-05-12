/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author changshu.li
 */
public class CircularListTest {

	@Test
	public void testSize() {
		System.out.println("size");
		Object e = null;
		CircularList<Object> instance = new CircularList(1, new ArrayList());
		instance.push(e);
		instance.push(e);
		instance.push(e);
		assertEquals(instance.size(), 1);
	}

	@Test
	public void testGetFrom() {
		System.out.println("getFrom");
		//size 10 And push 55
		CircularList<Object> instance = new CircularList(10, new ArrayList());
		for (int i = 0; i < 55; i++) {
			instance.push(i);
		}
		// small from 1 Result size = 10
		List<CircularList<Object>.CircularListNode> result = instance.getFrom(1);
		assertEquals(result.size(), instance.size());
		// biger
		result = instance.getFrom(100000000);
		assertEquals(result.size(), 0);

		result = instance.getFrom(48);
		assertEquals(result.size(), 7);
		assertEquals(result.get(6).getNode(), 54);
	}
//
//	@Test
//	public void testQuartz() throws InterruptedException, SchedulerException {
//		Scheduler sched = new StdSchedulerFactory().getScheduler();
//		JobDetail job = JobBuilder.newJob(MyJob.class)
//				.withIdentity("myJob", "group1") // name "myJob", group "group1"
//				.build();
//		JobDetail job2 = JobBuilder.newJob(MyJob2.class)
//				.withIdentity("myJob2", "group1") // name "myJob", group "group1"
//				.build();
//
//		// Trigger the job to run now, and then every 40 seconds
//		TriggerBuilder<CronTrigger> triggerbuild = TriggerBuilder.newTrigger()
//				.startNow()
//				.withSchedule(CronScheduleBuilder.cronSchedule("*/3 * * ? * * *"));
//				//				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
//		//						.withIntervalInSeconds(2)
//		//						.repeatForever())
//		Trigger trigger = triggerbuild.withIdentity("myTrigger", "group1").build();
//		Trigger trigger2 = triggerbuild.withIdentity("myTrigger2", "group1").build();
//
//		// Tell quartz to schedule the job using our trigger
//		sched.scheduleJob(job, trigger);
//		sched.scheduleJob(job2, trigger2);
//		sched.start();
//		Thread.sleep(1);
//	}
//
//	public static class MyJob2 extends MyJob {
//
//	}
//
//	public static class MyJob implements Job {
//
//		public MyJob() {
//			System.out.println(this.getClass().getName() + "Create!" + new Date().toLocaleString() + Thread.currentThread().getId());
//		}
//
//		@Override
//		public void execute(JobExecutionContext context) throws JobExecutionException {
//			System.out.println(this.getClass().getName() + " start ! Hello = runing job!" + Thread.currentThread().getId());
//			try {
//				Thread.sleep(8000);
//			} catch (InterruptedException ex) {
//				Logger.getLogger(CircularListTest.class.getName()).log(Level.SEVERE, null, ex);
//			}
//			System.out.println(this.getClass().getName() + " over ! Hello = runing job!" + Thread.currentThread().getId());
//		}
//	}
}
