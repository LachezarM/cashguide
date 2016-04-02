package com.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.model.db.DBBudgetDAO;


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
			@RequestParam(value="description") String description
			){
		System.out.println("Payment was added");
		DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		LocalDate date = LocalDate.parse(d, f);
		System.out.println("amount: " + amount);
		System.out.println("payment_type: " + paymentType);
		System.out.println("category: " + category);
		System.out.println("date: " + date.toString());
		System.out.println("description: " + description);
		
		//change to forward in future
		//return "forward:add";
		return "redirect:add"; 
	}
}
