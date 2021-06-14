package com.itheima.store.web.servlet;

import java.sql.Array;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.domain.PageBean;
import com.itheima.store.domain.Product;
import com.itheima.store.service.ProductService;
import com.itheima.store.service.impl.ProductServiceImpl;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.CookieUtils;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {
	
	public String findByCid(HttpServletRequest request,HttpServletResponse response) {
		try {
		String cid = request.getParameter("cid");
		Integer currPage = Integer.parseInt(request.getParameter("currPage"));
		//调用业务层
		//ProductSerice productSerice = new ProductSericeImpl();
		ProductService productSerice = (ProductService) BeanFactory.getBean("productService");
		PageBean<Product> pageBean = productSerice.findByPageCid(cid,currPage);
		//把数据存起来
		request.setAttribute("pageBean",pageBean);
		return "/jsp/product_list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
	
	public String findByPid(HttpServletRequest request,HttpServletResponse response) {
		try {
			//接受pid
			String pid = request.getParameter("pid");
			//调用业务层
			ProductService productSerice = (ProductService) BeanFactory.getBean("productService");
			Product product = productSerice.findByPid(pid);
			
			//记录用户的商品浏览记录
			//从cookie中获取
			Cookie[] cookies = request.getCookies();
			Cookie cookie = CookieUtils.findCookie(cookies, "history");
			//判断cookie是否为空
			if (cookie == null) {
				//如果为空就是第一次访问
				//如果是第一次访问    
				Cookie c = new Cookie("history", pid);
				c.setPath("/store_v1.0");
				c.setMaxAge(60*60*24);
				response.addCookie(c);
			}else {
				//如果不为空就不是第一次访问     1-2-3
				//获取到cookie里的值
				String value = cookie.getValue();
				//按照存进去的方式切分
				String[] ids = value.split("-");
				//把数组转换为链表集合(方便我们删除于添加)
				LinkedList<String> list = new LinkedList<String>(Arrays.asList(ids));
				if (list.contains(pid)) {
					//如果list里面包含了pid
					list.remove(pid);
					list.addFirst(pid);
				}else {
					//如果list里面没有包含了pid
					//说明没有浏览过
					//判断list的长度是否大于6
					if (list.size() >= 6) {
						//如果大于6
						list.removeLast();
						list.addFirst(pid);
					}else {
						//如果没有大于6
						list.addFirst(pid);
					}
				}
				StringBuffer sb = new StringBuffer();
				//遍历集合 组成对应的格式
				for (String id : list) {
					sb.append(id).append("-");
				}
				String idStr = sb.toString().substring(0, sb.length()-1);
				System.out.println(idStr);
				Cookie c = new Cookie("history", idStr);
				c.setPath("/store_v1.0");
				c.setMaxAge(60*60*24);
				response.addCookie(c);
			}
			
			//页面跳转
			request.setAttribute("product", product);
			return "/jsp/product_info.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
