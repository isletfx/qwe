package com.itheima.store.web.servlet;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.math.IntRange;

import com.itheima.store.domain.Cart;
import com.itheima.store.domain.CartItem;
import com.itheima.store.domain.Order;
import com.itheima.store.domain.OrderItem;
import com.itheima.store.domain.PageBean;
import com.itheima.store.domain.User;
import com.itheima.store.service.OrderService;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.PaymentUtil;
import com.itheima.store.utils.UUIDUtils;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	public String saveOrder(HttpServletRequest request,HttpServletResponse response) {
		Order order = new Order();
		order.setOid(UUIDUtils.getUUID());
		order.setOrdertime(new Date());
		order.setState(1);//未付款
		
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if (cart == null) {
			System.out.println("为空");
			request.setAttribute("msg", "购物车里没有东西");
			return "/jsp/msg.jsp";
		}
		order.setTotal(cart.getTotal());
		
		
		User user = (User) request.getSession().getAttribute("existUser");
		if (user == null) {
			request.setAttribute("msg", "用户名为空");
			return "/jsp/login.jsp";
		}
		order.setUser(user);
		
		for (CartItem cartItem: cart.getMap().values()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(UUIDUtils.getUUID());
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);
			
			order.getOrderItems().add(orderItem);
		}
		//调用业务层,提交订单
		OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
		orderService.save(order);
		
		//提交订单之前,清空购物车
		cart.clearCart();
		request.getSession().setAttribute("cart", null);
		
		//页面跳转
		request.setAttribute("order", order);
		return "/jsp/order_info.jsp";
	}
	
	public String findByUid(HttpServletRequest request,HttpServletResponse response) {
		try {
		//接受参数
		Integer currPage = Integer.parseInt(request.getParameter("currPage"));
		//获取用户对象
		User user = (User) request.getSession().getAttribute("existUser");
		//调用业务层
		OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
		PageBean<Order> pageBean = orderService.findByUid(user.getUid(),currPage);
		//存入域里
		request.setAttribute("pageBean", pageBean);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//页面跳转
		return "/jsp/order_list.jsp";
	}
	
	public String findByOid(HttpServletRequest request,HttpServletResponse response) {
		try {
		String oid = request.getParameter("oid");
		OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
		Order order = orderService.findByOid(oid);
		request.setAttribute("order", order);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/jsp/order_info.jsp";
	}
	
	public String payOrder(HttpServletRequest request,HttpServletResponse response) {
		try {
		//接受参数
		String oid = request.getParameter("oid");
		String address = request.getParameter("address");
		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		String pd_FrpId = request.getParameter("pd_FrpId");
		
		//修改数据库,姓名,地址,电话
		OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
		Order order = orderService.findByOid(oid);
		order.setAddress(address);
		order.setName(name);
		order.setTelephone(telephone);
		orderService.update(order);
		
		//付款:跳转到网银页面
		String p0_Cmd = "Buy";
		String p1_MerId = "10000326625";
		String p2_Order = oid;
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		String p8_Url = "http://localhost:8080/store_v1.0/OrderServlet?method=callBack";
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		String keyValue = "0acqgug6x57m0wrsiod6clpn1ezh47r2ot5h1zkq5dztiic8y5xkm5g0p0ek";
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
		
		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);
		
		response.sendRedirect(sb.toString());
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String callBack(HttpServletRequest request,HttpServletResponse response) {
		try {
		//接受回来的参数
		String oid = request.getParameter("r6_Order");
		String money = request.getParameter("r3_Amt");
		//修改订单状态
		OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
		Order order = orderService.findByOid(oid);
		order.setState(2);
		orderService.update(order);
		
		request.setAttribute("msg", "你的订单:"+oid+"付款成功,付款的金额为"+money);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/jsp/msg.jsp";
	}
	public String finish(HttpServletRequest request,HttpServletResponse response) {
		try {
			//接受回来的参数
			String oid = request.getParameter("oid");
			OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
			Order order = orderService.findByOid(oid);
			order.setState(4);
			orderService.update(order);
			
			response.sendRedirect(request.getContextPath()+"/OrderServlet?method=findByUid&currPage=1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
