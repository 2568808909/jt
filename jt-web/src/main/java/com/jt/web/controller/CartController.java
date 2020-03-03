package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.SysResult;
import com.jt.itf.pojo.Cart;
import com.jt.itf.service.CartService;
import com.jt.web.thread.UserThreadLocal;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@RequestMapping("/show")
	public String showCart(Model model) {
		Long userId=UserThreadLocal.get().getId();
		List<Cart> cartList=cartService.findCartsByUserID(userId);
		model.addAttribute("cartList",cartList);
		return "cart";
	}
	
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateNumByUI(@PathVariable Long itemId,@PathVariable Integer num) {
		try {
			Long userId=UserThreadLocal.get().getId();
			cartService.updateNumByUI(userId,itemId,num);
			return SysResult.oK();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "更新失败");
	}
	
	@RequestMapping("/add/{itemId}")
	public String addItem(@PathVariable Long itemId,Cart cart) {
		try {
			Long userId=UserThreadLocal.get().getId();
			cart.setUserId(userId);
			cart.setItemId(itemId);
			cartService.saveCart(cart);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/cart/show.html";
	}
	
	@RequestMapping("/delete/{itemId}")
	public String deleteItem(@PathVariable Long itemId) {
		try {
			Long userId=UserThreadLocal.get().getId();
			cartService.deleteCart(userId, itemId);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/cart/show.html";
	}
	
}
