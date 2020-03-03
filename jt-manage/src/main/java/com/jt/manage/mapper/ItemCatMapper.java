package com.jt.manage.mapper;

import java.util.List;

import com.jt.common.mapper.SysMapper;
import com.jt.manage.pojo.ItemCat;

public interface ItemCatMapper extends SysMapper<ItemCat>{
	
	/**
	 * 通过id查找对应的分类名
	 * @param itemCatId
	 * @return
	 */
	String getItemCatNameById(Long itemCatId);
	
	/**
	 * 通过parentId寻找记录
	 * @param parentId
	 * @return
	 */
	List<ItemCat> findItemCatByParentId(Long parentId);
}
