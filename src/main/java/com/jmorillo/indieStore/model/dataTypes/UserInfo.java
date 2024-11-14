package com.jmorillo.indieStore.model.dataTypes;

import java.util.List;
import java.util.Map;

public class UserInfo {
	private String username;
	// This is to retrieve the image more easily
	private int user_id;
	private String email;
	private String phone;
	private String country;
	private List<Map<String, Object>> wishlist;
	private List<Map<String, Object>> bought_games;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}

	public List<Map<String, Object>> getWishlist() {
		return wishlist;
	}

	public void setWishlist(List<Map<String, Object>> wishlist) {
		this.wishlist = wishlist;
	}

	public List<Map<String, Object>> getBought_games() {
		return bought_games;
	}

	public void setBought_games(List<Map<String, Object>> bought_games) {
		this.bought_games = bought_games;
	}
	
}
