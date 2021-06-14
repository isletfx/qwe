package com.itheima.store.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;

import com.itheima.store.dao.OrderDao;
import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;
import com.itheima.store.domain.PageBean;
import com.itheima.store.service.OrderService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.JDBCUtils;

public class OrderServiceImpl implements OrderService {

	@Override
	public void save(Order order) {
		Connection con = null;
		try {
			con = JDBCUtils.getConnection();
			// 开启事务
			con.setAutoCommit(false);
			// 执行操作
			OrderDao orderDao = (OrderDao) BeanFactory.getBean("orderDao");
			orderDao.save(con, order);
			// 循环保存订单中的订单项
			for (OrderItem orderItem : order.getOrderItems()) {
				orderDao.saveOrderItem(con, orderItem);
			}

			// 提交事务
			DbUtils.commitAndCloseQuietly(con);
		} catch (Exception e) {
			e.printStackTrace();
			// 报错的时候事务回滚
			DbUtils.rollbackAndCloseQuietly(con);
		}
	}

	@Override
	public PageBean<Order> findByUid(String uid, Integer currPage) throws Exception {
		PageBean<Order> pageBean = new PageBean<Order>();
		// 设置当前页
		pageBean.setCurrPage(currPage);
		// 每页显示多少条
		Integer pageSize = 5;
		pageBean.setPageSize(pageSize);
		// 总记录数
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("orderDao");
		Integer totalCount = orderDao.fingCountByUid(uid);
		pageBean.setTotalCount(totalCount);
		// 总页数
		double tc = totalCount;
		Double num = Math.ceil(tc / pageSize);
		pageBean.setTotalPage(num.intValue());

		// 设置数据
		int begin = (currPage - 1) * pageSize;
		List<Order> list = orderDao.findPageByUid(uid,begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}

	@Override
	public Order findByOid(String oid) throws Exception {
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("orderDao");
		return orderDao.findByOid(oid);
	}

	@Override
	public void update(Order order) throws Exception {
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("orderDao");
		orderDao.update(order);
	}

	@Override
	public List<Order> findAll() throws Exception {
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("orderDao");
		List<Order> list = orderDao.findAll();
		return list;
	}

	@Override
	public List<Order> findByState(int pstate) throws Exception {
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("orderDao");
		List<Order> list = orderDao.findByState(pstate);
		return list;
	}

	@Override
	public List<OrderItem> showDateil(String oid) throws Exception {
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("orderDao");
		List<OrderItem> list = orderDao.showDateil(oid);
		return list;
	}

}
