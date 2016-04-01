package com.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserProfileController {
	
	@RequestMapping(value = "/changeUsername" , method = RequestMethod.GET)
	String changeUsername(HttpSession s) {

		s.removeAttribute("changePassword");
		s.removeAttribute("addBalance");
		s.setAttribute("changeUsername", true);
		return "UserProfile";
	}
	@RequestMapping(value = "/changePassword" , method = RequestMethod.GET)
	String changePassword(HttpSession s) {

		s.removeAttribute("changeUsername");
		s.removeAttribute("addBalance");
		s.setAttribute("changePassword", true);
		return "UserProfile";
	}
	@RequestMapping(value = "/addBalance" , method = RequestMethod.GET)
	String addBalance(HttpSession s) {

		s.removeAttribute("changePassword");
		s.removeAttribute("changeUsername");
		s.setAttribute("addBalance", true);
		return "UserProfile";
	}
	@RequestMapping(value = "/back" , method = RequestMethod.GET)
	String back(HttpSession s) {
		s.removeAttribute("changePassword");
		s.removeAttribute("changeUsername");
		s.removeAttribute("addBalance");
		return "home";
	}
}
