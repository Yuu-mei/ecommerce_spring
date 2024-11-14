package com.jmorillo.indieStore.JPAServicesImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jmorillo.indieStore.model.User;
import com.jmorillo.indieStore.model.Videogame;
import com.jmorillo.indieStore.model.Wishlist;
import com.jmorillo.indieStore.services.WishlistService;

@Service
@Transactional
public class WishlistServiceJPAImpl implements WishlistService{
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void addVideogameToWishlist(String username, int videogame_id) {
		Wishlist w = new Wishlist();
		
		Query userQuery = entityManager.createQuery("select u from User u where u.username = :username");
		userQuery.setParameter("username", username);
		Query videogameQuery = entityManager.createQuery("select v from Videogame v where v.videogame_id = :videogame_id");
		videogameQuery.setParameter("videogame_id", videogame_id);
		
		try {
			User u = (User) userQuery.getSingleResult();
			Videogame v = (Videogame) videogameQuery.getSingleResult();
			
			w.setUser(u);
			w.setVideogame(v);
			entityManager.persist(w);
		}catch(NoResultException e) {
			System.err.println("User/Videogame not found");
			return;
		}
	}

	@Override
	public void removeVideogameFromWishlist(String username, int videogame_id) {
		Query userQuery = entityManager.createQuery("select u from User u where u.username = :username");
		userQuery.setParameter("username", username);
		Query videogameQuery = entityManager.createQuery("select v from Videogame v where v.videogame_id = :videogame_id");
		videogameQuery.setParameter("videogame_id", videogame_id);
		
		try {
			User u = (User) userQuery.getSingleResult();
			Videogame v = (Videogame) videogameQuery.getSingleResult();
			Query query = entityManager.createQuery("delete from Wishlist w where w.user = :user and w.videogame = :videogame");
			query.setParameter("user", u);
			query.setParameter("videogame", v);
			query.executeUpdate();
		}catch(NoResultException e) {
			System.err.println("User/Videogame not found");
			return;
		}
	}

	@Override
	public List<Videogame> getUserWishlist(User user) {
		Query query = entityManager.createQuery("select w.videogame from Wishlist w where w.user = :user");
		query.setParameter("user", user);
		List<Videogame> videogame_wishlist = query.getResultList();
		return videogame_wishlist;
	}

	@Override
	public boolean videogameIsAlreadyWishlisted(String username, int videogame_id) {
		Query userQuery = entityManager.createQuery("select u from User u where u.username = :username");
		userQuery.setParameter("username", username);
		
		try {
			User u = (User) userQuery.getSingleResult();
			List<Videogame> videogame_wishlist = getUserWishlist(u);
			for (Videogame videogame : videogame_wishlist) {
				if(videogame.getVideogame_id() == videogame_id) {
					return true;
				}
			}
		}catch(NoResultException e) {
			System.err.println("User not found");
			// Returning true so that the following code does not get executed and it doesn't insert into the DB
			return true;
		}
		
		// If the game is not wishlisted we return false
		return false;
	}

}
