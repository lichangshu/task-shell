/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.service.test;

import cn.goldroid.task.service.test.dao.TDao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

/**
 *
 * @author changshu.li
 */
@IocBean
public class TUserService {

	@Inject("tDao")
	public TDao dao;

	public String getName() {
		return dao.getName(TUserService.class.getCanonicalName());
	}
}
