package com.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.Payment;
import com.model.PaymentManager;
import com.model.User;


@Controller
public class AddController {

	@RequestMapping(value="/addPayment", method=RequestMethod.POST)
	public String add(@RequestParam(value="amount") double amount,
			@RequestParam(value="payment_type") String paymentType,
			@RequestParam(value="category") String category,
			@RequestParam(value="date") String d,
			@RequestParam(value="description") String description,
			HttpSession session, Model model
			){
		
		//VALIDATION
		String error = "";
		if(!validateAmount(amount)){
			error += "Amount is invalid";
			model.addAttribute("error", error);
			return "redirect:add";
		}
		LocalDate date = validateDate(d);
		if(date==null){
			error += "Date is invalid";
			model.addAttribute("error", error);
			return "redirect:add";
		}
		//end of validations
		
		/*Payment payment = null;
		//get user from session
		*/
		/*
		//CREATE PAYMENT
		if(paymentType.equalsIgnoreCase("EXPENSE")){
			payment = new Expense(category,description, amount, date);
		}else if(paymentType.equalsIgnoreCase("INCOME")){
			payment = new Income(category, description, amount, date);
		}*/
		
		User user = (User)session.getAttribute("logedUser");
		//CREATE PAYMENT
		Payment payment = PaymentManager.createPayment(paymentType, category, description, amount, date);
		
		//ADD PAYMENT
		//this will add payment to db+all foreign keys and to user's budget in session
		if(payment.getType().equalsIgnoreCase("EXPENSE")&&payment.getAmount()>user.getBudget().getBalance()){
			//if user doesn't have enough money in the balance
			model.addAttribute("errorBudgetMessage", "You don't have enought money in your budget");
		}else{
			//if payment is income or expense but the balance is gte the expense amount
			//saves payment to a budget and makes all calculations
			PaymentManager.savePayment(user, payment);
		}

		return "redirect:add"; 
	}
	
	private boolean validateAmount(double amount){
		if(amount<0){
			return false;
		}
		return true;
	}

	private LocalDate validateDate(String d){
		LocalDate date = null;
		try{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			date = LocalDate.parse(d, formatter);
		}catch(DateTimeParseException e){
			//model.addAttribute("errorMessage", "date must be in dd-mm-yyyy format");
		}
		return date;
	}
}
