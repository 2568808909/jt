package com.jt.order.service.impl;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.order.mapper.OrderItemMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.mapper.OrderShippingMapper;

import redis.clients.jedis.JedisCluster;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.itf.pojo.NumNotEnoughException;
import com.jt.itf.pojo.Order;
import com.jt.itf.pojo.OrderItem;
import com.jt.itf.pojo.OrderShipping;
import com.jt.itf.service.ItemService;
import com.jt.itf.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderItemMapper orderItemMapper;
	
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private JedisCluster jedisCluster;
	
	private final static ObjectMapper MAPPER=new ObjectMapper();
	
	/**
	 * 提交订单
	 */
	@Override
	public String saveOrder(Order order) {
		String orderId=""+order.getUserId()+System.currentTimeMillis();
		order.setOrderId(orderId);
		List<OrderItem> orderItems =order.getOrderItems();
		//循环检查每个订单项商品是否满足需求
		for (int i=0;i<orderItems.size();i++) {
			OrderItem orderItem=orderItems.get(i);
			if(!getItemNum(orderItem.getItemId(),orderItem.getNum())) {
				//库存数量不足则抛出业务异常
				throw new NumNotEnoughException(orderItems,i);
			}
		}
		//库存充足则将订单信息放入消息队列，等待入库
		rabbitTemplate.convertAndSend("saveOrder", order);
		try {
			String objectJSON=MAPPER.writeValueAsString(order);
			jedisCluster.setex("ORDER"+orderId,1800,objectJSON);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return orderId;
	}

	/**
	 * 用于检验商品数量是否充足
	 * @param itemId
	 * @param num
	 * @return
	 */
	private boolean getItemNum(Long itemId,Integer num) {
		Integer itemNum=null;
		boolean result=true;
		String key="ITEM_ID_NUM"+itemId;
		//检验时从缓存中读取商品数量
		String numStr=jedisCluster.get(key);
		//如果缓存中没有数据则从数据库中查询
		if(numStr==null) {
			itemNum=itemService.findItemNumById(itemId);
		}else {
			itemNum=Integer.parseInt(numStr);
		}
		//若用户需求量大于库存则返回false
		if(itemNum<num) {
			result=false;
		}else {
			//若库存充足则将要更新的商品与数量放入消息队列
			itemNum-=num;
			OrderItem orderItem=new OrderItem();
			orderItem.setItemId(itemId);
			orderItem.setNum(num);
			rabbitTemplate.convertAndSend("updateNum", orderItem);
		}
		//将目前的库存量放入缓存
		jedisCluster.set(key, itemNum+"");
		return result;
	}
	
	@Override
	public Order findOrderByOID(String orderId) {
		Order order=orderMapper.findOrderByOID(orderId);
		List<OrderItem> orderItems=orderItemMapper.findOrderItemByOID(orderId);
		OrderShipping orderShipping=orderShippingMapper.findOrderShippingByOID(orderId);
		order.setOrderItems(orderItems);
		order.setOrderShipping(orderShipping);
		return order;
	}
	
	public void cancelOrder(String orderId) {
		rabbitTemplate.convertAndSend("cancelOrder", orderId);
	}

	/**
	 * 如果某订单项商品不足，则会抛出异常，在捕获业务异常后，在此对数据进行回滚操作，回滚缓存以及库中的数据
	 */
	@Override
	public void rollback(List<OrderItem> orderItems,int size) {
		for(int i=0;i<size;i++) {
			OrderItem orderItem=orderItems.get(i);
			addNum(orderItem.getItemId(),orderItem.getNum());
		}
	}
	
	private void addNum(Long itemId,Integer num) {
		String key="ITEM_ID_NUM"+itemId;
		Integer itemNum=Integer.parseInt(jedisCluster.get(key));
		jedisCluster.set(key, (num+itemNum)+"");
		OrderItem orderItem=new OrderItem();
		orderItem.setItemId(itemId);
		orderItem.setNum(-num);
		rabbitTemplate.convertAndSend("updateNum",orderItem);
	}
	
}
