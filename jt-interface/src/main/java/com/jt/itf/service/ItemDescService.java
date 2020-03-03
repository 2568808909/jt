package com.jt.itf.service;

import com.jt.itf.pojo.ItemDesc;

public interface ItemDescService {
	
	/**
	 * 查询商品描述
	 * @param id
	 * @return
	 */
	ItemDesc findItemDescById(Long id);

}
