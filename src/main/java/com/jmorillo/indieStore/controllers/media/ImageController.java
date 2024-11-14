package com.jmorillo.indieStore.controllers.media;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jmorillo.indieStore.model.Image;
import com.jmorillo.indieStore.model.Videogame;
import com.jmorillo.indieStore.services.UserService;
import com.jmorillo.indieStore.services.VideogameService;

@Controller
public class ImageController {
	@Autowired
	private VideogameService videogameService;
	@Autowired
	private UserService userService;
	
	private void operateImage(byte[] img_bytes, HttpServletResponse response) throws IOException {
		if(img_bytes == null) {
			System.err.println("No image found returning");
			return;
		}
		
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif, image/webp");
		response.getOutputStream().write(img_bytes);
		response.getOutputStream().close();
	}
	
	@RequestMapping("get_image")
	public void getImage(@RequestParam("id") Integer id, @RequestParam("type") String type , HttpServletResponse response) throws IOException {
		Videogame v = videogameService.obtainVideogameById(id);
		byte[] img_bytes = null;
		for (Image img : v.getVgImages()) {
			switch(img.getImageType()) {
				case CAPSULE:
					if(type.equals("capsule")) {
						img_bytes = img.getImageData();						
					}
					break;
				case HEADER:
					if(type.equals("header")) {
						img_bytes = img.getImageData();	
					}
					break;
				case GAMEPLAY1:
					if(type.equals("gameplay1")) {
						img_bytes = img.getImageData();	
					}
					break;
				case GAMEPLAY2:
					if(type.equals("gameplay2")) {
						img_bytes = img.getImageData();	
					}
					break;
				default:
					System.err.println("Invalid image type");
					break;
			}
			if(img_bytes != null) {
				break;
			}
		}
		operateImage(img_bytes, response);
	}
	
	@RequestMapping("get_user_image")
	public void getUserImage(@RequestParam("user_id") Integer user_id, HttpServletResponse response) throws IOException {
		byte[] img_bytes = userService.obtainUserById(user_id).getUser_image();
		operateImage(img_bytes, response);
	}
}
