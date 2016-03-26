package com.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomePageController {
	
	@RequestMapping(value="#payment" , method = RequestMethod.GET)
	String addPayment(HttpServletResponse r) {
		System.out.println("priema se zaqvka");
		return 	"index";	
	}
	
	@RequestMapping(value="#history" , method = RequestMethod.GET)
	String showHistory(HttpServletResponse r) {
		System.out.println("priema se zaqvka");
		return 	"index";	
	}
	@RequestMapping(value="#payments" , method = RequestMethod.GET)
	String showPayments(HttpServletResponse r) {
		System.out.println("priema se zaqvka");
		return 	"index";	
	}
	@RequestMapping(value="#shopping" , method = RequestMethod.GET)
	String shopingList(HttpServletResponse r) {
		System.out.println("priema se zaqvka");
		return 	"index";	
	}
	@RequestMapping(value="#simulator" , method = RequestMethod.GET)
	String simulator(HttpServletResponse r) {
		System.out.println("priema se zaqvka");
		return 	"index";	
	}
}
