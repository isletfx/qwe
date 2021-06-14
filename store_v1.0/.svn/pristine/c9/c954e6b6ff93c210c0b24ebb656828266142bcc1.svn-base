package com.itheima.store.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.store.dao.OrderDao;
import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;
import com.itheima.store.domain.Product;
import com.itheima.store.utils.JDBCUtils;
import com.sun.mail.iap.Literal;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void save(Connection con, Order order) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "INSERT INTO orders VALUES (?,?,?,?,?,?,?,?)";
		Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()};
		qr.update(con, sql, params);
	}

	@Override
	public void saveOrderItem(Connection con, OrderItem orderItem) throws SQLException {
		QueryRunner qr = new QueryRunner();
		String sql = "INSERT INTO orderitem VALUES (?,?,?,?,?)";
		Object[] params = {orderItem.getItemid(),orderItem.getCount(),orderItem.getSubtotal(),orderItem.getProduct().getPid(),orderItem.getOrder().getOid()};
		qr.update(con, sql, params);
		
	}

	@Override
	public Integer fingCountByUid(String uid) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select count(*) from orders where uid = ?";
		Long count = (Long) qr.query(sql, new ScalarHandler(), uid);
		return count.intValue();
	}

	@Override
	public List<Order> findPageByUid(String uid, int begin, Integer pageSize) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where uid = ? order by ordertime desc limit ?,?";
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class),uid,begin,pageSize);
		for (Order order : list) {
			sql = "select * from orderitem o,product p where o.pid = p.pid and o.oid = ?";
			List<Map<String, Object>> oList = qr.query(sql, new MapListHandler(), order.getOid());
			//遍历list集合:获取到每条记录,封装到不同的对象里
			for (Map<String, Object> map: oList) {
				Product product = new Product();
				BeanUtils.populate(product, map);
				OrderItem orderItem = new OrderItem();
				BeanUtils.populate(orderItem , map);
				orderItem.setProduct(product);
				
				order.getOrderItems().add(orderItem);
			}
 		}
		return list;
	}

	@Override
	public Order findByOid(String oid) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where oid = ?";
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);
		sql = "select * from orderitem o,product p where o.pid = p.pid and o.oid = ?";
		List<Map<String, Object>> Olist = qr.query(sql, new MapListHandler(), oid);
		for (Map<String, Object> map : Olist) {
			Product product = new Product();
			BeanUtils.populate(product, map);
			OrderItem orderItem = new OrderItem();
			BeanUtils.populate(orderItem, map);
			
			orderItem.setProduct(product);
			
			order.getOrderItems().add(orderItem);
		}
		return order;
	}

	@Override
	public void update(Order order) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update orders set total = ?,state = ?,address =?,name = ?,telephone = ? where oid = ?";
		Object[] param = {order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid()};
		qr.update(sql,param);
	}

	@Override
	public List<Order> findAll() throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders order by ordertime desc";
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class));
		return list;
	}

	@Override
	public List<Order> findByState(int pstate) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where state = ? order by ordertime desc";
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class), pstate);
		return list;
	}

	@Override
	public List<OrderItem> showDateil(String oid) throws Exception {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orderitem o,product p where o.pid = p.pid and o.oid = ?";
		
		List<OrderItem> list = new ArrayList<OrderItem>();
		List<Map<String, Object>> oList = qr.query(sql, new MapListHandler(), oid);
		for (Map<String, Object> map : oList) {
			Product product = new Product();
			BeanUtils.populate(product, map);
			OrderItem orderItem = new OrderItem();
			BeanUtils.populate(orderItem, map);
			orderItem.setProduct(product);
			list.add(orderItem);
		}
		return list;
	}

}
