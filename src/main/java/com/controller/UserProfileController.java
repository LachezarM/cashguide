package com.controller;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

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
import com.model.db.DBBudgetDAO;
import com.model.db.DBPaymentDAO;
import com.model.db.IBudgetDAO;
import com.model.db.IPaymentDAO;
import com.model.db.IUserDAO;

@Controller
public class UserProfileController {
	
	
	@RequestMapping(value = "/changePassword" , method = RequestMethod.GET)
	String changePassword(Model model) {
		model.addAttribute("panel", "changePassword");
		return "settings";
	}
	
	@RequestMapping(value = "/changeBudgetPercentage" , method = RequestMethod.GET)
	String changeBudgetPercentage(Model model) {
		model.addAttribute("panel", "changePercentage");
		return "settings";
	}
	
	@RequestMapping(value = "/deleteCategory" , method = RequestMethod.GET)
	String deleteCategory(HttpSession session, Model model){
		User user = (User)session.getAttribute("logedUser");
		if(user!=null){
			int id = user.getId();
			ArrayList<String> categories = DBBudgetDAO.getInstance().getCustomCategories(id);
			System.out.println("categories: " + categories);
			model.addAttribute("categories", categories);
		}
		model.addAttribute("panel", "deleteCategory");
		return "settings";
	}
	
	
	@RequestMapping(value = "/deletePayment" , method = RequestMethod.GET)
	String deletePayment(HttpSession session, Model model) {
		model.addAttribute("panel", "deletePayment");
		return "settings";
	}
	
	@RequestMapping(value = "/getBudgetDel" , method = RequestMethod.POST)
	String getBudget(@RequestParam(value="date") String d, HttpSession session, Model model) {
		LocalDate date = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
		if(d.trim().length()!=0){
			try{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			date = LocalDate.parse(d, formatter);
			}catch(DateTimeParseException e){
				model.addAttribute("error","invalid date");
				return "settings";
			}
		}
		User user = (User)session.getAttribute("logedUser");
		
		Budget budget = DBBudgetDAO.getInstance().getBudget(user.getId(), date);
		
		System.out.println("Budget: " + budget);
		
		if(budget!=null){
			//session.setAttribute("budget", budget);
			model.addAttribute("panel", "deletePayment");
			session.setAttribute("delBudget", budget);
			//model.addAttribute("delBudget", budget);
			model.addAttribute("month", true);
		}else{
			session.removeAttribute("delBudget");
			model.addAttribute("error","No such budget");
		}		
		
		return "settings";
	}
	
	@RequestMapping(value="/changePasswordUser", method = RequestMethod.POST)
	String changePasswordPost(@RequestParam(value="newPassword") String newPassword,
			@RequestParam(value="oldPassword") String oldPassword, HttpSession session,
	Model model) {
		User user = (User)session.getAttribute("logedUser");
		if(user!=null){
			if(user.getPassword().equals(oldPassword)&&(newPassword.length() > 6)&& !(IUserDAO.getInstance().checkIfPasswordExists(newPassword))) {
					
					IUserDAO.getInstance().changePassword(user.getId(), newPassword);
					model.addAttribute("successMessage","successful");
			}else{
				model.addAttribute("errorMessage","unsuccessful");
			}
		}else{
			model.addAttribute("errorMessage","unsuccessful");
		}
		model.addAttribute("panel", "changePassword");
		return "settings";
	}
	
