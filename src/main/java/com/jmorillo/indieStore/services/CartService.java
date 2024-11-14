package com.jmorillo.indieStore.services;

import java.util.List;
import java.util.Map;

import com.jmorillo.indieStore.model.Cart;

public interface CartService {
	void addProduct(int videogame_id, int user_id, int quantity);
	void removeProduct(int videogame_id, int user_id);
	void alterGameQuantity(int videogame_id, int user_id, int quantity);
	List<Map<String, Object>> obtainUserCartProducts(int user_id);
	//To remove games that have been deleted we obtain all the cards and cycle through their products
	List<Cart> obtainAllCarts();
}
