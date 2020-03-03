package com.jt.itf.service;

import java.util.List;

import com.jt.itf.pojo.Item;

public interface SolrService {
	
	List<Item> findItemByKey(String key);
}
