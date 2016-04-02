package com.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.User;
import com.model.db.IUserDAO;

@Controller
public class UserProfileController {
	
	@RequestMapping(value = "/changeUsername" , method = RequestMethod.GET)
	String changeUsername(HttpSession s) {

		s.removeAttribute("changePassword");
		s.removeAttribute("addBalance");
		s.setAttribute("changeUsername", true);
		return "UserProfile";
	}
	@RequestMapping(value = "/changePassword" , method = RequestMethod.GET)
	String changePassword(HttpSession s) {

		s.removeAttribute("changeUsername");
		s.removeAttribute("addBalance");
		s.setAttribute("changePassword", true);
		return "UserProfile";
	}
	@RequestMapping(value = "/addBalance" , method = RequestMethod.GET)
	String addBalance(HttpSession s) {

		s.removeAttribute("changePassword");
		s.removeAttribute("changeUsername");
		s.setAttribute("addBalance", true);
		return "UserProfile";
	}
	@RequestMapping(value = "/back" , method = RequestMethod.GET)
	String back(HttpSession s) {
		s.removeAttribute("changePassword");
		s.removeAttribute("changeUsername");
		s.removeAttribute("addBalance");
		return "home";
	}
	@RequestMapping(value = "/changeUsername" , method = RequestMethod.POST)
	String changeUsernamePost(@RequestParam(value ="username") String username,
			HttpSession s,
			Model m) {
		if(username.length() != 0 && !IUserDAO.getInstance().checkIfUserExests(username)) {
			User u = (User) s.getAttribute("logedUser");
			System.out.println(u.getId());
			IUserDAO.getInstance().changeUserProfile(u.getId(), username);
			System.out.println(u.getId());
			m.addAttribute("change", "Sucessful");
			return "UserProfile";
		
		}
		m.addAttribute("change", "UnSucessful");
		return "UserProfile";
	}
	
	@RequestMapping(value="/changePasswordUser", method = RequestMethod.GET)
	String changePasswordPost(@RequestParam(value="newPassword") String newPassword,
	HttpSession s,
	Model m) {
		if(newPassword.length() > 6 && !IUserDAO.getInstance().checkIfPasswordExists(newPassword)) {
		User u = (User) s.getAttribute("logedUser");
		IUserDAO.getInstance().changePassword(u.getId(), newPassword);
		m.addAttribute("change","successful");
		return "UserProfile";
	}
		m.addAttribute("change","unsuccessful");
		return "UserProfile";
}
}
