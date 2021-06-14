package com.itheima.store.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.store.dao.UserDao;
import com.itheima.store.domain.User;
import com.itheima.store.utils.JDBCUtils;

public class UserDaoImpl implements UserDao{

	@Override
	public User findByUsername(String username) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		User user = qr.query("select * from user where username = ?", new BeanHandler<User>(User.class), username);
		return user;
	}

	@Override
	public void save(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = {user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),
				user.getBirthday(),user.getSex(),user.getState(),user.getCode()};
		qr.update("insert into user values(?,?,?,?,?,?,?,?,?,?)", params);
	}

	@Override
	public User findByCode(String code) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		User user = qr.query("select * from user where code = ?", new BeanHandler<User>(User.class), code);
		return user;
	}

	@Override
	public void update(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = {user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),
				user.getBirthday(),user.getSex(),user.getState(),user.getCode(),user.getUid()};
		qr.update("update user set username = ? ,password = ?,name = ?, email = ?,telephone=?,birthday = ?,sex = ?,state=?,code=? where uid = ?", params);
	}

	@Override
	public User login(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		User existuser = qr.query("select * from user where username = ? and password = ? and state = ?", new BeanHandler<User>(User.class), user.getUsername(),user.getPassword(),2);
		return existuser;
	}

}
