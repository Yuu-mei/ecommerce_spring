package com.jmorillo.indieStore.RESTServices;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jmorillo.indieStore.model.User;
import com.jmorillo.indieStore.services.CartService;

@RestController
public class CartRESTService {
	@Autowired
	CartService cartService;
	
	@RequestMapping("add-game-to-cart")
	public String AddGameToCart(@RequestParam("id") Integer videogame_id, @RequestParam("quantity") Integer quantity, HttpServletRequest req) {
		User u = (User) req.getSession().getAttribute("user");
		cartService.addProduct(videogame_id, u.getUser_id(), quantity);
		return "OK";
	}
	
	@RequestMapping("obtain-cart-products")
	public List<Map<String, Object>> obtainCartProducts(HttpServletRequest req) {
		User u = (User) req.getSession().getAttribute("user");
		return cartService.obtainUserCartProducts(u.getUser_id());
	}
	
	@RequestMapping("remove-game-from-cart")
	public void RemoveGameFromCart(@RequestParam("id") Integer videogame_id, HttpServletRequest req) {
		User u = (User) req.getSession().getAttribute("user");
		cartService.removeProduct(videogame_id, u.getUser_id());
	}
	
	@RequestMapping("alter-game-quantity")
	public void AlterGameQuantity(@RequestParam("id") Integer videogame_id, @RequestParam("quantity") Integer quantity, HttpServletRequest req) {
		User u = (User) req.getSession().getAttribute("user");
		cartService.alterGameQuantity(videogame_id, u.getUser_id(), quantity);
	}
}
