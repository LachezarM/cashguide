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

	@RequestMapping(value = "/info", method = RequestMethod.GET)
	String showPayments(HttpServletResponse r, HttpSession s, Model m) {
		User u = (User) s.getAttribute("logedUser");
		if(u==null){
			return "redirect:index";
		}
		List<Payment> payments = IPaymentDAO.getInstance().getAllPayments(u.getId());
		List<Payment> currMonthPayments = getCurrMonthPayments(payments);
		HashMap<String, Double> currMonthIncomesMap = getAsMap(currMonthPayments, "Income");
		HashMap<String, Double> currMonthExpensesMap = getAsMap(currMonthPayments, "Expense");
		JsonObject currMonthIncomesJson = getPaymentsAsJson(currMonthIncomesMap);
		JsonObject currMonthExpensesJson = getPaymentsAsJson(currMonthExpensesMap);
		TreeMap<LocalDate, Double> lastYearMonthlyIncomes = getPaymentsBetweenDates(payments, LocalDate.now(),
				LocalDate.now().minusYears(1), "Income");
		TreeMap<LocalDate, Double> lastYearMonthlyExpenses = getPaymentsBetweenDates(payments, LocalDate.now(),
				LocalDate.now().minusYears(1), "Expense");
		JsonObject lastYearMonthlyExpensesJson = null;
		try {
			lastYearMonthlyExpensesJson = getAmountsAsJson(lastYearMonthlyExpenses);
		} catch (InvalidAttributesException e) {
			e.printStackTrace();
		}
		System.out.println(lastYearMonthlyExpensesJson.toString());
		JsonObject lastYearMonthlyIncomesJson = null;
		try {
			lastYearMonthlyIncomesJson = getAmountsAsJson(lastYearMonthlyIncomes);
		} catch (InvalidAttributesException e) {
			e.printStackTrace();
		}
		System.out.println(lastYearMonthlyIncomesJson.toString());
		m.addAttribute("currMonthIncomesJson", currMonthIncomesJson);
		m.addAttribute("currMonthExpensesJson",currMonthExpensesJson);
		m.addAttribute("lastYearMonthlyExpenses", lastYearMonthlyExpensesJson);
		m.addAttribute("lastYearMonthlyIncomes", lastYearMonthlyIncomesJson);
		Utils.logger.info("info link is clicked");
		return "info";
	}

	private HashMap<String, Double> getAsMap(List<Payment> currMonthPayments, String type) {
		HashMap<String, Double> result = new HashMap<String, Double>();
		for (Payment payment : currMonthPayments) {
			if (payment.getType().equalsIgnoreCase(type)) {
				if (result.containsKey(payment.getCategory())) {
					result.put(payment.getCategory(), result.get(payment.getCategory()) + payment.getAmount());
				} else {
					result.put(payment.getCategory(), payment.getAmount());
				}
			}
		}
		return result;
	}

	private JsonObject getAmountsAsJson(TreeMap<LocalDate, Double> lastYearMonthlyIncomes)
			throws InvalidAttributesException {
		String currMonth = null;
		String previousMonth = null;
		double amount = 0;
		JsonArray arr = new JsonArray();
		JsonObject obj = new JsonObject();
		for (Entry<LocalDate, Double> entry : lastYearMonthlyIncomes.entrySet()) {
			currMonth = getMonthByInt(entry.getKey().getMonthValue());
			if (previousMonth != null) {
				if (currMonth.equalsIgnoreCase(previousMonth)) {
						arr.remove(obj);
					amount += entry.getValue();
				} else {;
					amount = entry.getValue();
				}
				
			} else {
				amount += entry.getValue();
			}
			obj = new JsonObject();
			obj.addProperty(currMonth, amount);
			arr.add(obj);
			previousMonth = currMonth;
		}
		JsonObject result = new JsonObject();
		result.add("amounts", arr);
		return result;
	}

	private TreeMap<LocalDate, Double> getPaymentsBetweenDates(List<Payment> payments, LocalDate now,
			LocalDate minusYears, String type) {
		TreeMap<LocalDate, Double> result = new TreeMap<LocalDate, Double>();
		for (Payment payment : payments) {
			if (payment.getDate().isAfter(minusYears) && payment.getDate().isBefore(now)) {
				// the date is in the period we want
				if (payment.getType().equalsIgnoreCase(type)) {
					// payments is the type we want
					if (result.containsKey(payment.getDate())) {
						result.put(payment.getDate(), result.get(payment.getDate()) + payment.getAmount());
					} else {
						result.put(payment.getDate(), payment.getAmount());
					}
				}
			}
		}
		// the map has now (date -> amount) only of the type we want
		return result;
	}

	private void fillMapByMonths(Map<String, List<Payment>> result, List<Payment> payments)
			throws InvalidAttributesException {
		for (int i = 1; i <= 12; i++) {
			String month = getMonthByInt(i);
			result.put(month, new ArrayList<Payment>());
			for (Payment p : payments) {
				if (p.getDate().getMonthValue() == i)
					result.get(month).add(p);
			}
		}
	}

	private String getMonthByInt(int monthValue) throws InvalidAttributesException {
		switch (monthValue) {
		case 1:
			return "January";
		case 2:
			return "February";
		case 3:
			return "March";
		case 4:
			return "April";
		case 5:
			return "May";
		case 6:
			return "June";
		case 7:
			return "July";
		case 8:
			return "August";
		case 9:
			return "September";
		case 10:
			return "Octomber";
		case 11:
			return "November";
		case 12:
			return "December";
		default:
			throw new InvalidAttributesException("Invalid date number in getLastYearPayments");
		}
	}

	private JsonObject getPaymentsAsJson(HashMap<String, Double> currMonthPaymentsMap) {
		JsonObject obj = new JsonObject();
		JsonArray arrPayments = new JsonArray();
		for (Entry<String, Double> entry : currMonthPaymentsMap.entrySet()) {
			JsonObject tmp = new JsonObject();
			tmp.addProperty("amount", entry.getValue());
			tmp.addProperty("category", entry.getKey());
			arrPayments.add(tmp);

		}
		obj.add("payments", arrPayments);
		return obj;
	}

	private List<Payment> getCurrMonthPayments(List<Payment> payments) {
		List<Payment> currMonthPayments = new ArrayList<Payment>();
		currMonthPayments.addAll(payments);
		int currMonth = LocalDate.now().getMonth().getValue();
		for (Iterator<Payment> itt = currMonthPayments.iterator(); itt.hasNext();) {
			Payment p = itt.next();
			if (p.getDate().getMonth().getValue() != currMonth)
				itt.remove();
		}
		return currMonthPayments;
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
