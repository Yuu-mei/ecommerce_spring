package com.jmorillo.indieStore.services;

import java.util.List;

import com.jmorillo.indieStore.model.Image;

public interface ImageService {
	void uploadImage(Image image);
	void deleteImage(int image_id);
	void deleteImagesOfVideogame(int videogame_id);
	void updateImage(Image image);
	List<Image> getVideogameImages(int videogame_id);
}
