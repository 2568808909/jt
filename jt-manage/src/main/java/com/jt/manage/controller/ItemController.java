package com.jt.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemCatService;
import com.jt.manage.service.ItemDescService;
import com.jt.manage.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ItemCatService itemCatService;
	
	@Autowired
	private ItemDescService itemDescService;
	
	@RequestMapping("/query")
	@ResponseBody
	public EasyUIResult query(Integer page,Integer rows){
		return itemService.findItemPage(page, rows);
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item,String desc) {
		try {
			itemService.saveItem(item,desc);
			return SysResult.oK();
		}catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(1, "error");
		}
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteItem(Long[] ids) {
		try {
			itemService.deleteItems(ids);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(0, e.getMessage());
		}
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateItem(Item item,String desc) {
		try {
			itemService.updateItem(item,desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(0, e.getMessage());
		}
	}
	
	@RequestMapping("/instock")
	@ResponseBody
	public SysResult instock(Long[] ids) {
		try {
			itemService.changeState(ids, 2);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(0, e.getMessage());
		}
	}
	
	@RequestMapping("/reshelf")
	@ResponseBody
	public SysResult reshelf(Long[] ids) {
		try {
			itemService.changeState(ids, 1);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(0, e.getMessage());
		}
	}
	
	@RequestMapping("/query/item/desc/{id}")
	@ResponseBody
	public SysResult queryItemDesc(@PathVariable Long id) {
		try {
			ItemDesc itemDesc=itemDescService.findItemDescById(id);
			return SysResult.oK(itemDesc);
		}catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(0, "error");
		}
	}
	
	//由于DispatcherServlet在直接返回字符串时，使用的字符集是ISO-8859-1，所以需要在此处设置一下，当然也可以通过response对象设置，但是这在SpringMVC中不是推荐做法
	@RequestMapping(value="/queryItemCatName",produces="text/html;charset=utf-8")
	@ResponseBody
	public String queryItemCatName(Long itemCatId) {
		return itemCatService.getItemCatNameById(itemCatId);
	}
	
	
	
}
