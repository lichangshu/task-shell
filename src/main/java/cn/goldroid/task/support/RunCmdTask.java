/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.support;

import cn.goldroid.task.util.CircularList;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author changshu.li
 */
public class RunCmdTask {

	private static final Log logger = LogFactory.getLog(RunCmdTask.class);
	ExecutorService pool = Executors.newCachedThreadPool();
	private ProcessBuilder builder;
	private Process process;
	private String[] cmd;
	private Date startTime;
	private Future future;

	private CircularList<String> circularList;

//	public RunCmdTask(RunableTask task) {
//		this(task, 200);
//	}
//	public RunCmdTask(RunableTask task, int size) {
//		this.task = task;
//		this.circularList = new CircularList<>(size, new ArrayList());
//		String param = task.getParam();
//		String[] pms = new String[]{};
//		if (param != null) {
//			param = param.trim();
//			String[] lines = param.split("\n");
//			if (lines.length > 0) {
//				pms = lines;
//			} else {
//				pms = param.replaceAll(" {2,}", "").split(" ");
//			}
//		}
//		List<String> plist = new LinkedList();
//		Collections.addAll(plist, pms);
//		plist.add(0, task.getCmd());
//	}
	public RunCmdTask(String... cmd) {
		this(200, null, cmd);
	}

	public RunCmdTask(int logsize, String dir, String... cmd) {
		this.circularList = new CircularList<>(logsize, new ArrayList());
		if (cmd != null && cmd.length == 1) {
			cmd = cmd[0].trim().split("\\p{Space}+");
		}
		this.cmd = cmd;
		this.builder = new ProcessBuilder(cmd);
		if (dir != null) {
			File file = new File(dir);
			if (file.exists()) {
				builder.directory(file);
			}
		}
	}

	public Process run() throws IOException {
		startTime = new Date();
		try {
			process = builder.start();
		} catch (IOException ex) {
			circularList.push("Run error : " + ex.getMessage()).closed();
			throw ex;
		}
		this.future = pool.submit(new Runnable() {

			@Override
			public void run() {
				try (InputStream inputstream = process.getInputStream();
						InputStream errorstream = process.getErrorStream()) {
//					InputStream inputstream = process.getInputStream();
//					outputstream = exe.getOutputStream();
//					InputStream errorstream = process.getErrorStream();
					BufferedReader read = new BufferedReader(new InputStreamReader(inputstream));
					while (true) {
						String line = read.readLine();
						if (line == null) {
							break;
						}
						circularList.push(line);
					}
					BufferedReader read2 = new BufferedReader(new InputStreamReader(errorstream));
					if (process.exitValue() != 0) {
						while (true) {
							String line = read2.readLine();
							if (line == null) {
								break;
							}
							circularList.push(line);
						}
					}
				} catch (IOException ex) {
					logger.error("Run task error :" + StringUtils.join(cmd, " "), ex);
					circularList.push(ex.getMessage());
				} finally {
					circularList.closed();
				}
			}
		});
		return process;
	}

	public Process getProcess() {
		return process;
	}

	public CircularList<String> getCircularList() {
		return circularList;
	}

	public String[] getCmd() {
		return cmd;
	}

	public Date getStartTime() {
		return startTime;
	}

	/**
	 * 没有开始 返回 Null
	 *
	 * @return
	 */
	public Boolean isClose() {
		if (future == null) {
			return null;
		}
		return future.isDone();
	}

	public boolean isRunning() {
		if (future == null) {
			return false;
		}
		return !future.isDone();
	}
}
