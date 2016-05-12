/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.dao;

import cn.goldroid.task.domain.TimerTask;
import java.util.UUID;
import javax.sql.DataSource;
//import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.nutz.conf.NutConf;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.json.JsonLoader;

/**
 *
 * @author changshu.li
 */
public class RunableTaskDaoTest {

	public RunableTaskDaoTest() {
	}

	@Test
	public void testInsert() {
		System.out.println("insert");
		TimerTask r = new TimerTask();
		r.setCmd(new String[]{"cmd", "/c", "ping", "www.baidu.com"});
		r.setName("test-ping");
		NutIoc ioc = new NutIoc(new JsonLoader("config.js"));
		DataSource dataSource = ioc.get(DataSource.class, "dataSource");
		TimerTaskDao instance = new TimerTaskDao(dataSource);
		TimerTask result = instance.insert(r);
		System.out.println(result.toString());
		assertFalse(result.getId() == 0);

		TimerTask task = instance.getById(result.getId());
		assertNotNull(task.getCmdText());
		assertEquals(task.getCmdText(), r.getCmdText());

		String name = UUID.randomUUID().toString();
		result.setName(name);
		instance.update(result);
		task = instance.getById(result.getId());
		assertEquals(task.getName(), name);

		instance.delete(task);
	}

}
