package com.jmorillo.indieStore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.jmorillo.indieStore.model.dataTypes.ImageType;

@Entity
public class Image {
	@Id
	@GeneratedValue
	private int image_id;
	@ManyToOne
	@JoinColumn(name="videogame_id")
	private Videogame videogame;
	@Lob
	@Column(name="image")
	private byte[] imageData;
	// The games will have a header image for the featured games, a capsule image for each card, and then two images for the carousel in videogame details
	@Enumerated
	private ImageType imageType;
	
	public int getImage_id() {
		return image_id;
	}
	
	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}
	
	public Videogame getVideogame() {
		return videogame;
	}
	
	public void setVideogame(Videogame videogame) {
		this.videogame = videogame;
	}
	
	public byte[] getImageData() {
		return imageData;
	}
	
	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	
	public ImageType getImageType() {
		return imageType;
	}
	
	public void setImageType(ImageType imageType) {
		this.imageType = imageType;
	}
	
}
