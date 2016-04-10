package com.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.naming.directory.InvalidAttributesException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.model.Payment;
import com.model.User;
import com.model.db.IPaymentDAO;

@Controller
public class InfoController {
	
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	String showPayments( HttpSession s, Model m) {
		User u = (User) s.getAttribute("logedUser");
		List<Payment> payments = IPaymentDAO.getInstance().getAllPayments(u.getId());
		
		List<Payment> currMonthPayments = getCurrMonthPayments(payments,LocalDate.now().getMonthValue());
		
		HashMap<String, Double> currMonthIncomesMap = getAsMap(currMonthPayments, "Income");
		HashMap<String, Double> currMonthExpensesMap = getAsMap(currMonthPayments, "Expense");
		JsonObject currMonthIncomesJson = getPaymentsAsJson(currMonthIncomesMap);
		JsonObject currMonthExpensesJson = getPaymentsAsJson(currMonthExpensesMap);
		//for another charts
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
		m.addAttribute("currMonthIncomesJson", currMonthIncomesJson);
		m.addAttribute("currMonthExpensesJson",currMonthExpensesJson);
		m.addAttribute("lastYearMonthlyExpenses", lastYearMonthlyExpensesJson);
		m.addAttribute("lastYearMonthlyIncomes", lastYearMonthlyIncomesJson);
		return "info";
	}
	@RequestMapping(value="/compare",method = RequestMethod.POST)
	public @ResponseBody String compareFunction(@RequestBody  String dates,HttpSession s,Model m,HttpServletResponse r){
		String date1 = dates.substring(6,16);
		String date2 = dates.substring(23,dates.length());
		String result = compareByDate(date1,date2,s,m,r);
		return result;
	}
	private String compareByDate(String date1,String date2,HttpSession s,Model m, HttpServletResponse r2) {
		User u = (User) s.getAttribute("logedUser");
		List<Payment> payments = IPaymentDAO.getInstance().getAllPayments(u.getId());
		int month1 = getMonthValue(date1);
		int month2 = getMonthValue(date2);
		JsonObject obj = new JsonObject();
			List<Payment> paymentsFirstDate = new ArrayList<>();
			List<Payment> paymentsSecondDate = new ArrayList<>();
			paymentsFirstDate.addAll(payments);
			paymentsSecondDate.addAll(payments);
			paymentsFirstDate = getCurrMonthPayments(paymentsFirstDate,month1);
			paymentsSecondDate = getCurrMonthPayments(paymentsSecondDate, month2);
			HashMap<String, Double> firstDateIncomesMap = getAsMap(paymentsFirstDate, "Income");
			HashMap<String, Double> firstDateExpensesMap = getAsMap(paymentsFirstDate, "Expense");
			HashMap<String, Double> secondDateIncomesMap = getAsMap(paymentsSecondDate, "Income");
			HashMap<String, Double> secondDateExpensesMap = getAsMap(paymentsSecondDate, "Expense");
			
			//must have same categories in the maps
			
			addNullCategories(firstDateIncomesMap,secondDateIncomesMap);
			addNullCategories(secondDateIncomesMap,firstDateIncomesMap);
			addNullCategories(firstDateExpensesMap,secondDateExpensesMap);
			addNullCategories(secondDateExpensesMap,firstDateExpensesMap);
			
			obj.add("firstDateIncomesJson", getPaymentsAsJson(firstDateIncomesMap));
			obj.add("firstDateExpensesJson", getPaymentsAsJson(firstDateExpensesMap));
			obj.add("secondDateIncomesJson",getPaymentsAsJson(secondDateIncomesMap));
			obj.add("secondDateExpensesJson", getPaymentsAsJson(secondDateExpensesMap));
		
		return obj.toString();
	}
	

	private void addNullCategories(HashMap<String, Double> firstDateIncomesMap,
			HashMap<String, Double> secondDateIncomesMap) {
		for(Entry<String , Double> entry : firstDateIncomesMap.entrySet()) {
			if(secondDateIncomesMap.containsKey(entry.getKey())){
				
			}else {
				secondDateIncomesMap.put(entry.getKey(), 0.0);
			}
		}
		
	}
	private JsonObject getListAsJson(List<Payment> paymentsFirstDate) {
		JsonObject obj = new JsonObject();
		for(Payment payment : paymentsFirstDate) {
			
		}
		return obj;
	}

	private int getMonthValue(String date) {
		LocalDate dateJava;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		dateJava = LocalDate.parse(date, formatter);
		return dateJava.getMonthValue();
	}

	private HashMap<String, Double> getAsMap(List<Payment> currMonthPayments, String type) {
		HashMap<String, Double> result = new HashMap();
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
		TreeMap<LocalDate, Double> result = new TreeMap();
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

	private List<Payment> getCurrMonthPayments(List<Payment> payments,int monthValue) {
		List<Payment> currMonthPayments = new ArrayList<Payment>();
		currMonthPayments.addAll(payments);
		int currMonth = monthValue;
		for (Iterator<Payment> itt = currMonthPayments.iterator(); itt.hasNext();) {
			Payment p = itt.next();
			if (p.getDate().getMonth().getValue() != currMonth)
				itt.remove();
		}
		return currMonthPayments;
	}



}