	@RequestMapping(value="/changeBudgetPercentage", method = RequestMethod.POST)
	String changeBudgetPercentagePost(@RequestParam(value="percentage") double percentage,
	HttpSession s,
	Model model) {
			Budget budget = ((User) s.getAttribute("logedUser")).getBudget();
			double income = budget.getIncome();
			double oldBalance = budget.getBalance();
			double oldPercentage = budget.getPercentageOfIncome();
			double expenses = income*oldPercentage - oldBalance;
			if(percentage>0&&percentage<=100){
				percentage /= 100;
				budget.setPercentageOfIncome(percentage);
				budget.setBalance(income*percentage - expenses);
				System.out.println("Budget balance after changing percentage: " + budget.getBalance());
				
				IBudgetDAO.getInstance().updateBudget(budget);
				model.addAttribute("successMessage", "Percentage was updated");
			}else{
				model.addAttribute("errorMessage", "Your percentage value is invalid");
			}
			model.addAttribute("panel", "changePercentage");
			return "settings";
	}

	@RequestMapping(value="/deleteCategory", method = RequestMethod.POST)
	public String deleteCategoryPost(@RequestParam(value="category") String category, HttpSession session, Model model) {
			int id = ((User) session.getAttribute("logedUser")).getId();
			DBPaymentDAO.getInstance().deleteCategory(category, id);
			ArrayList<String> categories = DBBudgetDAO.getInstance().getCustomCategories(id);
			model.addAttribute("panel", "deleteCategory");
			model.addAttribute("categories", categories);
			model.addAttribute("successMessage", "category was successfully deleted");
			return "settings";
	}
	
	@RequestMapping(value = "/deletePayment" , method = RequestMethod.POST)
	String deletePaymentPost(Model model, HttpServletRequest request, HttpSession session){
		Budget budget = (Budget)(session.getAttribute("delBudget"));
		User user = (User)(session.getAttribute("logedUser"));
		String[] incomesID = request.getParameterValues("income");
		String[] expensesID = request.getParameterValues("expense");
		
		
		double incomes = 0;
		double expenses = 0;
		if(incomesID!=null){
			for(String id:incomesID){
				int intId = Integer.valueOf(id);
				Payment payment = budget.getPayments().get("INCOME").get(intId);
				incomes += payment.getAmount();
				budget.getPayments().get("INCOME").remove(intId);
				
				IPaymentDAO.getInstance().deletePayment(payment.getId());
			}
		}		
		if(expensesID!=null){
			for(String id:expensesID){
				int intId = Integer.valueOf(id);
				Payment payment = budget.getPayments().get("EXPENSE").get(intId);
				expenses= payment.getAmount();
				budget.getPayments().get("EXPENSE").remove(intId);
				
				IPaymentDAO.getInstance().deletePayment(payment.getId());
			}
		}
		
		budget.setIncome(budget.getIncome()-incomes);
		budget.setExpense(budget.getExpense()-expenses);
		
		double balance = budget.getIncome()*budget.getPercentageOfIncome()-budget.getExpense();
		budget.setBalance(balance);
		
		//update budget in DB
		IBudgetDAO.getInstance().updateBudget(budget);
		
		if(user.getBudget().getId()==budget.getId()){
			user.addBudet(budget);
		}

		model.addAttribute("panel", "deletePayment");
		model.addAttribute("month", true);
		
		return "settings";
	}
	/*@RequestMapping(value = "/addBalance" , method = RequestMethod.GET)
	String addBalance(HttpSession s) {

		s.removeAttribute("changePassword");
		s.removeAttribute("changeUsername");
		s.removeAttribute("changeBudgetPercentage");
		s.setAttribute("addBalance", true);
		return "UserProfile";
	}*/
	
	/*@RequestMapping(value = "/changeUsername" , method = RequestMethod.POST)
	String changeUsernamePost(@RequestParam(value ="username") String username,
			HttpSession s,
			Model m) {
		if(username.length() != 0 && !IUserDAO.getInstance().checkIfUserExests(username)) {
			User u = (User) s.getAttribute("logedUser");
			System.out.println(u.getId());
			IUserDAO.getInstance().changeUserProfile(u.getId(), username);
			System.out.println(u.getId());
			m.addAttribute("change", "Sucessful");
			return "UserProfile";
		
		}
		m.addAttribute("change", "UnSucessful");
		return "UserProfile";
	}*/
}
