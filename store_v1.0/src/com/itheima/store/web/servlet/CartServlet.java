package com.itheima.store.web.servlet;

import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.store.domain.Cart;
import com.itheima.store.domain.CartItem;
import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;

/**
 * 购物模块的servlet	 
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 添加到购物车
	 */
	public String addCart(HttpServletRequest request,HttpServletResponse response) {
		try {
		//接受参数
		String pid = request.getParameter("pid");
		System.out.println("pid="+pid);
		Integer count = Integer.parseInt(request.getParameter("count"));
		System.out.println("count="+count);
		//封装CartItem
		CartItem cartItem = new CartItem();
		cartItem.setCount(count);
		ProductService productSerice = (ProductService) BeanFactory.getBean("productService");
		Product product = productSerice.findByPid(pid);
		cartItem.setProduct(product);
		//调用Cart中的方法处理
		Cart cart = getCart(request);
		cart.addCart(cartItem);
		//页面跳转
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}

	private Cart getCart(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}
	
	public String clearCart(HttpServletRequest request,HttpServletResponse response) {
		Cart cart = getCart(request);
		cart.clearCart();
		return "/jsp/cart.jsp";
	}
	
	public String removeCart(HttpServletRequest request,HttpServletResponse response) {
		String pid = request.getParameter("pid");
		Cart cart = getCart(request);
		cart.removeCart(pid);
		return "/jsp/cart.jsp";
	}
}
