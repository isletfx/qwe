package com.itheima.store.utils;

import javax.servlet.http.Cookie;

public class CookieUtils {

	public static Cookie findCookie(Cookie[] cookies,String cookieName) {
		if(cookies == null) {
			return null;
		}else {
			for (Cookie cookie : cookies) {
				if (cookieName.equals(cookie.getName())) {
					return cookie;
				}
			}
			return null; 
		}
		
	}
}
