package com.jmorillo.indieStore.JPAServicesImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmorillo.indieStore.model.OrderProduct;
import com.jmorillo.indieStore.model.User;
import com.jmorillo.indieStore.model.Videogame;
import com.jmorillo.indieStore.model.dataTypes.UserInfo;
import com.jmorillo.indieStore.services.OrderService;
import com.jmorillo.indieStore.services.UserService;
import com.jmorillo.indieStore.services.WishlistService;

@Service
@Transactional
public class UserServiceJPAImpl implements UserService{
	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	OrderService orderService;
	@Autowired
	WishlistService wishlistService;

	@Override
	public List<User> obtainAllUsers() {
		return entityManager.createQuery("select u from User u").getResultList();
	}

	@Override
	public void createUser(User u) {
		entityManager.persist(u);
	}

	@Override
	public void editUser(User u) {
		entityManager.merge(u);
	}

	@Override
	public void deleteUser(int user_id) {
		User u = (User) entityManager.find(User.class, user_id);
		entityManager.remove(u);
	}

	@Override
	public User obtainUserById(int user_id) {
		return (User) entityManager.find(User.class, user_id);
	}

	@Override
	public User obtainUserByEmailPass(String email, String pwd) {
		Query query = entityManager.createQuery("select u from User u where u.email = :email and u.password = :pwd");
		query.setParameter("email", email);
		query.setParameter("pwd", pwd);
		List<User> results = query.getResultList();
		if(results.size() == 0) {
			return null;
		}else {
			return results.get(0);
		}
	}

	@Override
	public UserInfo obtainUserInfoByUsername(String username) {
		//Let's create the object first
		UserInfo uf = new UserInfo();
		//Obtaining the user data
		Query userQuery = entityManager.createQuery("select u from User u where u.username = :username");
		userQuery.setParameter("username", username);
		User u = (User) userQuery.getSingleResult();
		uf.setUser_id(u.getUser_id());
		uf.setUsername(u.getUsername());
		uf.setEmail(u.getEmail());
		uf.setPhone(u.getPhone());
		uf.setCountry(u.getCountry());
		// Obtaining the bought games from the order
		List<OrderProduct> orderProducts = (List<OrderProduct>) orderService.obtainOrderProductsByUser(u);
		// Newly added users may have no orders but still will want to check their profile so we return an empty list in that case to avoid it breaking
		if(orderProducts != null) {
			List<Map<String, Object>> orderVideogames = new ArrayList<Map<String,Object>>();
			for (OrderProduct orderProduct : orderProducts) {
				Map<String, Object> op = new HashMap<String, Object>();
				op.put("videogame_id", orderProduct.getVideogame().getVideogame_id());
				op.put("quantity", orderProduct.getQuantity());
				op.put("order_id", orderProduct.getOrder().getOrder_id());
				op.put("videogame_title", orderProduct.getVideogame().getTitle());
				orderVideogames.add(op);
			}
			uf.setBought_games(orderVideogames);			
		}else {
			uf.setBought_games(new ArrayList<Map<String,Object>>());
		}
		// Obtain the games from the wishlist
		List<Videogame> wishlisted_videogames = wishlistService.getUserWishlist(u);
		List<Map<String, Object>> wl_videogames = new ArrayList<Map<String,Object>>();
		for (Videogame videogame : wishlisted_videogames) {
			Map<String, Object> wlvg = new HashMap<String, Object>();
			wlvg.put("videogame_id", videogame.getVideogame_id());
			wlvg.put("videogame_title", videogame.getTitle());
			wl_videogames.add(wlvg);
		}
		uf.setWishlist(wl_videogames);
		
		return uf;
	}
}
