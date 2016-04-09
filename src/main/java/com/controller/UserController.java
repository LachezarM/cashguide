package com.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	String logout(HttpSession session){
		session.invalidate();
		return "redirect:index";
	}
	
	@RequestMapping(value="/settings", method = RequestMethod.GET)
	String settings(Model model){
		model.addAttribute("panel", "changePassword");
		return "settings";
	}
	
}
