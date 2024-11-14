package com.jmorillo.indieStore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class CartProduct {
	@Id
	@GeneratedValue
	private int cart_product_id;
	@OneToOne
	@JoinColumn(referencedColumnName = "videogame_id")
	private Videogame videogame;
	@ManyToOne
	@JoinColumn(name="cart_id")
	private Cart cart;
	private int quantity;
	
	public int getCart_product_id() {
		return cart_product_id;
	}
	
	public void setCart_product_id(int cart_product_id) {
		this.cart_product_id = cart_product_id;
	}
	
	public Videogame getVideogame() {
		return videogame;
	}
	
	public void setVideogame(Videogame videogame) {
		this.videogame = videogame;
	}
	
	public Cart getCart() {
		return cart;
	}
	
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
