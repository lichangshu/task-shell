/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.domain;

import cn.goldroid.task.support.RunCmdTask;
import cn.goldroid.task.util.RunCmdUtil;
import java.io.Serializable;
import java.util.Date;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 *
 * @author changshu.li
 */
@Table("run_history")
public class RunHistory implements Serializable {

	@Id
	private int id;
	@Name
	@Column("task_key")
	private String taskKey;
//	@Column("cmd_format")
//	private String cmdFormat;
//	@Column("cmd_text")
//	private String cmdText;
	@Column
	private String rundir;
	@Column
	private String batch;
	@Column("create_time")
	private Date createTime;

	private String[] cmd;
	private boolean running = false;

	@Column("cmd_format")
	public String getCmdFormat() {
		return RunCmdUtil.encodeCommend(this.cmd);
	}

	@Column("cmd_text")
	public String getCmdText() {
		return RunCmdUtil.join(this.cmd, " ");
	}

	public void setCmdFormat(String cmdFormat) {
		this.cmd = RunCmdUtil.decodeCommend(cmdFormat);
	}

	public void setCmdText(String cmdText) {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getRundir() {
		return rundir;
	}

	public String[] getCmd() {
		return cmd;
	}

	public void setRundir(String rundir) {
		this.rundir = rundir;
	}

	public void setCmd(String[] cmd) {
		this.cmd = cmd;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}
