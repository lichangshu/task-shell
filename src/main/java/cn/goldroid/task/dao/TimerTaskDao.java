/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.dao;

import cn.goldroid.task.domain.TimerTask;
import cn.goldroid.task.service.Paging;
import java.util.List;
import javax.sql.DataSource;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.loader.annotation.IocBean;

/**
 *
 * @author changshu.li
 */
@IocBean(args = "refer:dataSource")
public class TimerTaskDao {

//	private DataSource dataSource;
	protected Dao dao;

	public TimerTaskDao(DataSource dataSource) {
		this.dao = new NutDao(dataSource);
	}

	public TimerTask getById(int id) {
		return dao.fetch(TimerTask.class, id);
	}

	public List<TimerTask> listRunableTask(Integer page, Integer size) {
		return dao.query(TimerTask.class, Cnd.NEW().asc("id"), new Paging(page, size));
	}

	public List<TimerTask> listEnableRunableTask(Integer page, Integer size) {
		return dao.query(TimerTask.class, Cnd.where("status", "=", "1").asc("id"), new Paging(page, size));
	}

	public TimerTask insert(TimerTask runableTask) {
		return dao.insert(runableTask);
	}

	public void update(TimerTask runableTask) {
		dao.update(runableTask);
	}

	public void delete(TimerTask task) {
		dao.delete(task);
	}
}
