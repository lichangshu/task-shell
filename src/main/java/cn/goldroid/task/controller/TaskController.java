/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.controller;

import cn.goldroid.task.domain.TimerTask;
import cn.goldroid.task.service.QuartzService;
import cn.goldroid.task.service.TimerTaskService;
import cn.goldroid.task.util.AjaxMessage;
import java.text.ParseException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.*;
import org.quartz.CronExpression;
import org.quartz.SchedulerException;

/**
 *
 * @author changshu.li
 */
@At("/task")
@IocBean
public class TaskController extends BaseModule {

	private static final Log logger = LogFactory.getLog(TaskController.class);
	@Inject
	TimerTaskService timerTaskService;
	@Inject
	QuartzService quartzService;

	@At("/list")
	@Ok("jsp:jsp/task_list")
	public void listTask() {
	}

	@At("/list/json")
	@Ok("json")
	public List<TimerTask> listTask(@Param("page") Integer page, @Param("size") Integer size) {
		List<TimerTask> list = timerTaskService.listRunableTask(page, size);
		return list;
	}

	@At("/remove")
	@Ok("raw:js")
	@AdaptBy(type = JsonAdaptor.class)
	public String removeTask(@Param("id") int[] ids) {
		for (int id : ids) {
			TimerTask tm = timerTaskService.getById(id);
			if (tm != null) {
				timerTaskService.remove(tm.getId());
				try {
					quartzService.removeTask(tm);
				} catch (SchedulerException ex) {
					logger.error("Remove task error!", ex);
				}
			}
		}
		return new AjaxMessage().setData(ids).toJsonString();
	}

	@At("/edit")
	@POST
	@Ok("raw:js")
	@AdaptBy(type = JsonAdaptor.class)
	public String editTask(TimerTask task) {
		TimerTask dbtask = timerTaskService.getById(task.getId());
		if (dbtask != null) {
			try {
				checkCron(task);
				timerTaskService.update(task);
				addOrOverWriteTask(task);
				return new AjaxMessage().setData(task).toJsonString();
			} catch (ParseException ex) {
				return new AjaxMessage().addError("Time 解析有误：" + ex.getMessage()).setData(task).toJsonString();
			}
		} else {
			return new AjaxMessage().addError("Not found task !").toJsonString();
		}
	}

	@At("/add")
	@POST
	@Ok("raw:js")
	@AdaptBy(type = JsonAdaptor.class)
	public String addTask(TimerTask task) {
		try {
			task.setId(0);
			checkCron(task);
			timerTaskService.insert(task);
			addOrOverWriteTask(task);
			return new AjaxMessage().setData(task).toJsonString();
		} catch (ParseException ex) {
			return new AjaxMessage().addError("Time 解析有误：" + ex.getMessage()).setData(task).toJsonString();
		}
	}

	private CronExpression checkCron(TimerTask task) throws ParseException {
		String tm = task.getTimer();
		if (tm != null) {
			return new CronExpression(tm);
		}
		throw new ParseException(tm, 0);
	}

	private void addOrOverWriteTask(TimerTask task) {
		try {
			quartzService.addOrOverWriteTask(task);
		} catch (SchedulerException ex) {
			logger.error("Add task error!", ex);
		}
	}
}
