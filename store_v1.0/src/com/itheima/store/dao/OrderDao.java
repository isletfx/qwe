package com.itheima.store.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;

public interface OrderDao {

	void save(Connection con, Order order) throws SQLException;

	void saveOrderItem(Connection con, OrderItem orderItem) throws SQLException;

	Integer fingCountByUid(String uid) throws Exception;

	List<Order> findPageByUid(String uid, int begin, Integer pageSize) throws Exception;

	Order findByOid(String oid) throws Exception;

	void update(Order order) throws Exception;

	List<Order> findAll() throws Exception;

	List<Order> findByState(int pstate) throws Exception;

	List<OrderItem> showDateil(String oid) throws Exception;



}
