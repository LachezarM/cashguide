package com.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
import com.model.UserManager;
import com.model.Utils;
import com.model.db.DBBudgetDAO;
import com.model.db.DBPaymentDAO;
import com.model.db.IBudgetDAO;
import com.model.db.IPaymentDAO;
import com.model.db.IUserDAO;

@Controller
public class SettingsController {

	private static final String success = "successMessage";
	private static final String error = "errorMessage";
	private static final String panel = "panel";
	
	private static final String passwordSuccess = "Successful changing of password";
	private static final String percentageSuccess = "Percentage was changed";
	private static final String categorySuccess = "Category was successfully deleted";
	private static final String paymentsSuccess = "Selected payments were deleted";
	private static final String emailSuccess = "Email was changed";
	
	private static final String percentageError = "Your percentage value is invalid";
	private static final String passwordError = "Unsuccessful changing of password";
	private static final String budgetError = "No such budget";
	private static final String invalidDate = "Invalid date";
	private static final String categoryError = "No such category";
	private static final String emailError = "This email is taken";
	
	
	@RequestMapping(value="/settings", method = RequestMethod.GET)
	String settings(HttpSession session, Model model){
		if(session.getAttribute("logedUser")==null){
			return "redirect:index";
		}
		model.addAttribute("panel", "changePassword");
		Utils.logger.info("settings link was clicked");
		return "settings";
	}
	
	@RequestMapping(value = "/changePassword" , method = RequestMethod.GET)
	String changePassword(HttpSession session, Model model) {
		if(session.getAttribute("logedUser")==null){
			return "redirect:index";
		}
		model.addAttribute(panel, "changePassword");
		Utils.logger.info("chagepassword link was clicked");
		return "settings";
	}
	
	@RequestMapping(value = "/changeBudgetPercentage" , method = RequestMethod.GET)
	String changeBudgetPercentage(HttpSession session, Model model) {
		if(session.getAttribute("logedUser")==null){
			return "redirect:index";
		}
		model.addAttribute(panel, "changePercentage");
		Utils.logger.info("changePercentage link was clicked");
		return "settings";
	}
	
	@RequestMapping(value = "/changeEmail" , method = RequestMethod.GET)
	String changeEmail(HttpSession session, Model model) {
		if(session.getAttribute("logedUser")==null){
			return "redirect:index";
		}
		model.addAttribute(panel, "changeEmail");
		Utils.logger.info("changeEmail link was clicked");
		return "settings";
	}
	
	@RequestMapping(value = "/deleteCategory" , method = RequestMethod.GET)
	String deleteCategory(HttpSession session, Model model){
		Utils.logger.info("delete category link was clicked");
		User user = (User)session.getAttribute("logedUser");
		if(user!=null){
			int id = user.getId();
			ArrayList<String> categories = DBBudgetDAO.getInstance().getCustomCategories(id);
			model.addAttribute("categories", categories);
		}else{
			return "redirect:index";
		}
		model.addAttribute(panel, "deleteCategory");
		
		return "settings";
	}
	
	@RequestMapping(value = "/deletePayment" , method = RequestMethod.GET)
	String deletePayment(HttpSession session, Model model) {
		Utils.logger.info("delete category link was clicked");
		if(session.getAttribute("logedUser")==null){
			return "redirect:index";
		}
		model.addAttribute(panel, "deletePayment");
		return "settings";
	}
	
	@RequestMapping(value = "/getBudgetDel" , method = RequestMethod.POST)
	String getBudget(@RequestParam(value="date") String d, HttpSession session, Model model) {
		Utils.logger.info("getDeleteBudget link was clicked");
		LocalDate date = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1);
		if(d.trim().length()!=0){
			try{
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			date = LocalDate.parse(d, formatter);
			}catch(DateTimeParseException e){
				model.addAttribute(error,invalidDate);
				Utils.logger.error("Parsing date in getDeleteBudget");
				return "settings";
			}
		}
		User user = (User)session.getAttribute("logedUser");
		Budget budget = DBBudgetDAO.getInstance().getBudget(user.getId(), date);		
		if(budget!=null){
			model.addAttribute(panel, "deletePayment");
			session.setAttribute("delBudget", budget);
			model.addAttribute("month", true);
		}else{
			session.removeAttribute("delBudget");
			model.addAttribute(error, budgetError);
		}		
		
