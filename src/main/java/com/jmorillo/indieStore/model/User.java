package com.jmorillo.indieStore.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;
import com.jmorillo.indieStore.validationsConstants.ValidationsConstants;


@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue
	private int user_id;
	@OneToOne
	private Cart cart;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Wishlist> wishlist = new ArrayList<Wishlist>();
	@Transient
	private MultipartFile user_image_file;
	@Lob
	@Column(name="user_image")
	private byte[] user_image;
	
	@Pattern(regexp = ValidationsConstants.REGEXP_EMAIL, message = "Email must follow typical structure test@domain.com")
	@NotEmpty
	@Column(unique=true)
	private String email;
	
	@Pattern(regexp = ValidationsConstants.REGEXP_USERNAME, message = "Username between 3-30 characters, using only letters _ - and spaces")
	@NotEmpty
	@Size(min=3, max=30)
	@Column(unique=true)
	private String username;
	
	@Pattern(regexp = ValidationsConstants.REGEXP_PWD, message = "Password between 8-20 characters, letters and numbers only")
	@Size(min=8, max=20)
	@NotEmpty
	private String password;
	
	@Pattern(regexp = ValidationsConstants.REGEXP_COUNTRY, message = "Must be a Country Code (2-3 chars; ie: ES/ESP)")
	@Size(min=2, max=3)
	@NotEmpty
	private String country;
	
	@Pattern(regexp = ValidationsConstants.REGEXP_PHONE, message = "Phone allows country prefix, spaces, parenthesis and 7-9 char long numbers by themselves")
	@NotEmpty
	private String phone;
	
	public User() {
		// Empty constructor
	}

	public User(String email, String username, String password, String country, String phone) {
		super();
		this.email = email;
		this.username = username;
		this.password = password;
		this.country = country;
		this.phone = phone;
	}

	public User(Cart cart, MultipartFile user_image_file, byte[] user_image, String email, String username,
			String password, String country, String phone) {
		this.cart = cart;
		this.user_image_file = user_image_file;
		this.user_image = user_image;
		this.email = email;
		this.username = username;
		this.password = password;
		this.country = country;
		this.phone = phone;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public List<Wishlist> getWishlist() {
		return wishlist;
	}

	public void setWishlist(List<Wishlist> wishlist) {
		this.wishlist = wishlist;
	}

	public MultipartFile getUser_image_file() {
		return user_image_file;
	}

	public void setUser_image_file(MultipartFile user_image_file) {
		this.user_image_file = user_image_file;
	}

	public byte[] getUser_image() {
		return user_image;
	}

	public void setUser_image(byte[] user_image) {
		this.user_image = user_image;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
