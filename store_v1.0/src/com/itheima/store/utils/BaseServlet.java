package com.itheima.store.utils;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet{

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			req.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html;charset=UTF-8");
			//获取到方法名为method的参数
			String methodName = req.getParameter("method");
			if (methodName == null || "".equals(methodName)) {
				resp.getWriter().println("method的参数为null");
				return;
			}
			//获取到继承该类的class对象
			Class clazz = this.getClass();
			//获取到这个方法
			Method method = clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			String path = (String) method.invoke(this, req,resp);
			if (path!=null) {
				req.getRequestDispatcher(path).forward(req, resp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	
}
