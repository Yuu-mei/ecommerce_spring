package com.jmorillo.indieStore.services;

import java.util.Date;
import java.util.List;

import com.jmorillo.indieStore.model.Order;
import com.jmorillo.indieStore.model.OrderProduct;
import com.jmorillo.indieStore.model.User;
import com.jmorillo.indieStore.model.dataTypes.OrderSummary;

public interface OrderService {
	// Admin methods
	List<Order> obtainAllOrders();
	Order obtainOrderById(int order_id);
	void editOrderStatus(int order_id, String status);
	// Client methods
	void processGeneralUserData(String fullName, String address, String country, String state, String zip_code, String phone, int userID);
	void processCardData(String cardOwner, String cardNumber, String cardType, String ccv, Date cardExpireDate, int userID);
	void processShippingDetails(String shippingDetails, String shippingType, int userID);
	void confirmOrder(int userID);
	OrderSummary obtainOrderSummary(int userID);
	List<OrderProduct> obtainOrderProductsByUser(User user);
}
