package com.jmorillo.indieStore.JPAServicesImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmorillo.indieStore.SQLConstants.SQLConstants;
import com.jmorillo.indieStore.model.Cart;
import com.jmorillo.indieStore.model.CartProduct;
import com.jmorillo.indieStore.model.Developer;
import com.jmorillo.indieStore.model.Image;
import com.jmorillo.indieStore.model.Order;
import com.jmorillo.indieStore.model.OrderProduct;
import com.jmorillo.indieStore.model.Tag;
import com.jmorillo.indieStore.model.Videogame;
import com.jmorillo.indieStore.services.CartService;
import com.jmorillo.indieStore.services.ImageService;
import com.jmorillo.indieStore.services.OrderService;
import com.jmorillo.indieStore.services.VideogameService;
import com.jmorillo.indieStore.utilities.Utilities;

@Service
@Transactional
public class VideogameServiceJPAImpl implements VideogameService {
	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	CartService cartService;
	@Autowired
	OrderService orderService;
	@Autowired
	ImageService imageService;

	@Override
	public List<Videogame> obtainAllVideogames() {
		return entityManager.createQuery("select v from Videogame v").getResultList();
	}
	
	@Override
	public List<Videogame> obtainAllVideogames(String videogame_name, int from, int resultsPerPage) {
		Query query = entityManager.createQuery("select v from Videogame v where v.title like :videogame_title")
				.setParameter("videogame_title", "%"+videogame_name+"%")
				.setFirstResult(from)
				.setMaxResults(resultsPerPage);
		return query.getResultList();
	}
	
	@Override
	public List<Videogame> obtainAllVideogamesREST(String videogame_name, int from, int resultsPerPage) {
		Query query = entityManager.createQuery("select v.videogame_id, v.title from Videogame v where v.title like :videogame_title")
				.setParameter("videogame_title", "%"+videogame_name+"%")
				.setFirstResult(from)
				.setMaxResults(resultsPerPage);
		return query.getResultList();
	}

	@Override
	public Videogame obtainVideogameById(int videogame_id) {
		return (Videogame) entityManager.find(Videogame.class, videogame_id);
	}

	@Override
	public void createVideogame(Videogame v) {
		// Tag
		Tag t = entityManager.find(Tag.class, v.getTag_id());
		v.setTag(t);
		// Devs
		List<Developer> dev_list = new ArrayList<Developer>();
		for (int dev_id : v.getDevelopers_ids()) {
			Developer dev = entityManager.find(Developer.class, dev_id);
			dev_list.add(dev);
		}
		v.setDevelopers(dev_list);
		// Images
		for (Image img : v.getVgImages()) {
			img.setVideogame(v);
			imageService.uploadImage(img);
		}
		// Save
		entityManager.persist(v);
	}

	@Override
	public void editVideogame(Videogame v) {
		// Tag
		Tag t = entityManager.find(Tag.class, v.getTag_id());
		v.setTag(t);
		// Devs
		List<Developer> dev_list = new ArrayList<Developer>();
		for (int dev_id : v.getDevelopers_ids()) {
			Developer dev = entityManager.find(Developer.class, dev_id);
			dev_list.add(dev);
		}
		v.setDevelopers(dev_list);
		// Images
		imageService.deleteImagesOfVideogame(v.getVideogame_id());
		for (Image img : v.getVgImages()) {
			img.setVideogame(v);
			imageService.uploadImage(img);
		}
		//Update
		entityManager.merge(v);
	}

	@Override
	public void deleteVideogame(int videogame_id) {
		Videogame v = (Videogame) entityManager.find(Videogame.class, videogame_id);
		
		// For safety reasons we won't allow games to be deleted IF they are part of an OrderProduct
		List<Order> orders = orderService.obtainAllOrders();
		for (Order order : orders) {
			for (OrderProduct orderProduct : order.getOrderProducts()) {
				if(orderProduct.getVideogame().getVideogame_id() == videogame_id) {
					System.err.println("You are trying to delete a game that is part of an order already");
					return;
				}
			}
		}
		
		// Avoid issues when the product is part of a cart
		List<Cart> carts = cartService.obtainAllCarts();
		for (Cart cart : carts) {
			for (CartProduct cartProduct : cart.getCartProducts()) {
				if(cartProduct.getVideogame().getVideogame_id() == videogame_id) {
					entityManager.remove(cartProduct);
				}
			}
		}
		
		// To fix the issue when deleting a videogame due to the many to many relationship
		// We also do this after deleting it from the other places as to avoid having the developer be removed from the game BEFORE it has been completely erased elsewhere
		List<Developer> devs = v.getDevelopers();
		for(int i = 0; i < devs.size(); i++) {
			Developer dev = devs.get(i);
			v.removeDeveloper(dev);
		}
		entityManager.merge(v);
		
		entityManager.remove(v);
	}

