package com.jt.cart.mapper;

import com.jt.itf.pojo.Cart;
import com.jt.common.mapper.SysMapper;

public interface CartMapper extends SysMapper<Cart>{
	
	void updateNumByUI(Cart cart);
	
	void deleteByUI(Cart cart);
}
