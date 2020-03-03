package com.jt.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.vo.SysResult;
import com.jt.itf.pojo.Order;
import com.jt.itf.service.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	private final static ObjectMapper MAPPER=new ObjectMapper();
	
	@RequestMapping("/create")
	@ResponseBody
	public SysResult createOrder(String orderJSON) {
		try {
			//System.out.println("orderJSON: "+orderJSON);
			Order order=MAPPER.readValue(orderJSON, Order.class);
			String orderId=orderService.saveOrder(order);
			return SysResult.oK(orderId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "订单提交失败");
	}
	
	@RequestMapping("/query/{orderId}")
	@ResponseBody
	public Order queryOrder(@PathVariable String orderId) {
		return orderService.findOrderByOID(orderId);
	}
}