	@Override
	public Map<String, Object> obtainvideogameDetailsById(int videogame_id) {
		Query query = entityManager.createNativeQuery(SQLConstants.SQL_OBTAIN_VIDEOGAME_DETAIL);
		query.setParameter("id", videogame_id);
		NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
		nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		// Let's manipulate the result to associate the devs ids and dev names under a single key
		Map<String, Object> res = (Map<String, Object>) nativeQuery.getSingleResult();
	    // Retrieve the columns separated (should be properly ordered, but I have no order by on the query so I may be lying to myself)
	    String dev_ids = (String) res.get("dev_ids");
	    String dev_names = (String) res.get("dev_names");
	    // Creating a list to add the developers properly joined
	    List<Map<String, String>> developers = new ArrayList<>();
	    
	    if (dev_ids != null && dev_names != null) {
	        String[] dev_ids_arr = dev_ids.split(",");
	        String[] dev_names_arr = dev_names.split(",");

	        // Both arrays should be same length (and if it isn't...well something has gone awfully wrong in the DB)
            for (int i = 0; i < dev_ids_arr.length; i++) {
                Map<String, String> developer = new HashMap<>();
                developer.put("dev_id", dev_ids_arr[i]);
                developer.put("dev_name", dev_names_arr[i]);
                developers.add(developer);
            }
	    }
	    
		res.remove("dev_ids");
		res.remove("dev_names");
	    
	    // Adding the developers to the result so we can easily access them in the client
	    res.put("developers", developers);
		
		return res;
	}

	@Override
	public List<Map<String, Object>> obtainVideogamesList() {
		Query query = entityManager.createNativeQuery(SQLConstants.SQL_OBTAIN_VIDEOGAME_LIST);
		return Utilities.processNativeQuery(query);
	}

	@Override
	public List<Map<String, Object>> obtainLatestVideogames() {
		Query query = entityManager.createNativeQuery(SQLConstants.SQL_OBTAIN_LATEST_GAMES);
		List<Map<String, Object>> latest_videogames_list = Utilities.processNativeQuery(query);
		
		// We need to further process the query as we need to separate the dev_ids and dev_names from the group_concat
		for (Map<String, Object> game_map : latest_videogames_list) {
			String dev_ids = (String) game_map.get("dev_ids");
			String dev_names = (String) game_map.get("dev_names");
			List<Map<String, String>> developers = new ArrayList<Map<String,String>>();
			
			if(dev_ids != null && dev_names != null) {
				String[] dev_ids_arr = dev_ids.split(",");
				String[] dev_names_arr = dev_names.split(",");
				
				for(int i = 0; i < dev_ids_arr.length; i++) {
	                Map<String, String> developer = new HashMap<>();
	                developer.put("dev_id", dev_ids_arr[i]);
	                developer.put("dev_name", dev_names_arr[i]);
	                developers.add(developer);
				}
			}
			
			// Let's clean up the map as we no longer need the dev_ids and dev_names due to them being inside the developers list
			game_map.remove("dev_ids");
			game_map.remove("dev_names");
			
			game_map.put("developers", developers);
		}
		
		// Adding the property to the first element so Mustache knows when to add the active class to the caroussel item
		latest_videogames_list.get(0).put("first_element", true);
		
		return latest_videogames_list;
	}

