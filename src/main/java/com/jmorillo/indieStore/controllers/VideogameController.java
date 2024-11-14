package com.jmorillo.indieStore.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jmorillo.indieStore.model.Image;
import com.jmorillo.indieStore.model.Videogame;
import com.jmorillo.indieStore.model.dataTypes.ImageType;
import com.jmorillo.indieStore.services.DeveloperService;
import com.jmorillo.indieStore.services.TagService;
import com.jmorillo.indieStore.services.VideogameService;

@Controller
@RequestMapping("admin/")
public class VideogameController {
	@Autowired
	VideogameService vg_service;
	@Autowired
	TagService tag_service;
	@Autowired
	DeveloperService dev_service;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// Convert the Date into a format Spring understands
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    dateFormat.setLenient(false);
	    binder.registerCustomEditor(Date.class, "release_date", new CustomDateEditor(dateFormat, true));
	}
	
	@RequestMapping("videogames-list")
	public String getVideogames(@RequestParam(name = "title", defaultValue = "") String title, @RequestParam(name = "from", defaultValue = "0") Integer from, Model model) {
		int resultsPerPage = 10;
		List<Videogame> vg_list = vg_service.obtainAllVideogames(title, from, resultsPerPage);
		int totalVideogames = vg_service.obtainTotalVideogames(title);
		model.addAttribute("videogames", vg_list);
		model.addAttribute("title", title);
		model.addAttribute("next", from+resultsPerPage);
		model.addAttribute("prev", from-resultsPerPage);
		model.addAttribute("total_videogames", totalVideogames);
		return "admin/videogames";
	}
	
	@RequestMapping("videogames-delete")
	public String deleteVideogame(String videogame_id, Model model) {
		vg_service.deleteVideogame(Integer.parseInt(videogame_id));
		return getVideogames("", 0, model);
	}
	
	@RequestMapping("videogames-create-form")
	public String addVideogameForm(Model model) {
		Videogame v = new Videogame();
		model.addAttribute("v", v);
		model.addAttribute("tags", tag_service.obtainAllTags());
		model.addAttribute("devs", dev_service.obtainAllDevelopers());
		return "admin/add_videogame_form";
	}
	
	@RequestMapping("videogames-add")
	public String addVideogame(@ModelAttribute("v") @Valid Videogame v,  
			@RequestParam("headerImage") MultipartFile headerImage,
			@RequestParam("capsuleImage") MultipartFile capsuleImage,
            @RequestParam("gameplayImage1") MultipartFile gameplayImage1,
            @RequestParam("gameplayImage2") MultipartFile gameplayImage2, 
            BindingResult br, Model model, HttpServletRequest request) {
		
		if(br.hasErrors()) {
			model.addAttribute("tags", tag_service.obtainAllTags());
			model.addAttribute("devs", dev_service.obtainAllDevelopers());
			return "admin/add_videogame_form";
		}
		
		// Image handling
		try {
			// Header
			Image header_img = new Image();
			header_img.setImageType(ImageType.HEADER);
			header_img.setImageData(headerImage.getBytes());
			v.getVgImages().add(header_img);
			// Capsule
			Image capsule_img = new Image();
			capsule_img.setImageType(ImageType.CAPSULE);
			capsule_img.setImageData(capsuleImage.getBytes());
			v.getVgImages().add(capsule_img);
			// Gameplay
			Image gameplay_img1 = new Image();
			gameplay_img1.setImageType(ImageType.GAMEPLAY1);
			gameplay_img1.setImageData(gameplayImage1.getBytes());
			v.getVgImages().add(gameplay_img1);
			
			Image gameplay_img2 = new Image();
			gameplay_img2.setImageType(ImageType.GAMEPLAY2);
			gameplay_img2.setImageData(gameplayImage2.getBytes());
			v.getVgImages().add(gameplay_img2);
		} catch (IOException e) {
			System.out.println("Error uploading the image");
			model.addAttribute("tags", tag_service.obtainAllTags());
			model.addAttribute("devs", dev_service.obtainAllDevelopers());
			return "admin/add_videogame_form";
		}
		
		vg_service.createVideogame(v);
		return getVideogames("", 0, model);
	}
	
	@RequestMapping("videogames-edit-form")
	public String editVideogameForm(String videogame_id, Model model) {
		Videogame v = vg_service.obtainVideogameById(Integer.parseInt(videogame_id));
		model.addAttribute("v", v);
		model.addAttribute("tags", tag_service.obtainAllTags());
		model.addAttribute("devs", dev_service.obtainAllDevelopers());
		return "admin/edit_videogame_form";
	}
	
	@RequestMapping("videogames-update")
	public String updateVideogame(@ModelAttribute("v") @Valid Videogame v,  
			@RequestParam("headerImage") MultipartFile headerImage,
			@RequestParam("capsuleImage") MultipartFile capsuleImage,
            @RequestParam("gameplayImage1") MultipartFile gameplayImage1,
            @RequestParam("gameplayImage2") MultipartFile gameplayImage2, 
            BindingResult br, Model model, HttpServletRequest request) {
		
		if(br.hasErrors()) {
			model.addAttribute("tags", tag_service.obtainAllTags());
			model.addAttribute("devs", dev_service.obtainAllDevelopers());
			return "admin/edit_videogame_form";
		}
		
		// Image handling
		try {
			// Header
			Image header_img = new Image();
			header_img.setImageType(ImageType.HEADER);
			header_img.setImageData(headerImage.getBytes());
			v.getVgImages().add(header_img);
			// Capsule
			Image capsule_img = new Image();
			capsule_img.setImageType(ImageType.CAPSULE);
			capsule_img.setImageData(capsuleImage.getBytes());
			v.getVgImages().add(capsule_img);
			// Gameplay
			Image gameplay_img1 = new Image();
			gameplay_img1.setImageType(ImageType.GAMEPLAY1);
			gameplay_img1.setImageData(gameplayImage1.getBytes());
			v.getVgImages().add(gameplay_img1);
			
			Image gameplay_img2 = new Image();
			gameplay_img2.setImageType(ImageType.GAMEPLAY2);
			gameplay_img2.setImageData(gameplayImage2.getBytes());
			v.getVgImages().add(gameplay_img2);
		} catch (IOException e) {
			System.out.println("Error uploading the image");
			model.addAttribute("tags", tag_service.obtainAllTags());
			model.addAttribute("devs", dev_service.obtainAllDevelopers());
			return "admin/add_videogame_form";
		}
		
		vg_service.editVideogame(v);
		
		
		return getVideogames("", 0, model);
	}
}
