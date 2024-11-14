package com.jmorillo.indieStore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Wishlist {
	@Id
	@GeneratedValue 
	private int id;
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name="videogame_id")
	private Videogame videogame;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Videogame getVideogame() {
		return videogame;
	}
	
	public void setVideogame(Videogame videogame) {
		this.videogame = videogame;
	}
	
	
}
