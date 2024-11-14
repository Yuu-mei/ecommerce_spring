package com.jmorillo.indieStore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jmorillo.indieStore.setUpService.SetUp;

@Controller
public class IndexController {
	@Autowired
	private SetUp setupService;
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping()
	public String index() {
		setupService.setUp();
		// Language
		String currentLanguage = messageSource.getMessage("language", null, LocaleContextHolder.getLocale());
		
		if (currentLanguage.equals("es")) {
			return "../static/public/store_index_es";
		}
		
		if (currentLanguage.equals("jp")) {
			return "../static/public/store_index_jp";
		}
		
		return "../static/public/store_index";
	}
}
