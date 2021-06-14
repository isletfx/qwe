package com.itheima.store.web.servlet;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.itheima.store.domain.Category;
import com.itheima.store.service.CategoryService;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.UUIDUtils;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

/**
 * Servlet implementation class AdminCategoryServlet
 */
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 查找所有分类
	 * @param request
	 * @param response
	 * @return
	 */
	public String findAll(HttpServletRequest request,HttpServletResponse response) {
		try {	
		CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");
		List<Category> list = categoryService.findAll();
		request.setAttribute("list", list);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "/jsp/admin/category/list.jsp";
	}
	
	/**
	 * 添加分类页面跳转
	 * @param request
	 * @param response
	 * @return
	 */
	public String saveUI(HttpServletRequest request,HttpServletResponse response) {
		
		return "/jsp/admin/category/add.jsp";
	}
	
	/**
	 * 保存分类
	 */
	
	public String save(HttpServletRequest request,HttpServletResponse response) {
		try {
		String cname = request.getParameter("cname");
		Category category = new Category();
		category.setCid(UUIDUtils.getUUID());
		category.setCname(cname);
		
		CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");
		categoryService.save(category);
		
		response.sendRedirect(request.getContextPath()+"/AdminCategoryServlet?method=findAll");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 编辑分类
	 * @param request
	 * @param response
	 * @return
	 */
	public String edit(HttpServletRequest request,HttpServletResponse response) {
		try {
		//接受数据
		String cid = request.getParameter("cid");
		//调用业务层
		CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");
		Category category = categoryService.findById(cid);
		request.setAttribute("category", category);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "/jsp/admin/category/edit.jsp";
	}
	
	/**
	 * 修改分类
	 * @param request
	 * @param response
	 * @return
	 */
	public String update(HttpServletRequest request,HttpServletResponse response) {
		try {
			//接受参数
			Map<String, String[]> map = request.getParameterMap();
			//封装数据
			Category category = new Category();
			BeanUtils.populate(category, map);
			//调用业务层
			CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");
			categoryService.update(category);
			
			response.sendRedirect(request.getContextPath()+"/AdminCategoryServlet?method=findAll");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除分类
	 */
	public String delete(HttpServletRequest request,HttpServletResponse response) {
		try {
		String cid = request.getParameter("cid");
		CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");
		categoryService.delete(cid);
		//页面跳转
		response.sendRedirect(request.getContextPath()+"/AdminCategoryServlet?method=findAll");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
