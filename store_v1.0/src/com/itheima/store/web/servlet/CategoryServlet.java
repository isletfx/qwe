package com.itheima.store.web.servlet;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.domain.Category;
import com.itheima.store.service.CategoryService;
import com.itheima.store.service.impl.CategoryServiceImpl;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;

import net.sf.json.JSONArray;


/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {
	
	public String findAll(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			//CategoryService categoryService = new CategoryServiceImpl();
			CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");
			List<Category> list = categoryService.findAll();
			String jsonString = JSONArray.fromObject(list).toString();
			response.getWriter().println(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
