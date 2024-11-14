package com.jmorillo.indieStore.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jmorillo.indieStore.model.Tag;
import com.jmorillo.indieStore.services.TagService;

@Controller
@RequestMapping("admin/")
public class TagController {
	@Autowired
	TagService tag_service;
	
	@RequestMapping("tag-list")
	public String getAllTags(Model model) {
		List<Tag> tag_list = tag_service.obtainAllTags();
		model.addAttribute("tags", tag_list);
		return "admin/tags";
	}
	
	@RequestMapping("tag-edit-form")
	public String editTagForm(String tag_id, Model model) {
		Tag t = tag_service.obtainTagById(Integer.parseInt(tag_id));
		model.addAttribute("t", t);
		return "admin/edit_tag_form";
	}
	
	@RequestMapping("tag-update")
	public String updateTag(@ModelAttribute("t") @Valid Tag t, BindingResult br, Model model) {
		if(br.hasErrors()) {
			return "admin/edit_tag_form";
		}
		
		tag_service.editTag(t);
		return getAllTags(model);
	}
	
	@RequestMapping("tag-delete")
	public String deleteTag(String tag_id, Model model) {
		tag_service.deleteTag(Integer.parseInt(tag_id));
		return getAllTags(model);
	}
	
	@RequestMapping("tag-create-form")
	public String addTagForm(Model model) {
		Tag t = new Tag();
		model.addAttribute("t", t);
		return "admin/add_tag_form";
	}
	
	@RequestMapping("tag-add")
	public String addTag(@ModelAttribute("t") @Valid Tag t, BindingResult br, Model model) {
		if(br.hasErrors()) {
			return "admin/add_tag_form";
		}
		
		tag_service.createTag(t);
		return getAllTags(model);
	}
}
