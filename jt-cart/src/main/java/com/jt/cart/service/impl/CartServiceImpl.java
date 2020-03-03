package com.jt.cart.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jt.cart.mapper.CartMapper;
import com.jt.itf.pojo.Cart;
import com.jt.itf.service.CartService;
import redis.clients.jedis.Jedis;

//@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private CartMapper cartMapper;
	
	@Override
	public List<Cart> findCartsByUserID(Long userId) {
		Cart cart=new Cart();
		cart.setUserId(userId);
		return cartMapper.select(cart);
	}

	@Override
	public void updateNumByUI(Long userId, Long itemId, Integer num) {
		Cart cart=new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cart.setNum(num);
		cart.setUpdated(new Date());
		cartMapper.updateNumByUI(cart);
		System.out.println(cart);
	}

	@Override
	public void saveCart(Cart cart) {
		cart.setUpdated(new Date());
		Cart select=new Cart();
		select.setUserId(cart.getUserId());
		select.setItemId(cart.getItemId());
		List<Cart> list=cartMapper.select(select);
		System.out.println(list);
		if(list.size()!=0){
			Integer num=list.get(0).getNum();
			cart.setNum(num+cart.getNum());
			cartMapper.updateNumByUI(cart);
		}else {
			cartMapper.insert(cart);
		}
	}

	@Override
	public void deleteCart(Long userId, Long itemId) {
		Cart cart=new Cart();
		cart.setUserId(userId);
		cart.setItemId(itemId);
		cartMapper.deleteByUI(cart);
	}

}
