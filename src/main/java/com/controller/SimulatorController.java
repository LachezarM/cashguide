package com.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.Budget;
import com.model.Payment;
import com.model.User;
import com.model.Utils;
import com.model.db.DBBudgetDAO;

@Controller
public class SimulatorController {

	@RequestMapping(value="/getBudget", method=RequestMethod.GET)
	String budget() {
		return "simulator";
	}
	
	
	@RequestMapping(value="/getBudget", method=RequestMethod.POST)
	String getBudget(@RequestParam(value="date") String d, HttpSession session, Model model) {
		LocalDate date = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
		if(d.trim().length()!=0){
			try{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			date = LocalDate.parse(d, formatter);
			}catch(DateTimeParseException e){
				Utils.logger.error("Simulator>getBudget>date couldn't be parsed");
				model.addAttribute("error","invalid date");
				return "simulator";
			}
		}
		User user = (User)session.getAttribute("logedUser");
		
		Budget budget = DBBudgetDAO.getInstance().getBudget(user.getId(), date);
		
		System.out.println("Budget: " + budget);
		
		if(budget!=null){
			session.setAttribute("budget", budget);
			model.addAttribute("showBudget", budget);
		}else{
			session.removeAttribute("budget");
			Utils.logger.error("No such budget");
			model.addAttribute("error","No such budget");
		}
		return "simulator";
	}

	@RequestMapping(value="/calculate", method=RequestMethod.POST)
	String calculate(@RequestParam("period") int period, HttpServletRequest request, HttpSession session, Model model){
		
		if(period<0){
			model.addAttribute("error", "period must be positive integer");
			return "simulator";
		}
		Budget budget = (Budget)(session.getAttribute("budget"));
		
		String[] incomesID = request.getParameterValues("income");
		String[] expensesID = request.getParameterValues("expense");
		
		
		double balance = 0;
		if(incomesID!=null){
			for(String id:incomesID){
				int intId = Integer.valueOf(id);
				balance += budget.getPayments().get(Payment.INCOME).get(intId).getAmount();
			}
		}
		
		//balance = balance * budget.getPercentageOfIncome();
		
		if(expensesID!=null){
			for(String id:expensesID){
				int intId = Integer.valueOf(id);
				balance -= budget.getPayments().get(Payment.EXPENSE).get(intId).getAmount();
			}
		}	
		
		balance = balance*period;
		LocalDate startDate = budget.getDate();
		LocalDate endDate = budget.getDate().plusMonths(period);
		String result = "If you spend like "+ startDate.getMonth() + " " + startDate.getYear()+" after " + period +" months "
				+"in " + endDate.getMonth() +" "+ endDate.getYear()+" you will have " + balance;
		model.addAttribute("result", result);
		model.addAttribute("showBudget",budget);
		model.addAttribute("positiveSaving", balance>0);
		
		Utils.logger.info("Simulator: calculating future wealth");
		return "simulator";
	}
	
	
}
