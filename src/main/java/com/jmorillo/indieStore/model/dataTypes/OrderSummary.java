package com.jmorillo.indieStore.model.dataTypes;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderSummary {
	List<Map<String, Object>> videogames;
	//Step 1 - General Info
	private String fullName;
	private String address;
	private String country;
	private String state;
	private String zip_code;
	private String phone;
	//Step 2 - Card Info
	private String cardOwner;
	private String cardNumber;
	private String cardType;
	private String ccv;
	private Date cardExpireDate;
	//Step 3 - Shipping Info
	private String shippingDetails;
	private String shippingType;
	
	public List<Map<String, Object>> getVideogames() {
		return videogames;
	}
	
	public void setVideogames(List<Map<String, Object>> videogames) {
		this.videogames = videogames;
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
