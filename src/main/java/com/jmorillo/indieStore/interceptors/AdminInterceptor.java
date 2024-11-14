package com.jmorillo.indieStore.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.jmorillo.indieStore.SQLConstants.SQLConstants;

@Component
public class AdminInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(request.getParameter("admin-pass") != null) {
			System.out.println(request.getParameter("admin-pass"));
			if(request.getParameter("admin-pass").equals(SQLConstants.admin_pass)) {
				request.getSession().setAttribute("admin-token", "ok");
			}			
		}
		
		if(request.getRequestURI().contains("/admin/")) {
			System.out.println("Contains /admin/ "+request.getRequestURI());
			if(request.getSession().getAttribute("admin-token") == null || !request.getSession().getAttribute("admin-token").equals("ok")) {
				response.sendRedirect("../admin-login");
				return false;
			}
		}
		
		return true;
	}
}
