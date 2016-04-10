package com.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.model.Utils;

@Controller
public class UserController {
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	String logout(HttpSession session){
		session.invalidate();
		Utils.logger.info("User is logout");
		return "redirect:index";
	}
}
