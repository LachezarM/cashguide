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
import com.model.Utils;
import com.model.db.IUserDAO;

@Controller
public class IndexPageController {
	
	private static final String FORM= "form";
	private static final String LOGIN_FORM="login-form";
	private static final String REGISTER_FORM="register-form";
	
	private static final String LOGIN_ERROR="LoginErrorInfo";
	private static final String REGISTER_ERROR = "RegisterErrorInfo";
	
	private static final String WRONG_USERNAME = "Wrong username/password";
	private static final String INCORECT_LOGIN="Incorrect input";
	
	
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	String startUp(Model model) {
		model.addAttribute(FORM, LOGIN_FORM);
		System.out.println(model.asMap().get(FORM));
		return "index";
	}
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	String login(@RequestParam(value ="username") String username,
			@RequestParam(value ="password") String password,
			HttpSession session, Model model) {
		//normalization
		username = username.trim();
		password = password.trim();
		
		if(Utils.isValidUsername(username)&&Utils.isValidPassword(password)) {
			String hashed = UserManager.hashPassword(password);
			if(IUserDAO.getInstance().checkForCorrectUsernameAndPassword(username, hashed)){
				User user = UserManager.createUserAfterLogin(username, hashed);
				session.setAttribute("logedUser",user);
				System.out.println(user.getId());
				Utils.logger.info("user is logged");
				return "redirect:home";
			}
			model.addAttribute(LOGIN_ERROR, WRONG_USERNAME);
			model.addAttribute(FORM, LOGIN_FORM);
			return "index";
		} 
			model.addAttribute(LOGIN_ERROR, INCORECT_LOGIN);
			model.addAttribute(FORM, LOGIN_FORM);
			return "index";
	}
	
	@RequestMapping(value="/register",method = RequestMethod.GET)
	String registerGET(Model model){
		model.addAttribute(FORM, REGISTER_FORM);
		return "index";
	}
	
	@RequestMapping(value="/login",method = RequestMethod.GET)
	String loginGET(Model model){
		model.addAttribute(FORM, LOGIN_FORM);
		return "index";
	}
	
	
	@RequestMapping(value="/register",method = RequestMethod.POST)
	String register(@RequestParam(value="username") String username,
			@RequestParam(value ="email") String email,
			@RequestParam(value ="password") String password,
			@RequestParam(value ="confirm-password") String confirmPassword,
			HttpSession session, Model model, HttpServletResponse response){
		String result = valid(username,email,password,confirmPassword);
		if(result.equals("correct")){
			String hashed = UserManager.hashPassword(password);
			User user = UserManager.createUserAfterRegister(username, hashed, email);
			session.setAttribute("logedUser",user);
			Utils.logger.info("user is register and logged");
			return "redirect:home";
		}
		model.addAttribute(REGISTER_ERROR, result);
		model.addAttribute(FORM, REGISTER_FORM);
		Utils.logger.info("incorect registration");
		return "index";
	}
	
	private String valid(String username, String email, String password, String confirmPassword) {
		String result = "correct";
		if(username.length() == 0 || email.length() == 0 || password.length() == 0 || confirmPassword.length() == 0) {
			return "Empty fields";
		}
		if(!password.equals(confirmPassword)) {
			return "Passwords do not match";
		}
		if(!Utils.isValidPassword(password)) {
			return "Password must be between 6 and 16 characters and can contain letters, number and_ @ # $ %";
		}
		if(!Utils.isValidEmail(email)) {
			return "Invalid email";
		}
		if(IUserDAO.getInstance().checkIfUserExists(username, email)) {
			return "Username/email already in use";
		}
		return result;
	}
}
