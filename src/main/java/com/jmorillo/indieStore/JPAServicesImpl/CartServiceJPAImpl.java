package com.jmorillo.indieStore.JPAServicesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmorillo.indieStore.SQLConstants.SQLConstants;
import com.jmorillo.indieStore.model.Cart;
import com.jmorillo.indieStore.model.CartProduct;
import com.jmorillo.indieStore.model.User;
import com.jmorillo.indieStore.model.Videogame;
import com.jmorillo.indieStore.services.CartService;
import com.jmorillo.indieStore.utilities.Utilities;

@Service
@Transactional
public class CartServiceJPAImpl implements CartService{
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void addProduct(int videogame_id, int user_id, int quantity) {
		User user = entityManager.find(User.class, user_id);
		Cart cart = user.getCart();
		
		if(cart == null) {
			cart = new Cart();
			cart.setUser(user);
			user.setCart(cart);
			entityManager.persist(cart);
		}
		
		boolean productExist = false;
		for (CartProduct cartProduct : cart.getCartProducts()) {
			if(cartProduct.getVideogame().getVideogame_id() == videogame_id) {
				productExist = true;
				cartProduct.setQuantity(cartProduct.getQuantity()+quantity);
				entityManager.merge(cartProduct);
				break;
			}
		}
		
		if(!productExist) {
			CartProduct cartProduct = new CartProduct();
			Videogame vg = entityManager.find(Videogame.class, videogame_id);
			cartProduct.setCart(cart);
			cartProduct.setVideogame(vg);
			cartProduct.setQuantity(quantity);
			entityManager.persist(cartProduct);
		}
	}

	@Override
	public List<Map<String, Object>> obtainUserCartProducts(int user_id) {
		User user = entityManager.find(User.class, user_id);
		Cart cart = user.getCart();
		List<Map<String, Object>> res = new ArrayList<Map<String,Object>>();
		
		if(cart != null) {
			Query query = entityManager.createNativeQuery(SQLConstants.SQL_OBTAIN_CART_VIDEOGAMES);
			query.setParameter("id", cart.getCart_id());
			res = Utilities.processNativeQuery(query);
		}

		return res;
	}

	@Override
	public void removeProduct(int videogame_id, int user_id) {
		User user = entityManager.find(User.class, user_id);
		Cart cart = user.getCart();
		for(CartProduct cartProduct : cart.getCartProducts()) {
			if(cartProduct.getVideogame().getVideogame_id() == videogame_id) {
				entityManager.remove(cartProduct);
				break;
			}
		}
	}

	@Override
	public void alterGameQuantity(int videogame_id, int user_id, int quantity) {
		User user = entityManager.find(User.class, user_id);
		Cart cart = user.getCart();
		for(CartProduct cartProduct : cart.getCartProducts()) {
			if(cartProduct.getVideogame().getVideogame_id() == videogame_id) {
				cartProduct.setQuantity(quantity);
				entityManager.merge(cartProduct);
				break;
			}
		}
	}

	@Override
	public List<Cart> obtainAllCarts() {
		return entityManager.createQuery("select c from Cart c").getResultList();
	}

}
