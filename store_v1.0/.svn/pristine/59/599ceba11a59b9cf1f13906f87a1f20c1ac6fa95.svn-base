package com.itheima.store.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.store.dao.CategoryDao;
import com.itheima.store.domain.Category;
import com.itheima.store.utils.JDBCUtils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> findAll() throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Category> list = qr.query("select * from category", new BeanListHandler<Category>(Category.class));
		return list;
	}

	@Override
	public void save(Category category) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "insert into category values (?,?)";
		qr.update(sql, category.getCid(),category.getCname());
	}

	@Override
	public Category findById(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from category where cid = ?";
		Category category = qr.query(sql, new BeanHandler<Category>(Category.class), cid);
		return category;
	}

	@Override
	public void update(Category category) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update category set cname = ? where cid = ?";
		qr.update(sql, category.getCname(),category.getCid());
	}

	@Override
	public void delete(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "update product set cid = null where cid = ?";
		qr.update(sql,cid);
		
		sql = "delete from category where cid = ?";
		qr.update(sql,cid);
	}

}
