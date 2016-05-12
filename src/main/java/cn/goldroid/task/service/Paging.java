/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.goldroid.task.service;

import org.nutz.dao.pager.Pager;

/**
 *
 * @author changshu.li
 */
public class Paging extends Pager {

	public Paging() {
		this(null, null);
	}

	/**
	 * 从 1 开始
	 *
	 * @param page from 1
	 * @param size
	 */
	public Paging(Integer page, Integer size) {
		super();
		if (page == null || page <= 0) {
			page = 1;
		}
		if (size == null || size <= 0) {
			size = 20;
		}
		super.setPageNumber(page);
		super.setPageSize(size);
	}

}
