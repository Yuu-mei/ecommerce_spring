package com.jmorillo.indieStore.services;

import java.util.List;
import java.util.Map;

import com.jmorillo.indieStore.model.Videogame;

public interface VideogameService  {
	List<Videogame> obtainAllVideogames();
	List<Videogame> obtainAllVideogames(String videogame_name, int from, int resultsPerPage);
	List<Videogame> obtainAllVideogamesREST(String videogame_name, int from, int resultsPerPage);
	Videogame obtainVideogameById(int videogame_id);
	void createVideogame(Videogame v);
	void editVideogame(Videogame v);
	void deleteVideogame(int videogame_id);
	Map<String, Object> obtainvideogameDetailsById(int videogame_id);
	List<Map<String, Object>> obtainVideogamesList();
	List<Map<String, Object>> searchVideogamesByName(String videogame_name);
	List<Map<String, Object>> obtainLatestVideogames();
	int obtainTotalVideogamesByTag(int tag_id);
	Map<String, Object> obtainVideogamesByDeveloper(int dev_id);
	Map<String, Object> obtainVideogamesByDeveloper(int dev_id, int from, int resultsPerPage);
	int obtainTotalVideogamesByDev(int dev_id);
	Map<String, Object> obtainVideogamesByTag(int tag_id);
	Map<String, Object> obtainVideogamesByTag(int tag_id, int from, int resultsPerPage);
	List<Map<String, Object>> obtainSimilarVideogames(int tag_id, int videogame_id);
	int obtainTotalVideogames(String title);
}
