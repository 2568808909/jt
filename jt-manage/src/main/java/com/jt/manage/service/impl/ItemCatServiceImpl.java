package com.jt.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.vo.TreeResult;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.ItemCat;
import com.jt.manage.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService{
	
	@Autowired
	private ItemCatMapper itemCatMapper;

	@Override
	public String getItemCatNameById(Long itemCatId) {
		return itemCatMapper.getItemCatNameById(itemCatId);
	}

	@Override
	public List<TreeResult> getItemCatListByParentId(Long parentId) {
		if(parentId<0) {
			throw new IllegalArgumentException("参数不正确 parentId: "+parentId);
		}
		List<ItemCat> list=itemCatMapper.findItemCatByParentId(parentId);
		List<TreeResult> treeResults=new ArrayList<>();
		for (ItemCat itemCat : list) {
			TreeResult result=new TreeResult();
			result.setId(itemCat.getId());
			result.setText(itemCat.getName());
			result.setState(itemCat.getIsParent()?"closed":"open");
			treeResults.add(result);
		}
		return treeResults;
	}
}
