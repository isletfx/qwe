package com.itheima.store.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.itheima.store.dao.CategoryDao;
import com.itheima.store.dao.impl.CategoryDaoImpl;
import com.itheima.store.domain.Category;
import com.itheima.store.service.CategoryService;
import com.itheima.store.utils.BeanFactory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CategoryServiceImpl implements CategoryService {

	@Override
	public List<Category> findAll() throws SQLException {
		/**
		 * 使用缓存优化程序,先从缓存中获取数据
		 *    * 如果缓存中能够获取到,那么就直接返回了
		 *    * 如果缓存这种获取不到,那么再去查询数据库
		 */
		//读取到配置文件
		CacheManager cacheManager = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		//我们从配置文件获取到名称为categoryCache的这块缓存区域
		Cache cache = cacheManager.getCache("categoryCache");
		//判断缓存中是否有我们存入的数据
		Element element = cache.get("list");
		List<Category> list = null;
		if (element == null) {
			//缓存中没有我们的数据
			System.out.println("缓存中没有数据,查询数据库======");
			//CategoryDao categoryDao = new CategoryDaoImpl();
			CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("categoryDao");
			list = categoryDao.findAll();
			element = new Element("list", list);
			cache.put(element);
		
		}else {
			//缓存中已经存在我们的数据
			System.out.println("缓存中有数据,直接从缓存中获取======");
			list = (List<Category>) element.getObjectValue();
		}
		return list;
	}

	@Override
	public void save(Category category) throws SQLException {
		CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("categoryDao");
		categoryDao.save(category);
		
		//读取到配置文件
		CacheManager cacheManager = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		//我们从配置文件获取到名称为categoryCache的这块缓存区域
		Cache cache = cacheManager.getCache("categoryCache");
		//从缓存中移除
		cache.remove("list");
	}

	@Override
	public Category findById(String cid) throws SQLException {
		CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("categoryDao");
		return categoryDao.findById(cid);
	}

	@Override
	public void update(Category category) throws SQLException {
		CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("categoryDao");
		categoryDao.update(category);
		
		//读取到配置文件
		CacheManager cacheManager = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		//我们从配置文件获取到名称为categoryCache的这块缓存区域
		Cache cache = cacheManager.getCache("categoryCache");
		//从缓存中移除
		cache.remove("list");
	}

	@Override
	public void delete(String cid) throws SQLException {
		CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("categoryDao");
		categoryDao.delete(cid);
		
		//读取到配置文件
		CacheManager cacheManager = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		//我们从配置文件获取到名称为categoryCache的这块缓存区域
		Cache cache = cacheManager.getCache("categoryCache");
		//从缓存中移除
		cache.remove("list");
		
	}

}
