package com.jmorillo.indieStore.setUpService;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmorillo.indieStore.model.Developer;
import com.jmorillo.indieStore.model.Image;
import com.jmorillo.indieStore.model.Order;
import com.jmorillo.indieStore.model.OrderProduct;
import com.jmorillo.indieStore.model.SetUpEnt;
import com.jmorillo.indieStore.model.Tag;
import com.jmorillo.indieStore.model.User;
import com.jmorillo.indieStore.model.Videogame;
import com.jmorillo.indieStore.model.Wishlist;
import com.jmorillo.indieStore.model.dataTypes.ImageType;
import com.jmorillo.indieStore.model.orderStatus.OrderStatus;
import com.jmorillo.indieStore.services.CartService;

@Service
@Transactional
public class SetUpJPAServiceImpl implements SetUp{
	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	private CartService cartService;

	@Override
	public void setUp() {
		SetUpEnt setUpRegister = null;
		
		try {
			setUpRegister = (SetUpEnt) entityManager.createQuery("select s from SetUpEnt s").getSingleResult();
		} catch (Exception e) {
			System.out.println("Starting setup process...");
		}
		
		if(setUpRegister == null || !setUpRegister.isDone()) {
			// Tag Setup
			Tag t1 = new Tag("Horror", "Featuring tense atmospheres, unsettling themes, and often supernatural elements designed to frighten or disturb players. Games in this category may include jump scares, psychological terror, or survival elements set in eerie environments.");
			Tag t2 = new Tag("TPS", "Third-person shooter (TPS) is a subgenre of 3D shooter games in which the gameplay consists primarily of shooting. It is closely related to first-person shooters, but with the player character visible on-screen during play.");
			Tag t3 = new Tag("Unknown", "Undefined tag, must be changed ASAP. There is nothing to see here.");
			entityManager.persist(t1);
			entityManager.persist(t2);
			entityManager.persist(t3);
			
			// Dev Setup
			Developer d1 = new Developer("rose-engine", "german developers");
			Developer d2 = new Developer("Hopoo Games", "devs from all over the world");
			Developer d3 = new Developer("Unknown Developer", "Undefined developer, must be changed ASAP");
			entityManager.persist(d1);
			entityManager.persist(d2);
			entityManager.persist(d3);
			
			// Fake lists even though is only one dev
			List<Developer> devs1 = new ArrayList<Developer>();
			devs1.add(d1);
			devs1.add(d2);
			List<Developer> devs2 = new ArrayList<Developer>();
			devs2.add(d2);
			
			// Videogame Setup
			Videogame v1 = new Videogame("Signalis", 
					"A classic survival horror experience set in a dystopian future where humanity has uncovered a dark secret. Unravel a cosmic mystery, escape terrifying creatures, and scavenge an off-world government facility as Elster, a technician Replika searching for her lost dreams.", 
					new Date(), "https://www.youtube.com/embed/4KFiOp2o4L8?si=qc2vhrrANvCnsaTb", 19.99, t1, devs1);
			Videogame v2 = new Videogame("Risk of Rain 2", "Escape a chaotic alien planet by fighting through hordes of frenzied monsters – with your friends, or on your own. Combine loot in surprising ways and master each character until you become the havoc you feared upon your first crash landing.",
					new Date(), "https://www.youtube.com/embed/Qwgq_9EOCTg?si=KgOOg96rNIFpjNb6", 15.99, t2, devs2);
			entityManager.persist(v1);
			entityManager.persist(v2);
			
			// Silly loop to add some base games
			for (int i = 0; i < 50; i++) {
				Videogame placeholder_videogame = new Videogame("Signalis"+i, 
						"A classic survival horror experience set in a dystopian future where humanity has uncovered a dark secret. Unravel a cosmic mystery, escape terrifying creatures, and scavenge an off-world government facility as Elster, a technician Replika searching for her lost dreams.", 
						new Date(), "https://www.youtube.com/embed/4KFiOp2o4L8?si=qc2vhrrANvCnsaTb", 1.0+i, t1, devs1);
				
				entityManager.persist(placeholder_videogame);
				
				/* SIGNALIS */
				Image signalis_header_img = new Image();
				signalis_header_img.setImageType(ImageType.HEADER);
				signalis_header_img.setImageData(readBytesFromRoute("http://localhost:8080/default_images/signalis_header.jpg"));
				signalis_header_img.setVideogame(placeholder_videogame);
				entityManager.persist(signalis_header_img);
				placeholder_videogame.getVgImages().add(signalis_header_img);
				
				Image signalis_capsule_img = new Image();
				signalis_capsule_img.setImageType(ImageType.CAPSULE);
				signalis_capsule_img.setImageData(readBytesFromRoute("http://localhost:8080/default_images/signalis_capsule.jpg"));
				signalis_capsule_img.setVideogame(placeholder_videogame);
				entityManager.persist(signalis_capsule_img);
				placeholder_videogame.getVgImages().add(signalis_capsule_img);
				
				Image signalis_gameplay1_img = new Image();
				signalis_gameplay1_img.setImageType(ImageType.GAMEPLAY1);
				signalis_gameplay1_img.setImageData(readBytesFromRoute("http://localhost:8080/default_images/signalis_gameplay_1.jpg"));
				signalis_gameplay1_img.setVideogame(placeholder_videogame);
				entityManager.persist(signalis_gameplay1_img);
				placeholder_videogame.getVgImages().add(signalis_gameplay1_img);
				
				Image signalis_gameplay2_img = new Image();
				signalis_gameplay2_img.setImageType(ImageType.GAMEPLAY2);
				signalis_gameplay2_img.setImageData(readBytesFromRoute("http://localhost:8080/default_images/signalis_gameplay_2.jpg"));
				signalis_gameplay2_img.setVideogame(placeholder_videogame);
				entityManager.persist(signalis_gameplay2_img);
				placeholder_videogame.getVgImages().add(signalis_gameplay2_img);
				
				entityManager.merge(placeholder_videogame);
			}
			
			// Image Setup
			
			/* SIGNALIS */
			Image signalis_header_img = new Image();
			signalis_header_img.setImageType(ImageType.HEADER);
			signalis_header_img.setImageData(readBytesFromRoute("http://localhost:8080/default_images/signalis_header.jpg"));
			signalis_header_img.setVideogame(v1);
			entityManager.persist(signalis_header_img);
			v1.getVgImages().add(signalis_header_img);
			
			Image signalis_capsule_img = new Image();
			signalis_capsule_img.setImageType(ImageType.CAPSULE);
			signalis_capsule_img.setImageData(readBytesFromRoute("http://localhost:8080/default_images/signalis_capsule.jpg"));
			signalis_capsule_img.setVideogame(v1);
			entityManager.persist(signalis_capsule_img);
			v1.getVgImages().add(signalis_capsule_img);
			
			Image signalis_gameplay1_img = new Image();
			signalis_gameplay1_img.setImageType(ImageType.GAMEPLAY1);
			signalis_gameplay1_img.setImageData(readBytesFromRoute("http://localhost:8080/default_images/signalis_gameplay_1.jpg"));
			signalis_gameplay1_img.setVideogame(v1);
			entityManager.persist(signalis_gameplay1_img);
			v1.getVgImages().add(signalis_gameplay1_img);
			
			Image signalis_gameplay2_img = new Image();
			signalis_gameplay2_img.setImageType(ImageType.GAMEPLAY2);
			signalis_gameplay2_img.setImageData(readBytesFromRoute("http://localhost:8080/default_images/signalis_gameplay_2.jpg"));
			signalis_gameplay2_img.setVideogame(v1);
			entityManager.persist(signalis_gameplay2_img);
			v1.getVgImages().add(signalis_gameplay2_img);
			
			entityManager.merge(v1);
			
			/* RISK OF RAIN 2 */
			Image ror_header_img = new Image();
			ror_header_img.setImageType(ImageType.HEADER);
			ror_header_img.setImageData(readBytesFromRoute("http://localhost:8080/default_images/ror_header.jpg"));
			ror_header_img.setVideogame(v2);
			entityManager.persist(ror_header_img);
			v2.getVgImages().add(ror_header_img);
			
			Image ror_capsule_img = new Image();
			ror_capsule_img.setImageType(ImageType.CAPSULE);
			ror_capsule_img.setImageData(readBytesFromRoute("http://localhost:8080/default_images/ror_capsule.jpg"));
			ror_capsule_img.setVideogame(v2);
			entityManager.persist(ror_capsule_img);
			v2.getVgImages().add(ror_capsule_img);
			
			Image ror_gameplay1_img = new Image();
			ror_gameplay1_img.setImageType(ImageType.GAMEPLAY1);
			ror_gameplay1_img.setImageData(readBytesFromRoute("http://localhost:8080/default_images/ror_gameplay_1.jpg"));
			ror_gameplay1_img.setVideogame(v2);
			entityManager.persist(ror_gameplay1_img);
			v2.getVgImages().add(ror_gameplay1_img);
			
			Image ror_gameplay2_img = new Image();
			ror_gameplay2_img.setImageType(ImageType.GAMEPLAY2);
			ror_gameplay2_img.setImageData(readBytesFromRoute("http://localhost:8080/default_images/ror_gameplay_2.jpg"));
			ror_gameplay2_img.setVideogame(v2);
			entityManager.persist(ror_gameplay2_img);
			v2.getVgImages().add(ror_gameplay2_img);
			
			entityManager.merge(v2);
			
			// User Setup
			User u1 = new User("nothingburger@fake.com", "Nothing Burger", "12345678", "ES", "1234567");
			User u2 = new User("test-user@fake.com", "Test-User", "12345678", "GB", "1234567");
			User u3 = new User("test-user_cart@fake.com", "Test-User-Cart", "12345678", "ESP", "1234567");
			u1.setUser_image(readBytesFromRoute("http://localhost:8080/default_images/user_default.png"));
			u2.setUser_image(readBytesFromRoute("http://localhost:8080/default_images/user_default.png"));
			u3.setUser_image(readBytesFromRoute("http://localhost:8080/default_images/user_default.png"));
			entityManager.persist(u1);
			//These last two will be used for cart testing
			entityManager.persist(u2);
			entityManager.persist(u3);
			
			// Card Setup
			cartService.addProduct(v1.getVideogame_id(), u2.getUser_id(), 2);
			cartService.addProduct(v1.getVideogame_id(), u3.getUser_id(), 1);
			cartService.addProduct(v2.getVideogame_id(), u3.getUser_id(), 1);
			
			// Wishlist Setup
			Wishlist wishlist_u1 = new Wishlist();
			wishlist_u1.setUser(u2);
			wishlist_u1.setVideogame(v2);
			entityManager.persist(wishlist_u1);
			
			// Order Setup
			Order o1 = new Order();
			o1.setAddress("21885 Dunham Road");
			try {
				o1.setCardExpireDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-11-01"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			o1.setCardNumber("1234 1234 1234 5678");
			o1.setCardOwner("Mr Owner");
			o1.setCardType("visa");
			o1.setCcv("111");
			o1.setCountry("USA");
			o1.setFullName("John Doe");
			o1.setPhone("517-535-9234 x988");
			o1.setShippingDetails("none");
			o1.setShippingType("standard");
			o1.setState("Michigan");
			o1.setStatus(OrderStatus.COMPLETE.name());
			o1.setUser(u2);
			o1.setZip_code("48036");
			entityManager.persist(o1);
			
			OrderProduct op1 = new OrderProduct();
			op1.setOrder(o1);
			op1.setQuantity(1);
			op1.setVideogame(v1);
			entityManager.persist(op1);
			
			List<OrderProduct> firstOrderProducts = new ArrayList<OrderProduct>();
			firstOrderProducts.add(op1);
			o1.setOrderProducts(firstOrderProducts);
			entityManager.merge(o1);
			
			Order o2 = new Order();
			o2.setAddress("25701 CODE RD");
			try {
				o2.setCardExpireDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-12-01"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			o2.setCardNumber("5678 1234 8190 1234");
			o2.setCardOwner("Miss Owner");
			o2.setCardType("mastercard");
			o2.setCcv("232");
			o2.setCountry("USA");
			o2.setFullName("Jane Doe");
			o2.setPhone("842-929-3710");
			o2.setShippingDetails("Not before 6 pm");
			o2.setShippingType("express");
			o2.setState("Michigan");
			o2.setStatus(OrderStatus.FINISHED.name());
			o2.setUser(u3);
			o2.setZip_code("48033");
			entityManager.persist(o2);
			
			OrderProduct op2 = new OrderProduct();
			op2.setOrder(o2);
			op2.setQuantity(2);
			op2.setVideogame(v2);
			entityManager.persist(op2);
			
			List<OrderProduct> secondOrderProducts = new ArrayList<OrderProduct>();
			secondOrderProducts.add(op2);
			o2.setOrderProducts(secondOrderProducts);
			entityManager.merge(o2);
			
			
			// Finishing up the setup
			SetUpEnt setUp = new SetUpEnt();
			setUp.setDone(true);
			entityManager.persist(setUp);
		}
	}
	
	private byte[] readBytesFromRoute(String resource_route) {
		byte[] resource_data = null;
		try {
			URL url = new URL(resource_route);
			resource_data = IOUtils.toByteArray(url);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Can't read data from route");
		}
		return resource_data;
	}

}
