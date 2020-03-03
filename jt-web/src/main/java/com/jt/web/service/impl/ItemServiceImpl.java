package com.jt.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;
import com.jt.web.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService{
	
	@Autowired
	private HttpClientService httpClientService;
	
	private final static ObjectMapper MAPPER=new ObjectMapper();
	
	public Item showItem(Long itemId) {
		String url="http://manage.jt.com/web/item/findItemById";
		Map<String, String> params=new HashMap<>();
		params.put("itemId", ""+itemId);
		String itemJSON=httpClientService.doPost(url,params);
		System.out.println("JSON: "+itemJSON);
		Item item=null;
		try {
			if(itemJSON!=null) {
				item=MAPPER.readValue(itemJSON, Item.class);
				System.out.println("item :"+item);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return item;
	}
	
	public ItemDesc showItemDesc(Long itemId) {
		String url="http://manage.jt.com/web/item/findItemDescById";
		Map<String, String> params=new HashMap<>();
		params.put("itemId", ""+itemId);
		String itemJSON=httpClientService.doPost(url,params);
		System.out.println("JSON: "+itemJSON);
		ItemDesc itemDesc=null;
		try {
			if(itemJSON!=null) {
				itemDesc=MAPPER.readValue(itemJSON, ItemDesc.class);
				System.out.println("item :"+itemDesc);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return itemDesc;
	}
}
