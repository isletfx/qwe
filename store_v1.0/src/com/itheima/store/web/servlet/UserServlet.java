package com.itheima.store.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.buffer.UnboundedFifoBuffer;

import com.itheima.store.dao.UserDao;
import com.itheima.store.domain.User;
import com.itheima.store.service.UserService;
import com.itheima.store.service.impl.UserServiceImpl;
import com.itheima.store.utils.BaseServlet;
import com.itheima.store.utils.BeanFactory;
import com.itheima.store.utils.MyDateConverter;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
	
	/**
	 * 跳转到注册页面的执行的方法:
	 */
	public String registerUI(HttpServletRequest request,HttpServletResponse response) {
		return "/jsp/register.jsp";
	}
	
	public String checkUsername(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			String username = request.getParameter("username");
			//UserService userService = new UserServiceImpl();
			UserService userService = (UserService) BeanFactory.getBean("userService");
			User existUser = userService.findByUsername(username);
			if (existUser != null ) {
				//用户名不可以使用
				response.getWriter().println("1");
			}else {
				//用户名可以使用
				response.getWriter().println("2");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;
	}
	
	public String register(HttpServletRequest request,HttpServletResponse response) {
		try {
			Map<String, String[]> map = request.getParameterMap();
			User user = new User();
			ConvertUtils.register(new MyDateConverter(), Date.class);
			BeanUtils.populate(user, map);
			UserService userService = (UserService) BeanFactory.getBean("userService");
			userService.save(user);
			//页面跳转
			request.setAttribute("msg", "注册成功,请去邮箱激活");
			return "/jsp/msg.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	
	public String active(HttpServletRequest request,HttpServletResponse response) {
		try {
			String code = request.getParameter("code");
			UserService userService = (UserService) BeanFactory.getBean("userService");
			User existUser = userService.findByCode(code);
			//判断
			if (existUser == null) {
				//说明激活码错误,或者激活码为空
				request.setAttribute("msg", "激活码错误,请重新激活");
			}else {
				//进行激活操作
				existUser.setState(2); //设置激活的状态为2   把激活码也置为空
				existUser.setCode(null);
				userService.update(existUser);
				request.setAttribute("msg","激活成功!请去登录");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "/jsp/msg.jsp";
	}
	
	//登录跳转
	public String loginUI(HttpServletRequest request,HttpServletResponse response) {
		
		return "/jsp/login.jsp";
	}
	
	//登录的方法
	public String login(HttpServletRequest request,HttpServletResponse response) {
		try {
			//校验验证码
			String code1 = request.getParameter("code");
			String code2 = (String) request.getSession().getAttribute("code");
			request.getSession().removeAttribute("code");
			if (!code1.equalsIgnoreCase(code2)) {
				request.setAttribute("msg", "验证码输入错误!!");
				return "/jsp/login.jsp";
			}
			Map<String, String[]> map = request.getParameterMap();
			User user = new User();
			BeanUtils.populate(user, map);
			UserService userService = (UserService) BeanFactory.getBean("userService");
			User existUser = userService.login(user);
			if (existUser != null ) {
				//登录成功:自动登录
				String autoLogin = request.getParameter("autoLogin");
				if ("true".equals(autoLogin)) {
					//说明勾选了自动登录的按钮
					Cookie cookie = new Cookie("autoLogin", existUser.getUsername()+"&"+existUser.getPassword());
					cookie.setPath("/store_v1.0");
					cookie.setMaxAge(7 * 24 * 60 * 60);
					response.addCookie(cookie);
				}
				//登录成功记住用户名
				String remember = request.getParameter("remember");
				if ("true".equals(remember)) {
					//勾选了记住用户名
					Cookie cookie = new Cookie("username", existUser.getUsername());
					cookie.setPath("/store_v1.0");
					cookie.setMaxAge(24 * 60 *60);
					response.addCookie(cookie);
				}
				if (!"true".equals(remember)) {
					//没有勾选了记住用户名
					Cookie cookie = new Cookie("username", "");
					cookie.setPath("/store_v1.0");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
				
				request.getSession().setAttribute("existUser", existUser);
				response.sendRedirect(request.getContextPath()+"/index.jsp");
				return null;
			}else {
				//登录失败
				request.setAttribute("msg", "账号或密码错误或用户未激活");
				return "/jsp/login.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		} 
	}
	//退出用户登录
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		//销魂session
		request.getSession().invalidate();
		//清空自动登录的cookie
		Cookie cookie = new Cookie("autoLogin", "");
		cookie.setPath("store_v1.0");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		try {
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}

