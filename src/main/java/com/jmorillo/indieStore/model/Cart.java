package com.jmorillo.indieStore.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Cart {
	@Id
	@GeneratedValue
	private long cart_id;
	@OneToOne
	private User user;
	@OneToMany(mappedBy="cart")
	private List<CartProduct> cartProducts = new ArrayList<CartProduct>();
	private Date lastUse;

	public long getCart_id() {
		return cart_id;
	}
	
	public void setCart_id(long cart_id) {
		this.cart_id = cart_id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<CartProduct> getCartProducts() {
		return cartProducts;
	}
	
	public void setCartProducts(List<CartProduct> cartProducts) {
		this.cartProducts = cartProducts;
	}
	
	public Date getLastUse() {
		return lastUse;
	}
	
	public void setLastUse(Date lastUse) {
		this.lastUse = lastUse;
	}
	
	
}
