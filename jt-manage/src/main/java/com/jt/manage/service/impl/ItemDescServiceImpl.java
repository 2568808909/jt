package com.jt.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemDescService;

@Service
public class ItemDescServiceImpl implements ItemDescService{

	@Autowired
	private ItemDescMapper itemDescMapper;
	
	@Override
	public ItemDesc findItemDescById(Long id) {
		ItemDesc itemDesc=new ItemDesc();
		itemDesc.setItemId(id);
		return itemDescMapper.select(itemDesc).get(0);
	}

	
}
