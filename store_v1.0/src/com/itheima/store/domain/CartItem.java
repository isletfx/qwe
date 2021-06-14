package com.itheima.store.domain;
/**
 * 购物项
 * @author AAA
 *
 */
public class CartItem {
	private Product product; //商品
	private Integer count;	//商品数量
	private Double subtotal; //小计
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getSubtotal() {
		return count * product.getShop_price();
	}
	/*public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}*/
	
}
