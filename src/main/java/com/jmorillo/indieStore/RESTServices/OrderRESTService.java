package com.jmorillo.indieStore.RESTServices;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmorillo.indieStore.model.User;
import com.jmorillo.indieStore.model.dataTypes.OrderSummary;
import com.jmorillo.indieStore.services.OrderService;

@RestController
public class OrderRESTService {
	@Autowired
	OrderService orderService;
	
	// Might be even better to have an auto execute that saves the ID in a var and reuse that...but security concerns too
	private int obtainUserData(HttpServletRequest req) {
		User u = (User) req.getSession().getAttribute("user");
		return u.getUser_id();
	}
	
	@RequestMapping("process-general-user-data")
	public String processGeneralUserData(String fullName, String address, String country, String state, String zip_code, String phone, HttpServletRequest req) {
		orderService.processGeneralUserData(fullName, address, country, state, zip_code, phone, obtainUserData(req));
		return "ok";
	}
	
	@RequestMapping("process-card-data")
	public String processCardData(String cardOwner, String cardNumber, String cardType, String ccv, @DateTimeFormat(pattern="yyyy-MM-dd") Date cardExpireDate, HttpServletRequest req) {
		orderService.processCardData(cardOwner, cardNumber, cardType, ccv, cardExpireDate, obtainUserData(req));
		return "ok";
	}
	
	@RequestMapping("process-shipping-details")
	public OrderSummary processShippingDetails(String shippingDetails, String shippingType, HttpServletRequest req) {
		orderService.processShippingDetails(shippingDetails, shippingType, obtainUserData(req));
		OrderSummary orderSummary = orderService.obtainOrderSummary(obtainUserData(req));
		return orderSummary;
	}
	
	@RequestMapping("checkout")
	public String checkout(HttpServletRequest req) {
		orderService.confirmOrder(obtainUserData(req));
		return "order-confirmed";
	}
}
