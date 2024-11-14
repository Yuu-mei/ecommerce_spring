package com.jmorillo.indieStore.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.jmorillo.indieStore.validationsConstants.ValidationsConstants;

@Entity
public class Videogame {
	@Id
	@GeneratedValue
	private int videogame_id;
	
	@Size(min = 2, max = 350)
	@NotEmpty
	@Pattern(regexp = ValidationsConstants.REGEXP_VIDEOGAME_TITLE, message = "2-30 char long, allows every character")
	private String title;
	
	@Size(min = 50, max = 350)
	@NotEmpty(message="Must be between 50-500 characters")
	@Column(length = 350)
	private String description;
	
	@NotNull(message="You must input a Date")
	private Date release_date;
	
	@Transient
	private MultipartFile header_image;
	@Transient
	private String header_img_url;
	@Column(length = 650)
	private String video_url;
	
	@NotNull(message="Price must be between 0 and 999")
	@Min(value = 0)
	@Max(value = 999)
	private Double price;
	
	@Transient
	private int tag_id;
	
	@ManyToOne(optional = false)
	private Tag tag;
	
	@JoinTable(
			name="games_and_developers",
			joinColumns = @JoinColumn(name="videogame_id", nullable=false),
			inverseJoinColumns = @JoinColumn(name="dev_id", nullable=false)
	)
	@ManyToMany(cascade=CascadeType.ALL)
	private List<Developer> developers = new ArrayList<Developer>();
	
	@Transient
	private List<Integer> developers_ids = new ArrayList<Integer>();
	
	@OneToMany(mappedBy = "videogame", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Wishlist> wishlistedBy = new ArrayList<Wishlist>();
	
	@OneToMany(mappedBy = "videogame", cascade = CascadeType.ALL)
	private List<Image> vgImages = new ArrayList<Image>();
	
	@Lob
	@Column(name="header_image")
	private byte[] header_img;
	
	public Videogame() {
		// Empty constructor
	}
	
	public Videogame(String title, String description, Date release_date, 
			String video_url, Double price, Tag tag) {
		this.title = title;
		this.description = description;
		this.release_date = release_date;
		this.video_url = video_url;
		this.price = price;
		this.tag = tag;
	}
	
	public Videogame(String title, String description, Date release_date, 
			String video_url, Double price, Tag tag, List<Developer> developers) {
		this.title = title;
		this.description = description;
		this.release_date = release_date;
		this.video_url = video_url;
		this.price = price;
		this.tag = tag;
		this.developers = developers;
	}
	
	public Videogame(String title, String description, Date release_date, 
			String video_url, Double price, Tag tag, List<Developer> developers, List<Image> vgImages) {
		this.title = title;
		this.description = description;
		this.release_date = release_date;
		this.video_url = video_url;
		this.price = price;
		this.tag = tag;
		this.developers = developers;
		this.vgImages = vgImages;
	}

	public int getVideogame_id() {
		return videogame_id;
	}

	public void setVideogame_id(int videogame_id) {
		this.videogame_id = videogame_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getRelease_date() {
		return release_date;
	}

	public void setRelease_date(Date release_date) {
		this.release_date = release_date;
	}

	public MultipartFile getHeader_image() {
		return header_image;
	}

	public void setHeader_image(MultipartFile header_image) {
		this.header_image = header_image;
	}

	public String getHeader_img_url() {
		return header_img_url;
	}

	public void setHeader_img_url(String header_img_url) {
		this.header_img_url = header_img_url;
	}

	public String getVideo_url() {
		return video_url;
	}

	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getTag_id() {
		return tag_id;
	}

	public void setTag_id(int tag_id) {
		this.tag_id = tag_id;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
	
	public List<Developer> getDevelopers() {
		return developers;
	}

	public void setDevelopers(List<Developer> developers) {
		this.developers = developers;
	}
	
	public void removeDeveloper(Developer developer) {
		this.developers.remove(developer);
		developer.getVideogames().remove(this);
	}

	public List<Integer> getDevelopers_ids() {
		return developers_ids;
	}

	public void setDevelopers_ids(List<Integer> developers_ids) {
		this.developers_ids = developers_ids;
	}

	public List<Wishlist> getWishlistedBy() {
		return wishlistedBy;
	}

	public void setWishlistedBy(List<Wishlist> wishlistedBy) {
		this.wishlistedBy = wishlistedBy;
	}
	
	public List<Image> getVgImages() {
		return vgImages;
	}

	public void setVgImages(List<Image> vgImages) {
		this.vgImages = vgImages;
	}

	public byte[] getHeader_img() {
		return header_img;
	}

	public void setHeader_img(byte[] header_img) {
		this.header_img = header_img;
	}

	

}
