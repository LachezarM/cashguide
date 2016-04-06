package com.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.model.Payment;
import com.model.User;
import com.model.db.DBBudgetDAO;
import com.model.db.IBudgetDAO;
import com.model.db.IPaymentDAO;

@Controller
public class HomePageController {
	
	@RequestMapping(value="/add" , method = RequestMethod.GET)
	String add(HttpServletResponse r, Model model, HttpSession session) {
		User user = (User)session.getAttribute("logedUser");
		Map<String, ArrayList<String>> result = DBBudgetDAO.getInstance().getAllCategories(user.getId());
		JsonObject object = new JsonObject();
		for(String type:result.keySet()){
			JsonArray categories = new JsonArray();
			for(String category:result.get(type)){
				categories.add(category);
			}
			object.add(type, categories);
		}
		
		model.addAttribute("categories", object);
		return 	"add";	
	}
	
	@RequestMapping(value="/history" , method = RequestMethod.GET)
	String showHistory(HttpSession s,
			Model m) {
		//System.out.println("priema se zaqvka");
		User u = (User) s.getAttribute("logedUser");
		List<Payment> payments = IPaymentDAO.getInstance().getAllPayments(u.getId());
		Map<String, ArrayList<String>> result = DBBudgetDAO.getInstance().getAllCategories(u.getId());
		System.out.println(result.toString());
		List<String> categories = new ArrayList<String>();
		categories.addAll(result.get("EXPENSE"));
		categories.addAll(result.get("INCOME"));
		m.addAttribute("currPayments", payments);
		m.addAttribute("currCategories",categories);
		return 	"history";	
	}	
	
	
	@RequestMapping(value="/info" , method = RequestMethod.GET)
	String showPayments(HttpServletResponse r,
			HttpSession s,
			Model m) {
		User u = (User) s.getAttribute("logedUser");
		List<Payment> payments = IPaymentDAO.getInstance().getAllPayments(u.getId());
		int currMonth = LocalDate.now().getMonth().getValue();
		for(Iterator<Payment> itt = payments.iterator();itt.hasNext();) {
			Payment p = itt.next();
			if(p.getDate().getMonth().getValue() != currMonth)
				itt.remove();
		}
		//payments has now only payments in currMonth
		JsonObject obj = new JsonObject();
		JsonArray arrIncomes = new JsonArray();
		JsonArray arrExpenses = new JsonArray();
		for(int i = 0;i< payments.size();i++ ) {
			JsonObject tmp =  new JsonObject();
			tmp.addProperty("amount", payments.get(i).getAmount());
			tmp.addProperty("category", payments.get(i).getCategory());
			if(payments.get(i).getType().equalsIgnoreCase("INCOME"))
				arrIncomes.add(tmp);
			else 
				arrExpenses.add(tmp);
			
		}
		obj.add("INCOMES", arrIncomes);
		obj.add("EXPENSES", arrExpenses);
		m.addAttribute("paymentsCurrMonth", obj);
		System.out.println(obj.toString());
		return 	"info";	
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
