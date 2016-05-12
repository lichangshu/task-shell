/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.service;

import cn.goldroid.task.domain.TimerTask;
import cn.goldroid.task.support.RunCmdTask;
import cn.goldroid.task.support.TaskManager;
import cn.goldroid.task.util.CircularList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author changshu.li
 */
@IocBean
public class QuartzService {

	private final static Log logger = LogFactory.getLog(QuartzService.class);
//	private final static Map<Integer, JobDetail> keymap = new ConcurrentHashMap();
	private final static Map<Integer, TimerTask> jobdata = new ConcurrentHashMap();

	@Inject
	TimerTaskService timerTaskService;
	Scheduler scheduler;

	public QuartzService() throws SchedulerException {
		scheduler = new StdSchedulerFactory().getScheduler();
		scheduler.start();
	}

	public void init() {
		List<TimerTask> list = timerTaskService.listEnableRunableTask(0, 100);
		for (TimerTask i : list) {
			try {
				this.addOrOverWriteTask(i);
			} catch (SchedulerException ex) {
				logger.error("Init add task error!", ex);
			}
		}
		if (list.size() == 100) {
			logger.warn("Max manage task is 100 !!! over flow will skip !");
		}
	}

	public List<TimerTask> listTask() {
		return new ArrayList(jobdata.values());
	}

	public List<RunCmdTask> getTaskRunLog(TimerTask task) {
		return TaskJob.getTaskLog(task);
	}

	public void addOrOverWriteTask(TimerTask task) throws SchedulerException {
		JobDetail job = TaskJob.createJobDetail(task);
		this.removeTask(task);//删除
		jobdata.put(task.getId(), task);
		scheduler.scheduleJob(job, this.createTrigger(task));
	}

	public boolean removeTask(TimerTask task) throws SchedulerException {
		TriggerKey key = createTriggerKey(task);
		return scheduler.unscheduleJob(key);
	}

	private Trigger createTrigger(TimerTask task) {
		return TriggerBuilder.newTrigger()
				.startNow()
				.withSchedule(CronScheduleBuilder.cronSchedule(task.getTimer()))
				.withIdentity(createTriggerKey(task)).build();
	}

	private TriggerKey createTriggerKey(TimerTask task) {
		return new TriggerKey("TimeTask-id-" + task.getId(), "TIMER_TASK_TRIGGER_GROUP");
	}

	private static JobKey createJobKey(TimerTask task) {
		return new JobKey("job-" + task.getId(), "TIMER_TASK_JOB_GROUP");
	}

	public static class TaskJob implements Job {

		private final static String TASK_KEY = "TIMER_TASK_KEY_ID";
		public static final int LOG_HISTORY_MAX = 10;
		private final static Map<Integer, CircularList<String>> tasklist = new ConcurrentHashMap();

		private static JobDetail createJobDetail(TimerTask task) {
			JobDetail detail = JobBuilder.newJob(TaskJob.class)
					.usingJobData(TASK_KEY, task.getId())
					.withIdentity(createJobKey(task))
					.build();
			return detail;
		}

		public static List<RunCmdTask> getTaskLog(TimerTask task) {
			List<RunCmdTask> rst = new ArrayList();
			CircularList<String> tokens = tasklist.get(task.getId());
			if (tokens == null) {
				return rst;
			}
			for (CircularList<String>.CircularListNode k : tokens.getFrom(0)) {
				rst.add(TaskManager.getTaskManager().getTask(k.getNode()));
			}
			return rst;
		}

		@Override
		public void execute(JobExecutionContext context) throws JobExecutionException {
			Integer id = (Integer) context.getJobDetail().getJobDataMap().get(TASK_KEY);
			logger.info("start task ! task id :" + id);
			TimerTask task = jobdata.get(id);
			if (task != null && task.isEnable()) {
				try {
					if (tasklist.get(id) == null) {
						tasklist.put(id, new CircularList(LOG_HISTORY_MAX, new CopyOnWriteArrayList()));
					}
					String token = TaskManager.getTaskManager().runTask(500, task.getRundir(),task.getCmd());
					logger.info("Runging cmd :" + task.getCmdText());
					tasklist.get(id).push(token);
				} catch (IOException ex) {
					logger.error(String.format("Cmd error! id:[%s] for cmd:[%s]", task.getId(), task.getCmdText()), ex);
					throw new JobExecutionException(ex.getMessage(), ex);
				}
			}
		}

	}
}
