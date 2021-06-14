package com.itheima.store.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BeanFactory {
	public static Object getBean(String id) {
		//解析xml,使用dom4j创建对象
		SAXReader reader = new SAXReader();
		try {
			//读取xml文件获得document对象
			Document document = reader.read(BeanFactory.class.getClassLoader().getResourceAsStream("applicationContext.xml"));
			//使用xpath解析到元素
			Element element = (Element) document.selectSingleNode("//bean[@id='"+id+"']");
			//通过元素获取到class属性的值
			String value = element.attributeValue("class");
			//System.out.println(value);
			
			//使用反射生成实例
			Class clazz = Class.forName(value); //没有增强过的calss
			Object obj = clazz.newInstance();
			if (id.endsWith("Dao")) {
				Object objProxy = Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new InvocationHandler() {
					
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						if (method.getName().startsWith("save")) {
							System.out.println("权限校验===========");
							return method.invoke(obj, args);
						}
						return method.invoke(obj, args);
					}
				});
				return objProxy;
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
