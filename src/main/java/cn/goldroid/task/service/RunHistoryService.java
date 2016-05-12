/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.service;

import cn.goldroid.task.dao.TaskHistoryDao;
import cn.goldroid.task.domain.RunHistory;
import java.util.List;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

/**
 *
 * @author changshu.li
 */
@IocBean
public class RunHistoryService {

	@Inject
	TaskHistoryDao taskHistoryDao;

	public RunHistory getById(int id) {
		return taskHistoryDao.getById(id);
	}

	public List<RunHistory> listTaskHistory(Integer page, Integer size) {
		return taskHistoryDao.listTaskHistory(page, size);
	}

	public RunHistory insert(RunHistory runableTask) {
		return taskHistoryDao.insert(runableTask);
	}

}
