package com.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.Expense;
import com.model.Income;
import com.model.Payment;
import com.model.User;
import com.model.UserManager;


@Controller
public class AddController {

	/*@RequestMapping(value="/getCategories", method=RequestMethod.GET, produces = "application/json")
	public @ResponseBody String getCategories(@RequestParam(value="type") String type,HttpServletResponse r){
		List<String> result = new LinkedList<String>();
		if(type.equalsIgnoreCase("income"))
			result = DBBudgetDAO.getInstance().getAllCategoriesByType("INCOME");
		else
			result = DBBudgetDAO.getInstance().getAllCategoriesByType("EXPENSE");
		JsonArray arr = new JsonArray();
		for(String category:result){
			arr.add(category);
			
		}
		return arr.toString();
	}*/
	@RequestMapping(value="/addPayment", method=RequestMethod.POST)
	public String add(Model model, 
			@RequestParam(value="amount") double amount,
			@RequestParam(value="payment_type") String paymentType,
			@RequestParam(value="category") String category,
			@RequestParam(value="date") String d,
			@RequestParam(value="description") String description,
			HttpSession session
			){
		System.out.println("Payment was added");
		DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		LocalDate date = LocalDate.parse(d, f);
		System.out.println("amount: " + amount);
		System.out.println("payment_type: " + paymentType);
		System.out.println("category: " + category);
		System.out.println("date: " + date.toString());
		System.out.println("description: " + description);
		
		Payment payment = null;
		//get user from session
		User user = (User)session.getAttribute("logedUser");
		
		if(paymentType.equalsIgnoreCase("EXPENSE")){
			payment = new Expense(category,description, amount, date);
		}else if(paymentType.equalsIgnoreCase("INCOME")){
			payment = new Income(category, description, amount, date);
		}
		
		//this will add payment to db+all foreign keys and to user's budget in session
		UserManager.addPayment(user, payment);
		
		//change to forward in future
		//return "forward:add";
		return "redirect:add"; 
	}
}