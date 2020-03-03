package com.jt.order.mapper;

import com.jt.common.mapper.SysMapper;
import com.jt.itf.pojo.Order;

public interface OrderMapper extends SysMapper<Order>{
	
	Order findOrderByOID(String orderId);
	
	Integer cancelOrder(String orderId);
}
