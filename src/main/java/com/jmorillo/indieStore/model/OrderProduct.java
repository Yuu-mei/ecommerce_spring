package com.jmorillo.indieStore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class OrderProduct {
	@Id
	@GeneratedValue
	private int order_product_id;
	@ManyToOne
	@JoinColumn(name="order_id")
	private Order order;
	@OneToOne
	@JoinColumn(referencedColumnName = "videogame_id")
	private Videogame videogame;
	private int quantity;
	
	public int getOrder_product_id() {
		return order_product_id;
	}
	
	public void setOrder_product_id(int order_product_id) {
		this.order_product_id = order_product_id;
	}
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	public Videogame getVideogame() {
		return videogame;
	}
	
	public void setVideogame(Videogame videogame) {
		this.videogame = videogame;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
