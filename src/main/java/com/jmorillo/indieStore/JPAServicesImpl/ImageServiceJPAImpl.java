package com.jmorillo.indieStore.JPAServicesImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jmorillo.indieStore.model.Image;
import com.jmorillo.indieStore.services.ImageService;

@Service
@Transactional
public class ImageServiceJPAImpl implements ImageService{
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void uploadImage(Image image) {
		entityManager.persist(image);
	}

	@Override
	public void deleteImage(int image_id) {
		Image img = entityManager.find(Image.class, image_id);
		entityManager.remove(img);
	}

	@Override
	public List<Image> getVideogameImages(int videogame_id) {
		// Assuming that the videogames have images, as it would have no sense if they didn't have them on the upload
		List<Image> img_list = entityManager.createQuery("select i from Image i where i.videogame.videogame_id = :videogame_id").setParameter("videogame_id", videogame_id).getResultList();
		return img_list;
	}

	@Override
	public void deleteImagesOfVideogame(int videogame_id) {
		List<Image> images = getVideogameImages(videogame_id);
		for (Image img : images) {
			entityManager.remove(img);
		}
	}

	@Override
	public void updateImage(Image image) {
		entityManager.merge(image);
	}

}
