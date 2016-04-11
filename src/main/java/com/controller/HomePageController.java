package com.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonObject;
import com.model.Payment;
import com.model.User;
import com.model.Utils;
import com.model.db.IPaymentDAO;

@Controller
public class HomePageController {
	

	final static String EXPENSE = "EXPENSE";
	final static String INCOME = "INCOME";
	final static String ALL = "ALL";
	
	static User currentUser = null;
	//final static Logger logger = Logger.getLogger(HomePageController.class.getName());

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
		JsonObject object = IPaymentDAO.getInstance().getCategoriesJSON(user.getId());
		model.addAttribute("panel", "payment");
		model.addAttribute("categories", object);
		session.setAttribute("categories", object);
		Utils.logger.info("add payments link is clicked");
		return "add";
	}

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	String showHistory(HttpSession session, Model model) {
		currentUser = (User) session.getAttribute("logedUser");
		if(currentUser == null){
			return "redirect:index";
		}
		List<Payment> payments = IPaymentDAO.getInstance().getAllPayments(currentUser.getId());
		session.setAttribute("AllPaymentsSession", payments);
		//we add all payments for user in session
		Map<String, ArrayList<String>> categoriesMap = IPaymentDAO.getInstance().getAllCategories(currentUser.getId());
		List<String> categoriesList = generateCategoriesByType(ALL, categoriesMap);
		//we add all categories map in the session
		session.setAttribute("AllCategoriesSession", categoriesMap);
		//
		double total = total(payments);
		model.addAttribute("currPayments", payments);
		model.addAttribute("currCategories", categoriesList);
		model.addAttribute("totalAmount",total);
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

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	String logout(HttpSession session){
		session.invalidate();
		Utils.logger.info("User is logout");
		return "redirect:index";
	}
	
	public static List<String> generateCategoriesByType(String choise, Map<String, ArrayList<String>> result) {
		List<String> categories = new ArrayList<String>();
		if(choise.equalsIgnoreCase(ALL)) {
			categories.addAll(result.get(EXPENSE));
			categories.addAll(result.get(INCOME));
		}else if(choise.equalsIgnoreCase(EXPENSE)) {
			categories.addAll(result.get(EXPENSE));
		}
		else if(choise.equalsIgnoreCase(INCOME)) {
			categories.addAll(result.get(INCOME));
		}
		return categories;
	}

	public static double total(List<Payment> payments){
		double amount = 0;
		for(Payment payment : payments) {
			if(payment.getType().equalsIgnoreCase(Payment.EXPENSE))
				amount -= payment.getAmount();
			else 
				amount += payment.getAmount();
		}
		return amount;
	}
}
