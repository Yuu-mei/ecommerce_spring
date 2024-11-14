package com.jmorillo.indieStore;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.jmorillo.indieStore.interceptors.AdminInterceptor;

@Component
public class Config implements WebMvcConfigurer{
	@Autowired
	AdminInterceptor adminInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(adminInterceptor);
		registry.addInterceptor(localeChangeInterceptor());
	}
	
	@Bean
	private SessionLocaleResolver localeResolver() {
		SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
		sessionLocaleResolver.setDefaultLocale(Locale.getDefault());
		return sessionLocaleResolver;
	}
	
	@Bean
	private LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setIgnoreInvalidLocale(true);
		localeChangeInterceptor.setParamName("language");
		return localeChangeInterceptor;
	}
}
