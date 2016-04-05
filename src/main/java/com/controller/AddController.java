package com.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.boot.autoconfigure.web.ServerProperties.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.model.Expense;
import com.model.Income;
import com.model.Payment;
import com.model.User;
import com.model.UserManager;
import com.model.db.DBBudgetDAO;
import com.model.db.DBPaymentDAO;


@Controller
public class AddController {

	@RequestMapping(value="/addPayment", method=RequestMethod.POST)
	public String add(Model model, 
			@RequestParam(value="amount") double amount,
			@RequestParam(value="payment_type") String paymentType,
			@RequestParam(value="category") String category,
			@RequestParam(value="date") String d,
			@RequestParam(value="description") String description,
			HttpSession session){
		LocalDate date;
		try{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			date = LocalDate.parse(d, formatter);
		}catch(DateTimeParseException e){
			System.out.println("Date cann't be parsed");
			return "redirect:add";
		}
		
		
		if(amount<0){
			System.out.println("must be positive");
			amount = -amount;
		}
		//exception 
		
		
		Payment payment = null;
		//get user from session
		User user = (User)session.getAttribute("logedUser");
		
		if(paymentType.equalsIgnoreCase("EXPENSE")){
			payment = new Expense(category,description, amount, date);
		}else if(paymentType.equalsIgnoreCase("INCOME")){
			payment = new Income(category, description, amount, date);
		}
		
		//this will add payment to db+all foreign keys and to user's budget in session
		if(payment.getType().equalsIgnoreCase("EXPENSE")){
				if(payment.getAmount()>user.getBudget().getBalance()){
					model.addAttribute("errorBudgetMessage", "You don't have enought money in your budget");
					System.out.println("error you can't do this");
				}
				else{
					UserManager.addPayment(user, payment);
				}
			}else{
			UserManager.addPayment(user, payment);
		}
		return "add"; 
	}

	@RequestMapping(value="/customCategory", method=RequestMethod.POST)
	public String addCustonCategory(
			@RequestParam(value="type") String paymentType,
			@RequestParam(value="customCategory") String customCategory,
			Model model,
			HttpSession session){
		User user = (User)session.getAttribute("logedUser");
		if(paymentType.equalsIgnoreCase("expense")){
			paymentType="EXPENSE";
		}else{
			paymentType="INCOME";
		}
		
		//save to db
		DBPaymentDAO.getInstance().addNewCategory(customCategory, paymentType, user);
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
		model.addAttribute("message", "category is added");

		
		
		return "add";
	}

	
	
}
