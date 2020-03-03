package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.itf.pojo.Item;
import com.jt.itf.service.SolrService;

@Controller
public class IndexController {
	
	@Autowired
	private SolrService solrService;
	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/search")
	public String search(String q,Model model) {
		try {
			String key=new String(q.getBytes("ISO-8859-1"), "utf-8");
			List<Item> items=solrService.findItemByKey(key);
			System.out.println(items);
			model.addAttribute("itemList",items);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "search";
	}
}
