/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.service.test.dao;

import org.nutz.ioc.loader.annotation.IocBean;

/**
 *
 * @author changshu.li
 */
@IocBean
public class TDao {

	public String getName(String name) {
		return name;
	}
}