		return "settings";
	}
	
	@RequestMapping(value="/changePasswordUser", method = RequestMethod.POST)
	String changePasswordPost(@RequestParam(value="newPassword") String newPassword, @RequestParam(value="oldPassword") String oldPassword, HttpSession session, Model model) {
		newPassword=newPassword.trim();
		oldPassword = oldPassword.trim();
		if(Utils.isValidPassword(newPassword)&&Utils.isValidPassword(oldPassword)){
			User user = (User)session.getAttribute("logedUser");
			String oldHashedPass = UserManager.hashPassword(oldPassword);
			if((user!=null)&&(!user.getPassword().equals(oldHashedPass))){/*&& !(IUserDAO.getInstance().checkIfPasswordExists(newPassword))*/
				String newHashedPassword = UserManager.hashPassword(newPassword);
				IUserDAO.getInstance().changePassword(user.getId(), newHashedPassword);
				model.addAttribute(success,passwordSuccess);
				Utils.logger.info("changing password");
			}else{
				Utils.logger.error("old password was incorrect or user is null");
				model.addAttribute(error, passwordError);
			}
		}else{
			Utils.logger.error("password was incorrect");
			model.addAttribute(error,passwordError);
		}
		model.addAttribute(panel, "changePassword");
		return "settings";
	}
	
	@RequestMapping(value="/changeBudgetPercentage", method = RequestMethod.POST)
	String changeBudgetPercentagePost(/*@RequestParam(value="percentage") double percentage,*/
	HttpSession s, HttpServletRequest request,
	Model model) {
		String param = request.getParameter("percentage");
		if((param==null)||(param.trim().length()==0)||(!param.trim().matches("(\\d){1,3}"))){
			model.addAttribute(error, percentageError);
		}else{
			double percentage = Double.valueOf(param);
			Budget budget = ((User) s.getAttribute("logedUser")).getBudget();
			double income = budget.getIncome();
			double oldBalance = budget.getBalance();
			double oldPercentage = budget.getPercentageOfIncome();
			double expenses = income*oldPercentage - oldBalance;
			if(percentage>0&&percentage<=100){
				percentage /= 100;
				budget.setPercentageOfIncome(percentage);
				budget.setBalance(income*percentage - expenses);					
				IBudgetDAO.getInstance().updateBudget(budget);
				Utils.logger.info("budget percentage was changed");
				model.addAttribute(success, percentageSuccess);
			}else{
				model.addAttribute(error, percentageError);
				Utils.logger.error("budget percentage wasn't in range(0;100]");
			}
		}
		model.addAttribute(panel, "changePercentage");
		return "settings";
	}

	@RequestMapping(value="/changeEmail", method = RequestMethod.POST)
	String changeEmail(HttpServletRequest request, HttpSession session, Model model) {
		String newEmail = request.getParameter("email").trim();
		if(Utils.isValidEmail(newEmail)){
			User user = (User)session.getAttribute("logedUser");
			if(IUserDAO.getInstance().checkIfEmailExists(newEmail)){
				model.addAttribute(error, emailError);
				Utils.logger.error("email exists");
			}else{
				model.addAttribute(success, emailSuccess);
				IUserDAO.getInstance().changeEmail(user.getId(), newEmail);
				Utils.logger.info("email was changed");
			}
		}else{
			model.addAttribute(error, emailError);
			Utils.logger.error("new email is invalid");
		}
		model.addAttribute(panel, "changeEmail");
		return "settings";
	}
	
	@RequestMapping(value="/deleteCategory", method = RequestMethod.POST)
	String deleteCategoryPost(HttpServletRequest request, HttpSession session, Model model) {
		String category = request.getParameter("category");
		if(category==null||category.trim().length()==0){
			model.addAttribute(error, categoryError);
			Utils.logger.error("deleteCategory:category was either null or its length was 0");
		}else{
			int id = ((User) session.getAttribute("logedUser")).getId();
			DBPaymentDAO.getInstance().deleteCategory(category, id);
			ArrayList<String> categories = DBBudgetDAO.getInstance().getCustomCategories(id);
			model.addAttribute(panel, "deleteCategory");
			model.addAttribute("categories", categories);
			Utils.logger.info("categories was deleted");
			model.addAttribute(success, categorySuccess);
		}
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
		List<Payment> deleted = new LinkedList<Payment>();
		if(incomesID!=null){
			for(String id:incomesID){
				int intId = Integer.valueOf(id);
				Payment payment = budget.getPayments().get("INCOME").get(intId);
				deleted.add(payment);
				incomes += payment.getAmount();
				IPaymentDAO.getInstance().deletePayment(payment.getId());
			}			
			budget.getPayments().get("INCOME").removeAll(deleted);
			deleted.clear();
		}
		if(expensesID!=null){
			for(String id:expensesID){
				int intId = Integer.valueOf(id);
				Payment payment = budget.getPayments().get("EXPENSE").get(intId);
				expenses+= payment.getAmount();				
				deleted.add(payment);
				IPaymentDAO.getInstance().deletePayment(payment.getId());
			}
			budget.getPayments().get("EXPENSE").removeAll(deleted);
			deleted.clear();
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
		model.addAttribute(success, paymentsSuccess);
		model.addAttribute(panel, "deletePayment");
		model.addAttribute("month", true);
		Utils.logger.info("payments were deleted");
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
}
