/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.controller;

import cn.goldroid.task.domain.RunHistory;
import cn.goldroid.task.service.RunHistoryService;
import cn.goldroid.task.support.RunCmdTask;
import cn.goldroid.task.support.TaskManager;
import cn.goldroid.task.util.AjaxMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.adaptor.PairAdaptor;
import org.nutz.mvc.annotation.*;

/**
 *
 * @author changshu.li
 */
@At("/runing")
@IocBean
public class RunController extends BaseModule {

	private static final Log logger = LogFactory.getLog(RunController.class);
	@Inject
	RunHistoryService runHistoryService;

	@At("/history")
	@Ok("jsp:jsp/history_list")
	public void listTask() {
	}

	@At("/ajax/history")
	@Ok("raw:js")
	@AdaptBy(type = PairAdaptor.class)
	public String listJsonTask(@Param("page") Integer page, @Param("size") Integer size) {
		List<RunHistory> list = runHistoryService.listTaskHistory(page, size);
		return new AjaxMessage().setData(list).toJsonString();
	}

	@At("/list")
	@Ok("jsp:jsp/manage_list")
	public void listingCmd() {
	}

	@At("/ajax/remove")
	@Ok("raw:js")
	@AdaptBy(type = JsonAdaptor.class)
	public String runingRemove(@Param("rm") String[] rm) {
		for (String r : rm) {
			TaskManager.getTaskManager().removeTask(r);
		}
		return new AjaxMessage().setData(rm).toJsonString();
	}

	@At("/ajax/list")
	@Ok("raw:js")
	public String runingList() {
		List<RunHistory> result = new ArrayList();
		Map<String, RunCmdTask> taskmap = TaskManager.getTaskManager().listTask();
		for (String run : taskmap.keySet()) {
			RunCmdTask task = taskmap.get(run);
			RunHistory his = new RunHistory();
			his.setBatch(TaskManager.BATCH);
			his.setCmd(task.getCmd());
			his.setRunning(task.isRunning());
			his.setCreateTime(task.getStartTime());
			his.setTaskKey(run);
			result.add(his);
		}
		return new AjaxMessage().setData(result).toJsonString();
	}

	@At("/cmd")
	@GET
	@Ok("jsp:jsp/run_cmd")
	public void runCmd() {
	}

	@At("/cmd")
	@POST
	@Ok("raw:js")
	@AdaptBy(type = JsonAdaptor.class)
	public String runCmd(@Param("cmd") String... cmd) {
		AjaxMessage ajax = new AjaxMessage();
		try {
			String key = TaskManager.getTaskManager().runTask(cmd);
			RunCmdTask task = TaskManager.getTaskManager().getTask(key);
			RunHistory his = toTaskHistory(key, task);
			ajax.setData(runHistoryService.insert(his));
		} catch (IOException ex) {
			logger.error(ex.getMessage(), ex);
			ajax.addError("运行出错！" + ex.getMessage());
		}
		return ajax.toJsonString();
	}

	@At("/logs")
	@GET
	@Ok("jsp:jsp/logs")
	public RunCmdTask getLogs(@Param("key") String key) throws IOException {
		return TaskManager.getTaskManager().getTask(key);
	}

	@At("/loging")
	@GET
	@Ok("raw:js")
	public String getCmdLog(@Param("key") String key, @Param("from") Long from) throws IOException {
		RunCmdTask task = TaskManager.getTaskManager().getTask(key);
		if (from == null) {
			from = 0L;
		}
		if (task == null) {
			return new AjaxMessage().addError("This token commend is not find!").toJsonString();
		}
		boolean close = task.getCircularList().isClose();
		List list = task.getCircularList().getFrom(from);
		return new AjaxMessage().setData(list).addMessage(close ? "closed" : "runing").toJsonString();
	}

	@At("/shutdown")
	@Ok("raw:js")
	@AdaptBy(type = JsonAdaptor.class)
	public String shutdownCmd(@Param("key") String key) throws IOException {
		TaskManager.getTaskManager().shutdown(key);
		try {
			int i = TaskManager.getTaskManager().getTask(key).getProcess().exitValue();
			return new AjaxMessage().setData(Collections.singletonMap("exit", i)).toJsonString();
		} catch (IllegalThreadStateException ex) {
			return new AjaxMessage().addError("kill fail").toJsonString();
		}
	}

	private RunHistory toTaskHistory(String key, RunCmdTask task) {
		RunHistory his = new RunHistory();
		his.setBatch(TaskManager.BATCH);
		his.setCmd(task.getCmd());
		his.setCreateTime(task.getStartTime());
		his.setTaskKey(key);
		return his;
	}
}
