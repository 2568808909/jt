package com.jt.order.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jt.itf.pojo.Order;
import com.jt.itf.pojo.OrderItem;
import com.jt.itf.pojo.OrderShipping;
import com.jt.itf.service.ItemService;
import com.jt.order.mapper.OrderItemMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.mapper.OrderShippingMapper;

public class OrderMsgConsumer {

	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderItemMapper orderItemMapper;
	
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	
	@Autowired
	private ItemService itemService;
	
	public void saveOrder(Order order) {
		Order dbOrder=new Order();
		dbOrder.setOrderId(order.getOrderId());
		Date date=new Date();
		order.setUpdated(date);
		order.setCreated(date);
		if(orderMapper.select(dbOrder).size()!=0) {
			orderMapper.updateByPrimaryKeySelective(order);
		}else {
			//String orderId=""+order.getUserId()+System.currentTimeMillis();
			//插入订单信息
			//order.setOrderId(orderId);
			order.setStatus(1);
			orderMapper.insert(order);
		}
		//插入订单物流信息
		OrderShipping orderShipping=order.getOrderShipping();
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShipping.setOrderId(order.getOrderId());
		orderShippingMapper.insert(orderShipping);
		
		//订单项信息
		List<OrderItem> orderItems=order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			orderItem.setOrderId(order.getOrderId());
			orderItem.setCreated(date);
			orderItem.setUpdated(date);
		}
		orderItemMapper.saveOrderItems(orderItems);			
	}
	
	public void updateItemNumber(OrderItem orderItem) {
		Long itemId=orderItem.getItemId();
		Integer num=orderItem.getNum();
		itemService.descNumByItemId(itemId, num);
	}
	
	public void cancelOrder(String orderId) {
		Integer updateResult=orderMapper.cancelOrder(orderId);
		if(updateResult==0) {
			Order order=new Order();
			order.setOrderId(orderId);
			order.setStatus(6);
			orderMapper.insert(order);
		}
	}
}
