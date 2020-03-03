package com.jt.manage.service;

import com.jt.manage.pojo.ItemDesc;

public interface ItemDescService {
	
	/**
	 * 查询商品描述
	 * @param id
	 * @return
	 */
	ItemDesc findItemDescById(Long id);

}
