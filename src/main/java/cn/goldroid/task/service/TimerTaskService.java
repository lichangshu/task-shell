/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.service;

import cn.goldroid.task.dao.TimerTaskDao;
import cn.goldroid.task.domain.TimerTask;
import java.util.List;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.trans.Atom;

/**
 *
 * @author changshu.li
 */
@IocBean
public class TimerTaskService {

	@Inject
	TimerTaskDao timerTaskDao;

	public TimerTask getById(int id) {
		return timerTaskDao.getById(id);
	}

	public TimerTask insert(TimerTask runableTask) {
		return timerTaskDao.insert(runableTask);
	}

	public void update(TimerTask runableTask) {
		timerTaskDao.update(runableTask);
	}

	public List<TimerTask> listRunableTask(Integer page, Integer size) {
		return timerTaskDao.listRunableTask(page, size);
	}

	public List<TimerTask> listEnableRunableTask(Integer page, Integer size) {
		return timerTaskDao.listEnableRunableTask(page, size);
	}

	public void remove(final int id) {
		new Atom() {
			@Override
			public void run() {
				TimerTask tm = TimerTaskService.this.getById(id);
				if (tm != null) {
					timerTaskDao.delete(tm);
				}
			}
		}.run();
	}

}
