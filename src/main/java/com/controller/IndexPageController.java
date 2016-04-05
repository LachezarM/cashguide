package com.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.User;
import com.model.UserManager;
import com.model.db.IUserDAO;

@Controller
public class IndexPageController {
	
	
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	String startUp() {
		return "index";
	}
	
	@RequestMapping(value="/home",method=RequestMethod.GET)
	String home() {
		return "home";
	}
	//*********************THIS METHOD SHOULD BE REFACTORED******************
	@RequestMapping(value="/login",method = RequestMethod.POST)
	String login(@RequestParam(value ="username") String username,
			@RequestParam(value ="password") String password,
			HttpSession s, Model model) {
		
		if(valid(username,password)) {
			for(User u : IUserDAO.getInstance().getAllUsers()) {
				if(username.equals(u.getUsername()) && password.equals(u.getPassword())) {
					//User x = IUserDAO.getInstance().getUser(username);
					User user = UserManager.createUserAfterLogin(username, password);
					s.setAttribute("logedUser",user);
					System.out.println(user.getId());
					return "redirect:home";
				}
			}
			model.addAttribute("LoginErrorInfo", "User doesnt exists");
			//s.setAttribute("ErrorInfo", "User doesnt exists");
			return "index";
		} 
			model.addAttribute("LoginErrorInfo", "Fields empty");
			//s.setAttribute("ErrorInfo","Fields empty");
			return "index";
	}
	
	@RequestMapping(value="/register",method = RequestMethod.POST)
	String register(@RequestParam(value="username") String username,
			@RequestParam(value ="email") String email,
			@RequestParam(value ="password") String password,
			@RequestParam(value ="confirm-password") String confirmPassword,
			HttpSession s, Model model, HttpServletResponse response){
		String result = valid(username,email,password,confirmPassword);
		String view="index";
		if(result.equals("correct"))
		{
			//use UserManager.createUserAfterRegister();
			//maybe validation should be in UserManager
			User user = UserManager.createUserAfterRegister(username, password, email);
			s.setAttribute("logedUser",user);
			view="redirect:home";
		}else{
			model.addAttribute("RegisterErrorInfo", result);
			view="index";
		}
		return view;
	}
	
	private String valid(String username, String email, String password, String confirmPassword) {
		String result = "correct";
		if(username.length() == 0 || email.length() == 0 || password.length() == 0 || confirmPassword.length() == 0) {
			result = "Some fields are empty";
		}
		if(!password.equals(confirmPassword)) {
			result = "Passwords do not match";
		}
		if(password.length() < 6) {
			result = "Password must be atleast 6 characters";
		}
		if(!validMail(email)) {
			result = "Invalid email";
		}
		//--------------------!!!!!------------------------
		for(User u : IUserDAO.getInstance().getAllUsers()) {
			if(u.getUsername().equals(username)) {
				result = "Username or password already in use";
				break;
			}
		}
		//--------------------------------------------------
		return result;
	}
	
	private boolean validMail(String email) {
		 String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		 return email.matches(EMAIL_REGEX);
	}
	
	private boolean valid(String username, String password) {
		if(username.length() == 0 || password.length() == 0) {
			return false;
		}
		return true;
	}
}
