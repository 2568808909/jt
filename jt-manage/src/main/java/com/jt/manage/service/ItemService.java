package com.jt.manage.service;

import com.jt.common.vo.EasyUIResult;
import com.jt.manage.pojo.Item;

public interface ItemService {
	
	/**
	 * 查询商品分页信息
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIResult findItemPage(Integer page,Integer rows);
	
	/**
	 * 新增商品
	 * @param item
	 */
	void saveItem(Item item,String desc);
	
	/**
	 * 删除商品
	 * @param ids
	 */
	void deleteItems(Long[] ids);
	
	/**
	 * 修改商品
	 * @param item
	 */
	void updateItem(Item item,String desc);
	
	/**
	 * 修改商品状态：1.上架 2.下架
	 * @param ids
	 * @param state
	 */
	void changeState(Long[] ids,Integer status);
}
