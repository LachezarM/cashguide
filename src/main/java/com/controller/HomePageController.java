package com.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomePageController {
	
	@RequestMapping(value="/add" , method = RequestMethod.GET)
	String addPayment(HttpServletResponse r) {
		System.out.println("priema se zaqvka");
		return 	"add";	
	}
	
	@RequestMapping(value="/history" , method = RequestMethod.GET)
	String showHistory(HttpServletResponse r) {
		System.out.println("priema se zaqvka");
		return 	"history";	
	}
		
	@RequestMapping(value="/payment" , method = RequestMethod.GET)
	String showPayments(HttpServletResponse r) {
		System.out.println("priema se zaqvka");
		return 	"payment";	
	}
	@RequestMapping(value="/shopping" , method = RequestMethod.GET)
	String shopingList(HttpServletResponse r) {
		System.out.println("priema se zaqvka");
		return 	"index";	
	}
	@RequestMapping(value="/simulator" , method = RequestMethod.GET)
	String simulator(HttpServletResponse r) {
		System.out.println("priema se zaqvka");
		return 	"simulator";	
	}
	@RequestMapping(value="/userProfile" , method=RequestMethod.GET)
	String userProfile(HttpSession s) {

		s.removeAttribute("changePassword");
		s.removeAttribute("changeUsername");
		s.removeAttribute("addBalance");
		return "UserProfile";
	}
}
