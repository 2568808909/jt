package com.jt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.itf.service.ItemDescService;
import com.jt.itf.service.ItemService;

@Controller
@RequestMapping("/items")
public class ItemController { 

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ItemDescService itemDescService;
	
	@RequestMapping("/{itemId}")
	public String showItemById(@PathVariable Long itemId,Model model) {
		model.addAttribute("item",itemService.findItemById(itemId));
		model.addAttribute("itemDesc", itemDescService.findItemDescById(itemId));
		return "item";
	}
}
