package com.jt.order.controller;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.itf.pojo.OrderItem;

import redis.clients.jedis.JedisCluster;

@ControllerAdvice
public class OrderExceptionHandler {

//	@Autowired
//	private RabbitTemplate rabbitTemplate;
//	
//	@Autowired
//	private JedisCluster jedisCluster;
//	
//	@ExceptionHandler(value= {NumberFormatException.class})
//	@ResponseBody
//	public SysResult doHandlerRuntimeException(RuntimeException e) {
//		NumNotEnoughException notEnoughException=(NumNotEnoughException)e;
//		List<OrderItem> orderItems=notEnoughException.getOrderItems();
//		for(int i=0;i<notEnoughException.getNum();i++) {
//			OrderItem orderItem=orderItems.get(i);
//			rollback(orderItem.getItemId(),orderItem.getNum());
//		}
//		e.printStackTrace();
//		return SysResult.build(0, "库存不足："+orderItems.get(notEnoughException.getNum()).getTitle());
//	}
//	
//	private void rollback(Long itemId,Integer num) {
//		String key="ITEM_ID_NUM"+itemId;
//		Integer itemNum=Integer.parseInt(jedisCluster.get(key));
//		jedisCluster.set(key, (num+itemNum)+"");
//		OrderItem orderItem=new OrderItem();
//		orderItem.setItemId(itemId);
//		orderItem.setNum(-num);
//		rabbitTemplate.convertAndSend("updateNum",orderItem);
//	}
}
