package com.jmorillo.indieStore.RESTServices;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.jmorillo.indieStore.model.Videogame;
import com.jmorillo.indieStore.services.VideogameService;
import com.jmorillo.indieStore.services.WishlistService;

@RestController
public class VideogameRESTService {
	@Autowired
	VideogameService vg_service;
	@Autowired
	WishlistService wishlistService;
	
	@RequestMapping("obtain-videogames-json")
	public String obtainVideogamesJSON(){
		List<Map<String, Object>> videogames = vg_service.obtainVideogamesList();
		return new Gson().toJson(videogames);
	}
	
	@RequestMapping("obtain-videogames-pagination")
	public List<Videogame> obtainVideogamesPagination(@RequestParam(required = false, defaultValue = "") String videogame_name, @RequestParam(name = "from", defaultValue = "0") Integer from, @RequestParam(name = "resultsPerPage", defaultValue = "16") Integer resultsPerPage) {
		List<Videogame> vg_list = vg_service.obtainAllVideogamesREST(videogame_name, from, resultsPerPage);
		return vg_list;
	}
	
	@RequestMapping("obtain-total-videogames")
	public int obtainTotalNumberVideogames(@RequestParam(required = false, defaultValue = "") String videogame_name) {
		return vg_service.obtainTotalVideogames(videogame_name);
	}
	
	@RequestMapping("obtain-latest-games")
	public String obtainLatestGames() {
		List<Map<String, Object>> latest_videogames = vg_service.obtainLatestVideogames();
		return new Gson().toJson(latest_videogames);
	}
	
	@RequestMapping("obtain-videogame-detail")
	public String obtainVideogameDetail(@RequestParam("id") Integer videogame_id) {
		return new Gson().toJson(vg_service.obtainvideogameDetailsById(videogame_id));
	}
	
	@RequestMapping("obtain-videogames-by-developer")
	public String obtainVideogamesByDeveloper(@RequestParam("dev_id") Integer dev_id) {
		return new Gson().toJson(vg_service.obtainVideogamesByDeveloper(dev_id));
	}
	
	@RequestMapping("obtain-videogames-by-developer-with-pagination")
	public String obtainVideogamesByDeveloper(@RequestParam("dev_id") Integer dev_id, @RequestParam(name = "from", defaultValue = "0") Integer from, @RequestParam(name = "resultsPerPage", defaultValue = "16") Integer resultsPerPage) {
		return new Gson().toJson(vg_service.obtainVideogamesByDeveloper(dev_id, from, resultsPerPage));
	}
	
	@RequestMapping("obtain-videogames-by-tag-with-pagination")
	public String obtainVideogamesByTag(@RequestParam("tag_id") Integer tag_id, @RequestParam(name = "from", defaultValue = "0") Integer from, @RequestParam(name = "resultsPerPage", defaultValue = "16") Integer resultsPerPage) {
		return new Gson().toJson(vg_service.obtainVideogamesByTag(tag_id, from, resultsPerPage));
	}
	
	@RequestMapping("obtain-total-videogames-dev")
	public int obtainTotalVideogamesByDeveloper(@RequestParam("dev_id") Integer dev_id) {
		return vg_service.obtainTotalVideogamesByDev(dev_id);
	}
	
	@RequestMapping("obtain-total-videogames-tag")
	public int obtainTotalVideogamesByTag(@RequestParam("tag_id") Integer tag_id) {
		return vg_service.obtainTotalVideogamesByTag(tag_id);
	}
	
	@RequestMapping("obtain-videogames-by-tag")
	public String obtainVideogamesByTag(@RequestParam("tag_id") Integer tag_id) {
		return new Gson().toJson(vg_service.obtainVideogamesByTag(tag_id));
	}
	
	@RequestMapping("obtain-similar-videogames")
	public String obtainSimilarVideogames(@RequestParam("tag_id") Integer tag_id, @RequestParam("videogame_id") Integer videogame_id) {
		return new Gson().toJson(vg_service.obtainSimilarVideogames(tag_id, videogame_id));
	}
	
	@RequestMapping("obtain-videogames-by-name")
	public List<Map<String, Object>> obtainVideogamesByName(@RequestParam("videogame_name") String videogame_name) {
		return vg_service.searchVideogamesByName(videogame_name);
	}
	
	@RequestMapping("is-videogame-wishlisted")
	public boolean isVideogameWishlisted(@RequestParam("videogame_id") Integer videogame_id, @RequestParam("username") String username) {
		return wishlistService.videogameIsAlreadyWishlisted(username, videogame_id);
	}
	
	@RequestMapping("remove-videogame-from-wishlist")
	public void removeVideogameFromWishlist(@RequestParam("videogame_id") Integer videogame_id, @RequestParam("username") String username) {
		wishlistService.removeVideogameFromWishlist(username, videogame_id);
	}
	
	@RequestMapping("add-videogame-to-wishlist")
	public void addVideogameToWishlist(@RequestParam("videogame_id") Integer videogame_id, @RequestParam("username") String username) {
		wishlistService.addVideogameToWishlist(username, videogame_id);
	}
}
