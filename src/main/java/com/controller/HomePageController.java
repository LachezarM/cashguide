package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@RequestMapping(value = "/index")
@Controller
public class HomePageController {
	
	
	@RequestMapping(method = RequestMethod.GET)
	String startUp() {
		
		return "index";
	}
}
