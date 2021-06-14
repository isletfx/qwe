package com.itheima.store.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;
import com.itheima.store.service.OrderService;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * Servlet implementation class AdminOrderServlet
 */
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public String findAll(HttpServletRequest request,HttpServletResponse response) {
		try {
			//接受状态参数
			String state = request.getParameter("state");
			OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
			List<Order> list = null;
			if (state == null ) {
				//查询所有订单
				list = orderService.findAll();
			}else {
				//按状态查询订单
				int pstate = Integer.parseInt(state);
				list = orderService.findByState(pstate);
			}
			request.setAttribute("list", list);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "/jsp/admin/order/list.jsp";
	}
	
	public String showDateil(HttpServletRequest request,HttpServletResponse response) {
		try {
			String oid = request.getParameter("oid");
			OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
			
			List<OrderItem> list = orderService.showDateil(oid);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[]{"order"});
			JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
			
			response.getWriter().println(jsonArray);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String send(HttpServletRequest request,HttpServletResponse response) {
		try {
			String oid = request.getParameter("oid");
			OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
			Order order = orderService.findByOid(oid);
			order.setState(3);
			orderService.update(order);
			
			response.sendRedirect(request.getContextPath()+"/AdminOrderServlet?method=findAll");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
