package com.jt.order.mapper;

import com.jt.common.mapper.SysMapper;
import com.jt.itf.pojo.OrderShipping;

public interface OrderShippingMapper extends SysMapper<OrderShipping>{
	OrderShipping findOrderShippingByOID(String orderId);
}