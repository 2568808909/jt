package com.jt.manage.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jt.common.vo.EasyUIResult;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemService;

@Transactional
@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private ItemDescMapper itemDescMapper;
	
	@Transactional(readOnly=true,propagation=Propagation.SUPPORTS)
	@Override
	public EasyUIResult findItemPage(Integer page,Integer rows) {
		if(page<=0) {
			throw new IllegalArgumentException("参数不正确page: " +page);
		}
		if(rows<=0) {
			throw new IllegalArgumentException("参数不正确rows: " +rows);
		}
		Integer total=itemMapper.getItemCount();
		List<Item> items=itemMapper.findItemPage((page-1)*rows, rows);
		EasyUIResult result=new EasyUIResult();
		result.setRows(items);
		result.setTotal(total);
		return result;
	}

	@Override
	public void saveItem(Item item,String desc) {
		if(item==null) {
			throw new IllegalArgumentException("参数不可为空");
		}
		//新增商品
		item.setStatus(1);
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);
		//新增商品描述
		ItemDesc itemDesc=new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
	}

	@Override
	public void deleteItems(Long[] ids) {
		itemMapper.deleteByIDS(ids);
		itemDescMapper.deleteByIds(ids);
	}

	@Override
	public void updateItem(Item item,String desc) {
		//更新商品
		item.setUpdated(new Date());
		itemMapper.updateByPrimaryKeySelective(item);
		//更新商品描述
		ItemDesc itemDesc=new ItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.updateById(itemDesc);
	}

	@Override
	public void changeState(Long[] ids, Integer status) {
		if(ids.length==0) {
			throw new IllegalArgumentException("请选择至少一个");
		}
		itemMapper.updateStatus(ids, status);
	}
	
}
