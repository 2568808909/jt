package com.jt.manage.service;

import java.util.List;

import com.jt.common.vo.TreeResult;

public interface ItemCatService {
	
	/**
	 * 通过ItemCatId寻找对应的商品详情
	 * @param itemCatId
	 * @return
	 */
	String getItemCatNameById(Long itemCatId);
	
	/**
	 * 通过parendId寻找对应商品分类
	 * @param parentId
	 * @return
	 */
	List<TreeResult> getItemCatListByParentId(Long parentId);
	
}
