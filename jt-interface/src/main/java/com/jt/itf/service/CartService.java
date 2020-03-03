package com.jt.itf.service;

import java.util.List;

import com.jt.itf.pojo.Cart;

public interface CartService {
List<Cart> findCartsByUserID(Long userId);
	
	void updateNumByUI(Long userId,Long itemId,Integer num);
	
	void saveCart(Cart cart);
	
	void deleteCart(Long userId,Long itemId);
}
