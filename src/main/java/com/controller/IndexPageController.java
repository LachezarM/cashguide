package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexPageController {
	
	
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	String startUp() {
		
		return "index";
	}
	
	@RequestMapping(value = "/home",method = RequestMethod.GET)
	String continueTo() {
		
		return "home";
	}
	
}
