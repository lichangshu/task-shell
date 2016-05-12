/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.dao;

import cn.goldroid.task.domain.RunHistory;
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
public class TaskHistoryDao {

//	private DataSource dataSource;
	protected Dao dao;

	public TaskHistoryDao(DataSource dataSource) {
		this.dao = new NutDao(dataSource);
	}

	public RunHistory getById(int id) {
		return dao.fetch(RunHistory.class, id);
	}

	public List<RunHistory> listTaskHistory(Integer page, Integer size) {
		return dao.query(RunHistory.class, Cnd.NEW().desc("id"), new Paging(page, size));
	}

	public RunHistory insert(RunHistory runableTask) {
		return dao.insert(runableTask);
	}
}
