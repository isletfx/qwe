package com.itheima.store.web.servlet;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.service.impl.ProductServiceImpl;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;

/**
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public String index(HttpServletRequest request,HttpServletResponse response) {
		try {
			//查询最新商品
			ProductService productSerice = (ProductService) BeanFactory.getBean("productService");
			List<Product> newList = productSerice.findByNew();
			//查询热门商品
			List<Product> hotList = productSerice.findByHot();
			request.setAttribute("newList", newList);
			request.setAttribute("hotList", hotList);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return "/jsp/index.jsp";
	}
}
