/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.domain;

import cn.goldroid.task.util.RunCmdUtil;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.nutz.dao.entity.annotation.*;

/**
 *
 * @author changshu.li
 */
@Table("timer_task")
public class TimerTask implements Serializable {

	@Id
	private int id;
	@Column
	private String name;
	@Column("status")
	private boolean enable = false;
	@Column
	@ColDefine(type = ColType.VARCHAR)
	private String timer;
	@Column
	private String dscrption;
//	@Column("cmd_format")
//	private String cmdFormat;
//	@Column("cmd_text")
//	private String cmdText;
	@Column
	private String rundir;
	@Column("create_time")
	private Date creatTime = new Date();

	private Integer[] dependers;
	private String[] cmd;

	@Column("cmd_format")
	public String getCmdFormat() {
		return RunCmdUtil.encodeCommend(this.cmd);
	}

	@Column("cmd_text")
	public String getCmdText() {
		return RunCmdUtil.join(this.cmd, " ");
	}

	@Column("depends")
	public void setDepends(String depends) {
		if (depends == null) {
			dependers = new Integer[]{};
		}
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDscrption() {
		return dscrption;
	}

	public void setDscrption(String dscrption) {
		this.dscrption = dscrption;
	}

	public String getRundir() {
		return rundir;
	}

	public void setRundir(String rundir) {
		this.rundir = rundir;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	public String[] getCmd() {
		return cmd;
	}

	public void setCmd(String[] cmd) {
		this.cmd = cmd;
	}

	public String getDepends() {
		if (this.dependers == null) {
			return null;
		}
		return StringUtils.join(this.dependers, ",");
	}

	public Integer[] getDependers() {
		return dependers;
	}

	public void setDependers(Integer[] dependers) {
		this.dependers = dependers;
	}

	public String getTimer() {
		return timer;
	}

	public void setTimer(String timer) {
		this.timer = timer;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

}
