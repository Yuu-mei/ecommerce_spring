package com.jmorillo.indieStore.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jmorillo.indieStore.model.Order;
import com.jmorillo.indieStore.model.orderStatus.OrderStatus;
import com.jmorillo.indieStore.services.OrderService;

@Controller
@RequestMapping("admin/")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@RequestMapping("order-list")
	public String getOrders(Model model) {
		model.addAttribute("orders", orderService.obtainAllOrders());
		return "admin/orders";
	}
	
	@RequestMapping("order-details")
	public String getOrderDetails(String order_id, Model model) {
		Order o = orderService.obtainOrderById(Integer.parseInt(order_id));
		model.addAttribute("order", o);
		Map<String, String> orderStatus = new HashMap<String, String>();
		orderStatus.put(OrderStatus.INCOMPLETE.name(), "Order started by user");
		orderStatus.put(OrderStatus.COMPLETE.name(), "Order completed by user");
		orderStatus.put(OrderStatus.FINISHED.name(), "Order finished by user");
		model.addAttribute("orderStatus", orderStatus);
		return "admin/order_details";
		
	}
}
