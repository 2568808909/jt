package com.jt.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.itf.pojo.Cart;
import com.jt.itf.service.CartService;
import com.jt.common.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private final static ObjectMapper MAPPER=new ObjectMapper();
	
	@RequestMapping("/query/{userId}")
	@ResponseBody
	public SysResult query(@PathVariable Long userId) {
		try {
			return SysResult.oK(cartService.findCartsByUserID(userId));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "查询失败");
	}
	
	@RequestMapping("/update/num/{userId}/{itemId}/{num}")
	@ResponseBody
	public SysResult updateNumByUI(@PathVariable Long userId,@PathVariable Long itemId,@PathVariable Integer num) {
		try {
			cartService.updateNumByUI(userId,itemId,num);
			return SysResult.oK();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "更新失败");
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveCart(String cartJSON) {
		try {
			Cart cart=MAPPER.readValue(cartJSON, Cart.class);
			cartService.saveCart(cart);
			return SysResult.oK();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "新增失败");
	}
	
	@RequestMapping("/delete/{userId}/{itemId}")
	@ResponseBody
	public SysResult deleteCart(@PathVariable Long userId,@PathVariable Long itemId) {
		try {
			cartService.deleteCart(userId,itemId);
			return SysResult.oK();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "删除失败");
	}
}
