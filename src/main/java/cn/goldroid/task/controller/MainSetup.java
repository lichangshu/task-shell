/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.controller;

import cn.goldroid.task.service.QuartzService;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

/**
 *
 * @author changshu.li
 */
public class MainSetup implements Setup {

	@Override
	public void init(NutConfig conf) {
		Ioc ioc = conf.getIoc();
		QuartzService serv = ioc.get(QuartzService.class);
		serv.init();
	}

	@Override
	public void destroy(NutConfig nc) {
	}

}
