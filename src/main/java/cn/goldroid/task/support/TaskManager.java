/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.support;

import cn.goldroid.task.util.RunCmdUtil;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author changshu.li
 */
public class TaskManager {

	private static final Log logger = LogFactory.getLog(TaskManager.class);
	public static final String BATCH = UUID.randomUUID().toString();
	private Map<String, RunCmdTask> tasklist = new ListOrderedMap();
	private static TaskManager manager = new TaskManager();

	private TaskManager() {
		System.out.print("start up !");
		Runtime run = Runtime.getRuntime();
		run.addShutdownHook(new Thread() {

			@Override
			public void run() {
				System.out.print("shut down");
			}
		});
	}

	public static TaskManager getTaskManager() {
		return manager;
	}

	public String runTask(String... cmd) throws IOException {
		return this.runTask(100, null, cmd);
	}

	public String runTask(int logsize, String rundir, String... cmd) throws IOException {
		String key = UUID.randomUUID().toString().toUpperCase();
		RunCmdTask task = new RunCmdTask(logsize, rundir, cmd);
		tasklist.put(key, task);
		task.run();
		return key;
	}

	public RunCmdTask getTask(String key) {
		return tasklist.get(key);
	}

	public TaskManager removeTask(String key) {
		tasklist.remove(key);
		return this;
	}

	public Map<String, RunCmdTask> listTask() {
		return Collections.unmodifiableMap(tasklist);
	}

	public void shutdown(String key) {
		RunCmdTask task = tasklist.get(key);
		if (task != null) {
			logger.info("shut down process :" + RunCmdUtil.join(task.getCmd(), " "));
			task.getProcess().destroy();
		}
	}

	public void shutdown() {
		logger.info("shut down All process : " + tasklist.size());
		for (String key : tasklist.keySet()) {
			RunCmdTask task = tasklist.get(key);
			logger.info("shut down process :" + RunCmdUtil.join(task.getCmd(), " "));
			task.getProcess().destroy();
		}
	}
}
