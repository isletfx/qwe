package com.itheima.store.service;

import java.util.List;

import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;
import com.itheima.store.domain.PageBean;

public interface OrderService {

	void save(Order order);

	PageBean<Order> findByUid(String uid, Integer currPage) throws Exception;

	Order findByOid(String oid) throws Exception;

	void update(Order order) throws Exception;

	List<Order> findAll() throws Exception;

	List<Order> findByState(int pstate) throws Exception;

	List<OrderItem> showDateil(String oid) throws Exception;
	
}
