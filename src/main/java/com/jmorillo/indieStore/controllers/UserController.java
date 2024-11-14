package com.jmorillo.indieStore.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jmorillo.indieStore.model.User;
import com.jmorillo.indieStore.services.UserService;

@Controller
@RequestMapping("admin/")
public class UserController {
	@Autowired
	UserService user_service;
	
	@RequestMapping("user-list")
	public String getAllUsers(Model model) {
		List<User> user_list = user_service.obtainAllUsers();
		model.addAttribute("users", user_list);
		return "admin/users";
	}
	
	@RequestMapping("user-edit-form")
	public String editUserForm(String user_id, Model model) {
		User u = user_service.obtainUserById(Integer.parseInt(user_id));
		model.addAttribute("u", u);
		return "admin/edit_user_form";
	}
	
	@RequestMapping("user-update")
	public String updateUser(@ModelAttribute("u") @Valid User u, BindingResult br, Model model) {
		if(br.hasErrors()) {
			return "admin/edit_user_form";
		}
		
		try {
			u.setUser_image(u.getUser_image_file().getBytes());
		} catch (IOException e) {
			System.out.println("Error handling the image");
			return "admin/edit_user_form";
		}
		user_service.editUser(u);
		return getAllUsers(model);
	}
	
	@RequestMapping("user-delete")
	public String deleteUser(String user_id, Model model) {
		user_service.deleteUser(Integer.parseInt(user_id));
		return getAllUsers(model);
	}
	
	@RequestMapping("user-create-form")
	public String addUserForm(Model model) {
		User u = new User();
		model.addAttribute("u", u);
		return "admin/add_user_form";
	}
	
	@RequestMapping("user-add")
	public String addUser(@ModelAttribute("u") @Valid User u, @RequestParam("user_image_file") MultipartFile userImageFile, BindingResult br, Model model) {
		if(br.hasErrors()) {
			return "admin/add_user_form";
		}
		
		try {
			u.setUser_image(userImageFile.getBytes());
		} catch (IOException e) {
			System.out.println("Error uploading the image");
			return "admin/add_user_form";
		}
		
		user_service.createUser(u);
		return getAllUsers(model);
	}
}
