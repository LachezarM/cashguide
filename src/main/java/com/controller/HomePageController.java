package com.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.naming.directory.InvalidAttributesException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.model.Payment;
import com.model.User;
import com.model.Utils;
import com.model.db.DBBudgetDAO;
import com.model.db.DBPaymentDAO;
import com.model.db.IPaymentDAO;

@Controller
public class HomePageController {
	
	static User currentUser = null;
	final static Logger logger = Logger.getLogger(HomePageController.class.getName());

	@RequestMapping(value="/home" , method = RequestMethod.GET)
	String home(Model model, HttpSession session) {
		if(session.getAttribute("logedUser")!=null){
			return "home";
		}else{
			return "redirect:index";
		}
	}
	
	@RequestMapping(value="/add" , method = RequestMethod.GET)
	String add(Model model, HttpSession session) {
		User user = (User)session.getAttribute("logedUser");
		if(user==null){
			return "redirect:index";
		}
		JsonObject object = DBPaymentDAO.getInstance().getCategoriesJSON(user.getId());
		model.addAttribute("panel", "payment");
		model.addAttribute("categories", object);
		session.setAttribute("categories", object);
		Utils.logger.info("add payments link is clicked");
		return "add";
	}

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	String showHistory(HttpSession s, Model m) {
		currentUser = (User) s.getAttribute("logedUser");
		if(currentUser==null){
			return "redirect:index";
		}
		List<Payment> payments = IPaymentDAO.getInstance().getAllPayments(currentUser.getId());
		Map<String, ArrayList<String>> result = DBBudgetDAO.getInstance().getAllCategories(currentUser.getId());
		List<String> categories = new ArrayList<String>();
		categories.addAll(result.get("EXPENSE"));
		categories.addAll(result.get("INCOME"));
		m.addAttribute("currPayments", payments);
		m.addAttribute("currCategories", categories);
		Utils.logger.info("history payments link is clicked");
		return "history";
	}


	@RequestMapping(value="/simulator" , method = RequestMethod.GET)
	String simulator(HttpSession session) {
		if(session.getAttribute("logedUser")==null){
			return "redirect:index";
		}
		Utils.logger.info("simulator link is clicked");
		return 	"simulator";	
	}

	@RequestMapping(value = "/userProfile", method = RequestMethod.GET)
	String userProfile(HttpSession s) {

		s.removeAttribute("changePassword");
		s.removeAttribute("changeUsername");
		s.removeAttribute("addBalance");
		return "UserProfile";
	}
}
