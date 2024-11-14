package com.jmorillo.indieStore.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jmorillo.indieStore.model.Developer;
import com.jmorillo.indieStore.services.DeveloperService;

@Controller
@RequestMapping("admin/")
public class DeveloperController {
	@Autowired
	DeveloperService dev_service;
	
	@RequestMapping("dev-list")
	public String getAllDevs(Model model) {
		List<Developer> dev_list = dev_service.obtainAllDevelopers();
		model.addAttribute("devs", dev_list);
		return "admin/devs";
	}
	
	@RequestMapping("dev-edit-form")
	public String editDevForm(String dev_id, Model model) {
		Developer d = dev_service.obtainDeveloperById(Integer.parseInt(dev_id));
		model.addAttribute("d", d);
		return "admin/edit_dev_form";
	}
	
	@RequestMapping("dev-update")
	public String updateDev(@ModelAttribute("d") @Valid Developer d, BindingResult br, Model model) {
		if(br.hasErrors()) {
			return "admin/edit_dev_form";
		}
		
		dev_service.editDeveloper(d);
		return getAllDevs(model);
	}
	
	@RequestMapping("dev-delete")
	public String deleteDev(String dev_id, Model model) {
		dev_service.deleteDeveloper(Integer.parseInt(dev_id));
		return getAllDevs(model);
	}
	
	@RequestMapping("dev-create-form")
	public String addDevForm(Model model) {
		Developer d = new Developer();
		model.addAttribute("d", d);
		return "admin/add_developer_form";
	}
	
	@RequestMapping("dev-add")
	public String addDev(@ModelAttribute("d") @Valid Developer d, BindingResult br, Model model) {
		if(br.hasErrors()) {
			return "admin/add_developer_form";
		}
		
		dev_service.createDeveloper(d);
		return getAllDevs(model);
	}
}
