package com.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model.User;

@Controller
public class IndexPageController {
	
	
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	String startUp() {
		
		return "index";
	}
	@RequestMapping(value="/login",method = RequestMethod.POST)
	String login(@RequestParam(value ="username") String username,@RequestParam(value ="password") String password) {
		
		if(valid(username,password)) {
			//check DB
			//if exists
				return "home";
			//else return "index";
		} 
			//invalid data
			return "index";
	}
	@RequestMapping(value="/register",method = RequestMethod.POST)
	String register(Model m,@RequestParam(value="username") String username,@RequestParam(value ="email") String email,
			@RequestParam(value ="password") String password,@RequestParam(value ="confirm-password") String confirmPassword){
		if(valid(username,email,password,confirmPassword,m))
		{
			User x = new User(username,email,password);
			//saveuser(x)
			return "home";
		}
		//fields must remain filled
		return "index";
	}
	private boolean valid(String username, String email, String password, String confirmPassword,Model m) {
		if(username.length() == 0 || email.length() == 0 || password.length() == 0 || confirmPassword.length() == 0) {
			m.addAttribute("Error input all fields",true);
			return false;
		}
		return true;
	}
	private boolean valid(String username, String password) {
		if(username.length() == 0 || password.length() == 0) {
			return false;
		}
		return true;
	}
	
}
