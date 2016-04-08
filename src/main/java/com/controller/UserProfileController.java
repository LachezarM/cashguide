package com.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.Budget;
import com.model.User;
import com.model.db.DBBudgetDAO;
import com.model.db.DBPaymentDAO;
import com.model.db.IBudgetDAO;
import com.model.db.IUserDAO;

@Controller
public class UserProfileController {
	
	@RequestMapping(value = "/changeUsername" , method = RequestMethod.GET)
	String changeUsername(HttpSession s) {
		s.removeAttribute("changePassword");
		s.removeAttribute("addBalance");
		s.removeAttribute("changeBudgetPercentage");
		s.removeAttribute("deleteCategory");
		s.setAttribute("changeUsername", true);
		return "UserProfile";
	}
	
	@RequestMapping(value = "/changePassword" , method = RequestMethod.GET)
	String changePassword(HttpSession s) {
		s.removeAttribute("changeUsername");
		s.removeAttribute("addBalance");
		s.removeAttribute("changeBudgetPercentage");
		s.setAttribute("changePassword", true);
		return "UserProfile";
	}
	
	@RequestMapping(value = "/addBalance" , method = RequestMethod.GET)
	String addBalance(HttpSession s) {

		s.removeAttribute("changePassword");
		s.removeAttribute("changeUsername");
		s.removeAttribute("changeBudgetPercentage");
		s.setAttribute("addBalance", true);
		return "UserProfile";
	}
	
	@RequestMapping(value = "/changeBudgetPercentage" , method = RequestMethod.GET)
	String changeBudgetPercentage(HttpSession s) {

		s.removeAttribute("changePassword");
		s.removeAttribute("addBalance");
		s.removeAttribute("changeUsername");
		s.setAttribute("changeBudgetPercentage", true);
		
		return "UserProfile";
	}
	
	/*@RequestMapping(value = "/deleteCategory" , method = RequestMethod.GET)
	String deleteCategory(HttpSession s, Model m){
		s.removeAttribute("changePassword");
		s.removeAttribute("addBalance");
		s.removeAttribute("changeBudgetPercentage");
		s.removeAttribute("changeUsername");
		s.setAttribute("deleteCategory", true);
		
		int id = ((User) s.getAttribute("logedUser")).getId();
		ArrayList<String> categories = DBBudgetDAO.getInstance().getCustomCategories(id);
		
		System.out.println("categories: " + categories);
		
		m.addAttribute("categories", categories);
		return "UserProfile";
	}
	*/
	@RequestMapping(value = "/back" , method = RequestMethod.GET)
	String back(HttpSession s) {
		s.removeAttribute("changePassword");
		s.removeAttribute("changeUsername");
		s.removeAttribute("addBalance");
		return "home";
	}
	
	@RequestMapping(value = "/changeUsername" , method = RequestMethod.POST)
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
	}
	
	@RequestMapping(value="/changePasswordUser", method = RequestMethod.GET)
	String changePasswordPost(@RequestParam(value="newPassword") String newPassword,
	HttpSession s,
	Model m) {
			if(newPassword.length() > 6 && !IUserDAO.getInstance().checkIfPasswordExists(newPassword)) {
			User u = (User) s.getAttribute("logedUser");
			IUserDAO.getInstance().changePassword(u.getId(), newPassword);
			m.addAttribute("change","successful");
			return "UserProfile";
		}
			m.addAttribute("change","unsuccessful");
			return "UserProfile";
	}
	
	@RequestMapping(value="/changeBudgetPercentage", method = RequestMethod.POST)
	String changeBudgetPercentagePost(@RequestParam(value="percentage") double percentage,
	HttpSession s,
	Model m) {
			//isValidPercentage
			//change percentage in the budget
			//change percentage in the db
			//change balance in the budget
			//change balance in the db
			Budget budget = ((User) s.getAttribute("logedUser")).getBudget();
			double income = budget.getIncome();
			double oldBalance = budget.getBalance();
			double oldPercentage = budget.getPercentageOfIncome();
			double expenses = income*oldPercentage - oldBalance;
			if(percentage>0&&percentage<=1){
				budget.setPercentageOfIncome(percentage);
				budget.setBalance(income*percentage - expenses);
				System.out.println("Budget balance after changing percentage: " + budget.getBalance());
				
				IBudgetDAO.getInstance().updateBudget(budget);
				//balance in the db must be changed
			}else{
				//errorMessage shouldBeShown
				m.addAttribute("errorMessage", "Your percentage value is invalid");
			}
			return "UserProfile";
	}
	
	
	
	
}
