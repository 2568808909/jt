package com.jt.itf.pojo;

import java.util.List;

import com.jt.itf.pojo.OrderItem;

public class NumNotEnoughException extends RuntimeException{
	private static final long serialVersionUID = 3032224026964457619L;

	private List<OrderItem> orderItems;
	
	private Integer num;
	
	public NumNotEnoughException(List<OrderItem> orderItems,Integer num) {
		this.orderItems=orderItems;
		this.num=num;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	
}
