package com.jmorillo.indieStore.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	@Autowired
	IndexController indexController;
	
	@RequestMapping("admin/")
	public String admin() {
		return "admin/admin_panel_index";
	}
	
	@RequestMapping("admin-login")
	public String adminLogin() {
		return "admin/admin-login";
	}
	
	@RequestMapping("admin-logout")
	public String adminLogout(HttpServletRequest req) {
		req.getSession().removeAttribute("token-admin");
		return indexController.index();
	}
}
