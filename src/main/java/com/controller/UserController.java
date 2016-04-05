package com.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
	
	//moje da slojim kontrolerite za login i registar tuk
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	String logout(HttpSession session){
		session.invalidate();
		return "redirect:index";
	} 
}
