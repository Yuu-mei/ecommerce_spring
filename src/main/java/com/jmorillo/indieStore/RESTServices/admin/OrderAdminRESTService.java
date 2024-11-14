package com.jmorillo.indieStore.RESTServices.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmorillo.indieStore.services.OrderService;

@RestController
@RequestMapping("admin/")
public class OrderAdminRESTService {
	@Autowired
	OrderService orderService;
	
	@RequestMapping("change-order-status")
	public void changeOrderStatus(@RequestParam("id") Integer order_id,String status) {
		orderService.editOrderStatus(order_id, status);
	}
}
