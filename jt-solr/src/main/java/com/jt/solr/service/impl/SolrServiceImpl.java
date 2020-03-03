package com.jt.solr.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;

import com.jt.itf.pojo.Item;
import com.jt.itf.service.SolrService;

public class SolrServiceImpl implements SolrService{

	@Autowired
	private HttpSolrServer httpSolrServer;
	
	@Override
	public List<Item> findItemByKey(String key) {
		List<Item> items=null;
		try {
			SolrQuery solrQuery=new SolrQuery(key);
			solrQuery.setStart(0);
			solrQuery.setRows(20);
			QueryResponse response=httpSolrServer.query(solrQuery);
			items=response.getBeans(Item.class);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}
	
}
