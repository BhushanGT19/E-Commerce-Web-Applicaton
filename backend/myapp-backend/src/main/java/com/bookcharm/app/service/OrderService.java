package com.bookcharm.app.service;

import com.bookcharm.app.model.Order;

public interface OrderService {
    static Order getOrderById(Long orderId) {
		// TODO Auto-generated method stub
		return null;
	}
    Order createOrder(Order order);
    Order updateOrder(Long orderId, Order order);
    boolean deleteOrder(Long orderId);
    // Other OrderService methods
}
