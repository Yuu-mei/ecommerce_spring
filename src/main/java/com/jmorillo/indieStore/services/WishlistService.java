package com.jmorillo.indieStore.services;

import java.util.List;

import com.jmorillo.indieStore.model.User;
import com.jmorillo.indieStore.model.Videogame;

public interface WishlistService {
	void addVideogameToWishlist(String username, int videogame_id);
	void removeVideogameFromWishlist(String username, int videogame_id);
	List<Videogame> getUserWishlist(User user);
	boolean videogameIsAlreadyWishlisted(String username, int videogame_id);
}
