package com.itheima.store.domain;
/**
 * 购物车
 * @author AAA
 *
 */

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
	/**
	 * 购物车的属性
	 */
	private Double total = 0d; //总计
	// map集合用于存放购物项,map的key是商品的id是唯一性的,map的value是购物项,到时候可以直接通过map的key来删除购物项;
	private Map<String, CartItem> map = new LinkedHashMap<String, CartItem>();
	
	public Double getTotal() {
		return total;
	}
	public Map<String, CartItem> getMap() {
		return map;
	} 
	
	/**
	 * 购物车的方法
	 */
	// 将购物项添加到购物车
	public void addCart(CartItem cartItem) {
		//判断购物项在没在购物车里
		//获取到添加的这个购物项的id
		String pid = cartItem.getProduct().getPid();
		//判断购物车里有没有这个购物项 
		if (map.containsKey(pid)) {
			//购物车中存在该商品
			
			//得到原来的购物项
			CartItem _cartItem = map.get(pid);
			//现在的购物项 = 原来的购物项 + 新添加的购物项
			_cartItem.setCount(_cartItem.getCount() + cartItem.getCount());
		}else {
			//购物车里没有该商品
			map.put(pid, cartItem);
		}
		//总计 = 现在的总计+新添加进来的购物项的小计(总计始终是发生变化的)
		total += cartItem.getSubtotal();
	}
	// 从购物车中删除购物项
	public void removeCart(String pid) {
		//从map中移除某个元素
		CartItem cartItem = map.remove(pid);
		//总金额 减去 移除购物项的小计 
		total -= cartItem.getSubtotal();
	}
	// 清空购物车
	public void clearCart() {
		//清空map集合
		map.clear();
		//设置总金额为0
		total = 0d;
	}
}
