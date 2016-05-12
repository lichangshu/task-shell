/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.support;

import cn.goldroid.task.domain.TimerTask;
import cn.goldroid.task.util.CircularList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

/**
 *
 * @author changshu.li
 */
public class RunCmdTaskTest {

	@Test
	public void testRun() throws Exception {
		System.out.println("run");
		TimerTask task = new TimerTask();
		task.setCmd(new String[]{"cmd","/c ping www.baidu.com"});
		
		RunCmdTask instance = new RunCmdTask(task.getCmd());
		instance.run();
		CircularList<String> list = instance.getCircularList();
		for (int i = 0; i < Integer.MAX_VALUE;) {
			List<CircularList<String>.CircularListNode> fm = list.getFrom(i);
			for (CircularList<String>.CircularListNode it : fm) {
				i++;
				System.out.println("[java] " + it.getNode());
			}
			if (fm.isEmpty() && list.isClose()) {
				break;
			}
			Thread.sleep(1000);
		}
	}

}
