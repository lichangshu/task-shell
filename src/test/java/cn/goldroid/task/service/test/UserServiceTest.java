/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.service.test;

import cn.goldroid.task.service.test.TUserService;
import org.junit.Test;
import static org.junit.Assert.*;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.annotation.AnnotationIocLoader;

/**
 *
 * @author changshu.li
 */
public class UserServiceTest {

	Ioc ioc = new NutIoc(new AnnotationIocLoader("cn.goldroid.task.service.test"));

	@Test
	public void testGetName() {
		System.out.println("getName");
		TUserService instance = ioc.get(TUserService.class);
		String expResult = TUserService.class.getCanonicalName();
		String result = instance.getName();
		assertEquals(expResult, result);
	}

}
