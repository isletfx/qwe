package com.itheima.store.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtils2 {
	public static boolean sendmail(String to,String code) {
		boolean flag = true;
		//建立邮件会话
		Properties pro = new Properties();
		pro.put("mail.smtp.host","smtp.qq.com");//存储发送邮件的服务器
		pro.put("mail.smtp.auth","true"); //通过服务器验证
		Session s =Session.getInstance(pro); //根据属性新建一个邮件会话
		//s.setDebug(true);
		//由邮件会话新建一个消息对象
		MimeMessage message = new MimeMessage(s);
		//设置邮件
		InternetAddress fromAddr = null;
		InternetAddress toAddr = null;
		try{
			fromAddr = new InternetAddress(714562381+"@qq.com"); //邮件发送地址
			message.setFrom(fromAddr); //设置发送地址
			toAddr = new InternetAddress(to); //邮件接收地址
			message.setRecipient(Message.RecipientType.TO, toAddr); //设置接收地址
			message.setSubject("来自官方商城一个激活邮件"); //设置邮件标题
			message.setText("<h1>来自购物天堂官方商城的激活邮件:请点击下面链接激活</h1><h3><a href='http://localhost:8080/store_v1.0/UserServlet?method=active&code="+code+"'>http://localhost:8080/store_v1.0/UserServlet?method=active&code="+code+"<a/></h3>"); //设置邮件正文
			message.setSentDate(new Date()); //设置邮件日期
			message.saveChanges(); //保存邮件更改信息
			Transport transport = s.getTransport("smtp");
			transport.connect("smtp.qq.com", "714562381", "jfdqvsayedlbbaja"); //服务器地址，邮箱账号，邮箱密码
			transport.sendMessage(message, message.getAllRecipients()); //发送邮件
			transport.close();//关闭
		}catch (Exception e){
			e.printStackTrace();
			flag = false;//发送失败
		}
		return flag;
	}
	public static void main(String[] args) {
		MailUtils2.sendmail("islets@yeah.net", "123123");
	}
}

