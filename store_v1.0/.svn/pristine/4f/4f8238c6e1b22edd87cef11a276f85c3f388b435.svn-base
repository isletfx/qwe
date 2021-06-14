package com.itheima.store.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtils {
	public static void sendMain(String to,String code) {
		//获得连接
		Properties properties = new Properties();
		//smtp.qq.com  qq的   smtp.163.com  网易的
		properties.setProperty("mail.smtp.host","smtp.163.com");//存储发送邮件的服务器
		properties.setProperty("mail.smtp.auth","true"); //通过服务器验证
	
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("hibernal@163.com", "RBSKAUBVOOYXJHBE"); //授权密码登录  qq:jfdqvsayedlbbaja    163:RBSKAUBVOOYXJHBE
			}
		});
		//构建邮件:
		try {
			//设置发件人
			Message message = new MimeMessage(session); 
			message.setFrom(new InternetAddress("hibernal@163.com"));
			//设置收件人
			message.addRecipient(RecipientType.TO, new InternetAddress(to));
			//设置主题
			message.setSubject("来自官方商城一个激活邮件");
			//正文
			message.setContent("<h1>来自购物天堂官方商城的激活邮件:请点击下面链接激活</h1><h3><a href='http://localhost:8080/store_v1.0/UserServlet?method=active&code="+code+"'>http://localhost:8080/store_v1.0/UserServlet?method=active&code="+code+"<a/></h3>", "text/html;charset=UTF-8");   //第一个是邮件内容,第二个是邮件的格式
		
			//发送邮件
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) {
		MailUtils.sendMain("islets@yeah.net", "2312133131132148454");
	}
}
