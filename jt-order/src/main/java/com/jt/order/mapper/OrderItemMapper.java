package com.jt.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.common.mapper.SysMapper;
 
import com.jt.itf.pojo.OrderItem;

public interface OrderItemMapper extends SysMapper<OrderItem>{
	
	void saveOrderItems(@Param("orderItems") List<OrderItem> orderItems);
	
	List<OrderItem> findOrderItemByOID(String orderId);
}