/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.controller;

import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

/**
 *
 * @author changshu.li
 */
@Modules(scanPackage = true, packages = {"cn.goldroid.task.controller", "cn.goldroid.spider.controller"})
@IocBy(type = ComboIocProvider.class,
		args = {"*org.nutz.ioc.loader.json.JsonLoader",
			"/config.js",
			"*org.nutz.ioc.loader.annotation.AnnotationIocLoader",
			"cn.goldroid.spider.service",
			"cn.goldroid.spider.controller",
			"cn.goldroid.task.service",
			"cn.goldroid.task.controller",
			"cn.goldroid.task.dao"})
@SetupBy(MainSetup.class)
@Fail("http:500")
public class MainModule {
}
