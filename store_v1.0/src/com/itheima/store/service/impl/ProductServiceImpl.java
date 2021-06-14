package com.itheima.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.itheima.store.dao.ProductDao;
import com.itheima.store.dao.impl.ProductDaoImpl;
import com.itheima.store.domain.PageBean;
import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {

	@Override
	public List<Product> findByNew() throws SQLException {
		//ProductDao productDao = new ProductDaoImpl();
		ProductDao productDao = (ProductDao) BeanFactory.getBean("productDao");
		List<Product> newList = productDao.findByNew();
		return newList;
	}

	@Override
	public List<Product> findByHot() throws SQLException {
		ProductDao productDao = (ProductDao) BeanFactory.getBean("productDao");
		return productDao.findByHot();
	}

	@Override
	public PageBean<Product> findByPageCid(String cid, Integer currPage) throws SQLException {
		PageBean<Product> pageBean = new PageBean<Product>();
		//设置当前页
		pageBean.setCurrPage(currPage);
		//设置每页显示的条数
		Integer pageSize = 12;
		pageBean.setPageSize(pageSize);
		//设置总条数
		ProductDao productDao = new ProductDaoImpl();
		Integer totalCount = productDao.findCountByCid(cid);
		pageBean.setTotalCount(totalCount);
		//设置总页数
		Integer totalPage = 0;
		if (totalCount % pageSize == 0) {
			totalPage = totalCount / pageSize;
		}else {
			totalPage = totalCount / pageSize + 1;
		}
		pageBean.setTotalPage(totalPage);
		//设置数据
		int begin = (currPage - 1)*pageSize;
		List<Product> list = productDao.findPageByCid(cid,begin,pageSize);
		pageBean.setList(list);
		
		return pageBean;
	}

	@Override
	public Product findByPid(String pid) throws SQLException {
		ProductDao productDao = (ProductDao) BeanFactory.getBean("productDao");
		return productDao.findByPid(pid);
	}

	@Override
	public PageBean<Product> findByPage(Integer currPage) throws SQLException {
		
		PageBean<Product> pageBean = new PageBean<Product>();
		//设置当前页
		pageBean.setCurrPage(currPage);
		//设置每页显示多少条
		Integer pageSize = 10;
		pageBean.setPageSize(pageSize);
		//设置总记录数
		ProductDao productDao = (ProductDao) BeanFactory.getBean("productDao");
		Integer totalCount = productDao.findCount();
		pageBean.setTotalCount(totalCount);
		//设置总页数
		double tc = totalCount;
		Double num = Math.ceil(tc / pageSize);
		pageBean.setTotalPage(num.intValue());
		//设置集合
		int begin = (currPage - 1) * pageSize;
		List<Product> list = productDao.findByPage(begin,pageSize);
		pageBean.setList(list);
		
		return pageBean; 
	}

	@Override
	public void save(Product product) throws SQLException {
		ProductDao productDao = (ProductDao) BeanFactory.getBean("productDao");
		productDao.save(product);
	}

	@Override
	public void update(Product product) throws SQLException {
		ProductDao productDao = (ProductDao) BeanFactory.getBean("productDao");
		productDao.update(product);
		
	}

	@Override
	public List<Product> findByPushDown() throws SQLException {
		ProductDao productDao = (ProductDao) BeanFactory.getBean("productDao");
		List<Product> list = productDao.findByPushDown();
		return list;
	}

	

	


}
