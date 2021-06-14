package com.itheima.store.service.impl;

import java.sql.SQLException;

import com.itheima.store.dao.UserDao;
import com.itheima.store.dao.impl.UserDaoImpl;
import com.itheima.store.domain.User;
import com.itheima.store.service.UserService;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.MailUtils;
import com.itheima.store.utils.UUIDUtils;

public class UserServiceImpl implements UserService{

	@Override
	public User findByUsername(String username) throws SQLException {
		//UserDao userDao = new UserDaoImpl();
		UserDao userDao = (UserDao) BeanFactory.getBean("userDao");
		return userDao.findByUsername(username);
	}

	@Override
	public void save(User user) throws SQLException{
		UserDao userDao = (UserDao) BeanFactory.getBean("userDao");
		user.setUid(UUIDUtils.getUUID());
		user.setState(1);  //1.代表未激活  2.代码已经激活了
		String code = UUIDUtils.getUUID()+UUIDUtils.getUUID();
		user.setCode(code);
 		userDao.save(user);
		
 		//发送激活邮件
 		MailUtils.sendMain(user.getEmail(), code);
	}

	@Override
	public User findByCode(String code) throws SQLException {
		UserDao userDao = (UserDao) BeanFactory.getBean("userDao");
		User user = userDao.findByCode(code);
		return user;
	}

	@Override
	public void update(User existUser) throws SQLException {
		UserDao userDao = (UserDao) BeanFactory.getBean("userDao");
		userDao.update(existUser);
	}

	@Override
	public User login(User user) throws SQLException {
		UserDao userDao = (UserDao) BeanFactory.getBean("userDao");
		User existUser = userDao.login(user);
		return existUser;
	}

}
