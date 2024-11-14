package com.jmorillo.indieStore.RESTServices;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jmorillo.indieStore.RESTServices.responseData.LoginResponseData;
import com.jmorillo.indieStore.RESTServices.responseData.ResponseData;
import com.jmorillo.indieStore.model.User;
import com.jmorillo.indieStore.model.dataTypes.UserInfo;
import com.jmorillo.indieStore.services.UserService;

@RestController
public class UserRESTService {
	@Autowired
	UserService user_service;
	
	@RequestMapping(value = "register-user", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String registerUser(
			@RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("pwd") String pwd,
            @RequestParam("country") String country,
            @RequestParam("phone") String phone,
            @RequestParam("user_image_file") MultipartFile userImageFile
    ){
		User u = new User();
		u.setUsername(username);
		u.setEmail(email);
		u.setPassword(pwd);
		u.setCountry(country);
		u.setPhone(phone);
		try {
			u.setUser_image(userImageFile.getBytes());
		} catch (IOException e) {
			System.out.println("Error handling the image");
		}
		user_service.createUser(u);
		return "User added succesfully";
	}
	
	@RequestMapping("login-user")
	public LoginResponseData loginUser(String email, String pwd, HttpServletRequest req){
		User u = user_service.obtainUserByEmailPass(email, pwd);
		LoginResponseData lrd = null;
		if(u != null) {
			lrd = new LoginResponseData(ResponseData.OK, "Succesfully logged in", u.getUsername());
			req.getSession().setAttribute("user", u);
		}else {
			lrd = new LoginResponseData(ResponseData.BAD_REQUEST, "Incorrect email and/or password", null);
		}
		return lrd;
	}
	
	@RequestMapping("logout")
	public String logOut(HttpServletRequest req) {
		req.getSession().removeAttribute("user");
		return "Succesfully logged out";
	}
	
	@RequestMapping("obtain-user-info")
	public UserInfo obtainUserInfo(String username) {
		UserInfo uf = user_service.obtainUserInfoByUsername(username);
		return uf;
	}
}
