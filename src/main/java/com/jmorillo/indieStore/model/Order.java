package com.jmorillo.indieStore.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue
	private int order_id;
	@ManyToOne(optional=false)
	private User user;
	@OneToMany
	private List<OrderProduct> orderProducts = new ArrayList<OrderProduct>();
	private String status;
	//Step 1
	private String fullName;
	private String address;
	private String country;
	private String state;
	private String zip_code;
	private String phone;
	//Step 2
	private String cardOwner;
	private String cardNumber;
	private String cardType;
	private String ccv;
	private Date cardExpireDate;
	//Step 3
	private String shippingDetails;
	private String shippingType;
	
	public int getOrder_id() {
		return order_id;
	}
	
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	
	public User getUser() {
		return user;
	}
	
	public List<OrderProduct> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(List<OrderProduct> orderProducts) {
		this.orderProducts = orderProducts;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getZip_code() {
		return zip_code;
	}
	
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getCardOwner() {
		return cardOwner;
	}
	
	public void setCardOwner(String cardOwner) {
		this.cardOwner = cardOwner;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	public String getCardType() {
		return cardType;
	}
	
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	
	public String getCcv() {
		return ccv;
	}
	
	public void setCcv(String ccv) {
		this.ccv = ccv;
	}
	
	public Date getCardExpireDate() {
		return cardExpireDate;
	}
	
	public void setCardExpireDate(Date cardExpireDate) {
		this.cardExpireDate = cardExpireDate;
	}
	
	public String getShippingDetails() {
		return shippingDetails;
	}
	
	public void setShippingDetails(String shippingDetails) {
		this.shippingDetails = shippingDetails;
	}
	
	public String getShippingType() {
		return shippingType;
	}
	
	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}
	
	
}	
