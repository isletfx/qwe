package com.itheima.store.web.filter;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.itheima.store.domain.User;
import com.itheima.store.service.UserService;
import com.itheima.store.service.impl.UserServiceImpl;
import com.itheima.store.utils.CookieUtils;

public class AutoLoginFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		/**
		 *  首先我们进来先判断session里有没有值
		 *  如果session有值,我们直接放行
		 *  如果session没有值,我们获取cookie
		 *  	如果cookie里面没有值,我们直接放行
		 *  	如果cookie里面有值,我们去数据库查询这个user
		 *  		如果查询不到,说明cookie被串改了,放行
		 *  		如果查询到了,我们把这个cookie存到session里面,放行
		 *  		
		 */
		HttpServletRequest request2 = (HttpServletRequest) request;
		HttpSession session = request2.getSession();
		User existUser = (User) session.getAttribute("existUser");
		if (existUser != null ) {
			//不等于空,放行
			chain.doFilter(request2, response);
		}else {
			//session等于null,我们就获取cookie
			Cookie[] cookies = request2.getCookies();
			Cookie cookie = CookieUtils.findCookie(cookies, "autoLogin");
			if (cookie == null) {
				//cookie等于null我们直接放行
				chain.doFilter(request2, response);
			}else {
				//cookie不等于null,我们就获取到cookie里面存的值,去数据库里面查找
				String value = cookie.getValue();
				String username = value.split("&")[0];
				String password = value.split("&")[1];
				System.out.println(username);
				System.out.println(password);
				User user = new User();
				user.setUsername(username);
				user.setPassword(password);
				UserService userService = new UserServiceImpl();
				try {
					existUser = userService.login(user);
					if (existUser == null) {
						//没查到,说明cookie被串改了
						chain.doFilter(request2, response);
					}else {
						//查到了,就把cookie里面的数据放入session
						session.setAttribute("existUser", existUser);
						chain.doFilter(request2, response);
					}
				} catch (SQLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
	}
	@Override
	public void destroy() {
		// TODO 自动生成的方法存根
		
	}

}
