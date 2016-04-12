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

import com.google.gson.JsonObject;
import com.model.Expense;
import com.model.Income;
import com.model.Payment;
import com.model.User;
import com.model.UserManager;
import com.model.Utils;
import com.model.db.IPaymentDAO;


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
		
		String errorMessage = null;
		String successMessage=null;
		
		LocalDate date;
		try{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			date = LocalDate.parse(d, formatter);
		}catch(DateTimeParseException e){
			System.out.println("Date cann't be parsed");
			Utils.logger.info("Incorect date in add payments");
			return "redirect:add";
		}
		if(amount<0){
			System.out.println("must be positive");
			Utils.logger.info("Negative amount in add payments");
			errorMessage = "Amount must be positive";
		}else{
			Payment payment = null;
			//get user from session
			User user = (User)session.getAttribute("logedUser");
			
			if(paymentType.equalsIgnoreCase(Payment.EXPENSE)){
				payment = new Expense(category,description, amount, date);
			}else if(paymentType.equalsIgnoreCase(Payment.INCOME)){
				payment = new Income(category, description, amount, date);
			}
			
			//this will add payment to db+all foreign keys and to user's budget in session
			if(payment.getType().equalsIgnoreCase(Payment.EXPENSE) 
					&& payment.getAmount()>(user.getBudget().getIncome()-user.getBudget().getExpense())){
						errorMessage="You don't have enought money in your budget";
						System.out.println("error you can't do this");
						Utils.logger.info("Payment wasn't added because its amount is greater than total incomes");
			}else{
				UserManager.addPayment(user, payment);
				successMessage="Payment was added";
				Utils.logger.info("Payment was added");
			}
		}
		model.addAttribute("errorPayment", errorMessage);
		model.addAttribute("successPayment", successMessage);
		model.addAttribute("panel", "payment");
		
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
			paymentType=Payment.EXPENSE;
		}else{
			paymentType=Payment.INCOME;
		}
		
		if(customCategory.trim().length()==0){
			model.addAttribute("errorCategory", "Category is empty");
			Utils.logger.info("custom category wasn't added because its length is 0");
			
		}else if(customCategory.trim().length()>=45){
			
		}else{
			IPaymentDAO.getInstance().addNewCategory(customCategory, paymentType, user);		
			JsonObject object = IPaymentDAO.getInstance().getCategoriesJSON(user.getId());
			session.setAttribute("categories", object);
			model.addAttribute("successCategory", "category was added");
			Utils.logger.info("custom category was added");
		}
		model.addAttribute("panel", "add-category");
		return "add";
	}	
}
