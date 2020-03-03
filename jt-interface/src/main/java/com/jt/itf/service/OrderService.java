package com.jt.itf.service;

import java.util.List;

import com.jt.itf.pojo.Order;
import com.jt.itf.pojo.OrderItem;

public interface OrderService {
	
	String saveOrder(Order order);
	
	Order findOrderByOID(String orderId);
	
	void rollback(List<OrderItem> orderItems,int size);
}