	@Override
	public Map<String, Object> obtainVideogamesByDeveloper(int dev_id) {
		Query query = entityManager.createNativeQuery(SQLConstants.SQL_OBTAIN_VIDEOGAMES_BY_DEV);
		query.setParameter("dev_id", dev_id);
		List<Map<String, Object>> dev_videogames_list = Utilities.processNativeQuery(query);
		
		// I don't want the dev name to be in every game, just once in the entire map and so I will add it on the list and take it from the maps
		// Then we transform the list into the desired data structure (basically have the videogames inside their own array and the developer by itself)
		return Utilities.processList("dev_name", dev_videogames_list);
	}
	
	@Override
	public Map<String, Object> obtainVideogamesByDeveloper(int dev_id, int from, int resultsPerPage) {
		Query query = entityManager.createNativeQuery(SQLConstants.SQL_OBTAIN_VIDEOGAMES_BY_DEV_PAGINATION);
		query.setParameter("dev_id", dev_id);
		query.setParameter("offset", from);
		query.setParameter("limit", resultsPerPage);
		List<Map<String, Object>> dev_videogames_list = Utilities.processNativeQuery(query);
		return Utilities.processList("dev_name", dev_videogames_list);
	}

	@Override
	public Map<String, Object> obtainVideogamesByTag(int tag_id) {
		Query query = entityManager.createNativeQuery(SQLConstants.SQL_OBTAIN_VIDEOGAMES_BY_TAG);
		query.setParameter("tag_id", tag_id);
		
		List<Map<String, Object>> videogames_tagged_list = Utilities.processNativeQuery(query);
		
		return Utilities.processList("tag_name", videogames_tagged_list);
	}
	
	@Override
	public Map<String, Object> obtainVideogamesByTag(int tag_id, int from, int resultsPerPage) {
		Query query = entityManager.createNativeQuery(SQLConstants.SQL_OBTAIN_VIDEOGAMES_BY_TAG_PAGINATION);
		query.setParameter("tag_id", tag_id);
		query.setParameter("limit", resultsPerPage);
		query.setParameter("offset", from);
		
		List<Map<String, Object>> videogames_tagged_list = Utilities.processNativeQuery(query);
		
		return Utilities.processList("tag_name", videogames_tagged_list);
	}

	@Override
	public List<Map<String, Object>> obtainSimilarVideogames(int tag_id, int videogame_id) {
		Query query = entityManager.createNativeQuery(SQLConstants.SQL_OBTAIN_SIMILAR_VIDEOGAMES);
		query.setParameter("tag_id", tag_id);
		query.setParameter("videogame_id", videogame_id);
		
		List<Map<String, Object>> similar_games_list = Utilities.processNativeQuery(query);
		
		return similar_games_list;
	}

	@Override
	public List<Map<String, Object>> searchVideogamesByName(String videogame_name) {
		// First we prepare the videogame name for the search
		String vg_title_for_sql = "%"+videogame_name.trim().toLowerCase()+"%";
		Query query = entityManager.createNativeQuery(SQLConstants.SQL_OBTAIN_VIDEOGAMES_BY_NAME);
		query.setParameter("videogame_title", vg_title_for_sql);
		
		List<Map<String, Object>> videogames_by_name_list = Utilities.processNativeQuery(query);
		
		return videogames_by_name_list;
	}

	@Override
	public int obtainTotalVideogames(String title) {
		String vg_title_for_sql = "%"+title.trim().toLowerCase()+"%";
		Query query = entityManager.createNativeQuery(SQLConstants.SQL_OBTAIN_TOTAL_VIDEOGAMES_BY_NAME);
		query.setParameter("videogame_title", vg_title_for_sql);
		return Integer.parseInt(query.getSingleResult().toString());
	}

	@Override
	public int obtainTotalVideogamesByDev(int dev_id) {
		Query query = entityManager.createNativeQuery(SQLConstants.SQL_OBTAIN_TOTAL_VIDEOGAMES_BY_DEV);
		query.setParameter("dev_id", dev_id);
		return Integer.parseInt(query.getSingleResult().toString());
	}

	@Override
	public int obtainTotalVideogamesByTag(int tag_id) {
		Query query = entityManager.createNativeQuery(SQLConstants.SQL_OBTAIN_TOTAL_VIDEOGAMES_BY_TAG);
		query.setParameter("tag_id", tag_id);
		return Integer.parseInt(query.getSingleResult().toString());
	}
	
}
