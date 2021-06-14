package com.itheima.store.web.servlet;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.dao.ProductDao;
import com.itheima.store.dao.impl.ProductDaoImpl;
import com.itheima.store.domain.Category;
import com.itheima.store.domain.PageBean;
import com.itheima.store.domain.Product;
import com.itheima.store.service.CategoryService;
import com.itheima.store.service.ProductService;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;

/**
 * Servlet implementation class AdminProductServlet
 */
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * 商品显示分页
	 * @param request
	 * @param response
	 * @return
	 */
	public String findByPage(HttpServletRequest request,HttpServletResponse response) {
		try {
		Integer currPage = Integer.parseInt(request.getParameter("currPage"));
		ProductService productService = (ProductService) BeanFactory.getBean("productService");
		PageBean<Product> pageBean = productService.findByPage(currPage);
		request.setAttribute("pageBean", pageBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/jsp/admin/product/list.jsp";
	}
	/**
	 * 添加商品查询分类
	 * @param request
	 * @param response
	 * @return
	 */
	public String saveUI(HttpServletRequest request,HttpServletResponse response) {
		try {
		CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");
		List<Category> list = categoryService.findAll();
		request.setAttribute("list", list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "/jsp/admin/product/add.jsp";
	}
	
	/**
	 * 商品下架
	 */
	public String pushDown(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			String pid = request.getParameter("pid");
			ProductService productService = (ProductService) BeanFactory.getBean("productService");
			Product product = productService.findByPid(pid);
			//修改状态
			product.setPflag(1);
			
			productService.update(product);
			
			response.sendRedirect(request.getContextPath()+"/AdminProductServlet?method=findByPage&currPage=1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * 查询下架商品的信息
	 * @param request
	 * @param response
	 * @return
	 */
	public String findByPushDown(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			ProductService productService = (ProductService) BeanFactory.getBean("productService");
			List<Product> list = productService.findByPushDown();
			request.setAttribute("list", list);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "/jsp/admin/product/pushDown_list.jsp";
	}
	/**
	 * 下架商品上架
	 */
	public String pushUp(HttpServletRequest request,HttpServletResponse response) {
		try {
			String pid = request.getParameter("pid");
			ProductService productService = (ProductService) BeanFactory.getBean("productService");
			Product product = productService.findByPid(pid);
			//修改状态
			product.setPflag(0);
			
			productService.update(product);
			
			response.sendRedirect(request.getContextPath()+"/AdminProductServlet?method=findByPage&currPage=1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
