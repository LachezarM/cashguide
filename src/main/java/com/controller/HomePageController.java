package com.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.directory.InvalidAttributesException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JScrollBar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.model.Payment;
import com.model.User;
import com.model.db.DBBudgetDAO;
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
		List<Payment> currMonthPayments = getCurrMonthPayments(payments);
		JsonObject currMonthPaymentsJson = getPaymentsAsJson(currMonthPayments);
		Map<String, List<Payment>> lastYearMonthlyAmounts = getLastYearPayments(payments);
		JsonObject lastYearMonthlyExpensesJson = getAmountsAsJson(lastYearMonthlyAmounts,"Expense");
		JsonObject lastYearMonthlyIncomesJson = getAmountsAsJson(lastYearMonthlyAmounts, "Income");
		m.addAttribute("paymentsCurrMonth", currMonthPaymentsJson);
		m.addAttribute("lastYearMonthlyExpenses",lastYearMonthlyExpensesJson);
		m.addAttribute("lastYearMonthlyIncomes",lastYearMonthlyIncomesJson);
		return 	"info";	
	}
	
	private JsonObject getAmountsAsJson(Map<String, List<Payment>> lastYearMonthlyAmounts,String type) {
		JsonObject result = new JsonObject();
		for(Entry<String, List<Payment>> entry : lastYearMonthlyAmounts.entrySet()) {
			double amoutForThisMonth = 0;
			for(Payment p : entry.getValue()) {
				if(p.getType().equalsIgnoreCase(type))
					amoutForThisMonth += p.getAmount();
			} 
			JsonPrimitive pr = new JsonPrimitive(amoutForThisMonth);
			result.add(entry.getKey(), pr);
		}
		return result;
	}
	

	private Map<String, List<Payment>> getLastYearPayments(List<Payment> payments) {
		Map<String, List<Payment>> result = new HashMap<String, List<Payment>>();
		List<Payment> lastYearPayments = new ArrayList<Payment>();
		lastYearPayments.addAll(payments);
		LocalDate now = LocalDate.now();
		LocalDate yearLater = now.minusYears(1);
		System.out.println(now + " " + yearLater );
		for(Iterator<Payment>  itt = lastYearPayments.iterator();itt.hasNext();) {
			Payment p = itt.next();
			if(
					(now.getYear() == p.getDate().getYear() && now.getMonthValue() >= p.getDate().getMonthValue()) ||
					(yearLater.getYear() == p.getDate().getYear() && yearLater.getMonthValue() <= p.getDate().getMonthValue())
			  ) {
				String month = null;
				try {
					month = getMonthByInt(p.getDate().getMonthValue());
				} catch (InvalidAttributesException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(result.containsKey(month)) {
					result.get(month).add(p);
				} else {
					result.put(month, new ArrayList<Payment>());
					result.get(month).add(p);
				}
				
			} else {
				//invalid
				itt.remove();
			}
		}
		return result;
	}

	private String getMonthByInt(int monthValue) throws InvalidAttributesException {
		switch(monthValue){
		case 1: return "January";
		case 2: return "February";
		case 3: return "March";
		case 4: return "April";
		case 5: return "May";
		case 6: return "June";
		case 7: return "July";
		case 8: return "August";
		case 9: return "September";
		case 10: return "Octomber";
		case 11: return "November";
		case 12: return "December";
		default: throw new InvalidAttributesException("Invalid date number in getLastYearPayments");
		}
	}

	private JsonObject getPaymentsAsJson(List<Payment> payments) {
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
		return obj;
	}

	private List<Payment> getCurrMonthPayments(List<Payment> payments) {
		List<Payment> currMonthPayments = new ArrayList();
		currMonthPayments.addAll(payments);
		int currMonth = LocalDate.now().getMonth().getValue();
		for(Iterator<Payment> itt = currMonthPayments.iterator();itt.hasNext();) {
			Payment p = itt.next();
			if(p.getDate().getMonth().getValue() != currMonth)
				itt.remove();
		}
		return currMonthPayments;
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
