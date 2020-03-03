package com.jt.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.itf.pojo.NumNotEnoughException;
import com.jt.itf.pojo.Order;
import com.jt.itf.service.CartService;
import com.jt.itf.service.OrderService;
import com.jt.web.thread.UserThreadLocal;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("/create")
	public String createOrder(Model model) {
		Long userId=UserThreadLocal.get().getId();
		model.addAttribute("carts",cartService.findCartsByUserID(userId));
		return "order-cart";
	}
	
	@RequestMapping("/submit")
	@ResponseBody
	public SysResult saveOrder(Order order) {
		try {
			Long userId=UserThreadLocal.get().getId();
			order.setUserId(userId);
			String orderId=orderService.saveOrder(order);
			return SysResult.oK(orderId);
		}catch (NumNotEnoughException e) {
			e.printStackTrace();
			orderService.rollback(e.getOrderItems(),e.getNum());
			return SysResult.build(201, "库存不足");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "订单提交失败");
	}
	
	@RequestMapping("/success")
	public String success(Model model,String id) {
		Order order=orderService.findOrderByOID(id);
		System.out.println(order);
		model.addAttribute("order",order);
		return "success";
	}
}
