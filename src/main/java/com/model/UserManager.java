package com.model;

import java.time.LocalDate;

import com.model.db.DBBudgetDAO;
import com.model.db.DBUserDAO;
import com.model.db.IUserDAO;

//Tested
public class UserManager {
	private static final double PERCENTAGE = 1;	
	/*
	 * REGISTER INITIALIZATION
	 * Creates user
	 * Saves user in DB
	 * Creates budget
	 * Saves budget in DB
	 * 
	 * */
	public static User createUserAfterRegister(String username, String password, String email){
		User user = new User(username,email, password);
		DBUserDAO.getInstance().addUser(user);
		Budget budget = new Budget(LocalDate.now(), PERCENTAGE);
		user.addBudet(budget);
		DBBudgetDAO.getInstance().saveBudget(user.getId(), budget);
		return user;
	}
	 
	/*
	 * LOGIN INITIALIZATION
	 * Should it check if user exists here, not in IndexPageController?
	 * Get user from DB
	 * Get user's budget if exists
	 * Get payments for this budget
	 * Create new budget if there is no budget for current month
	 * 
	 * */
	public static User createUserAfterLogin(String username, String password){
		//getting user only by username is not cool!!!!TODO
		User user = DBUserDAO.getInstance().getUser(username);
		Budget budget = DBBudgetDAO.getInstance().getBudget(user, LocalDate.now());
		if(budget == null){
			//create budget for this user for current month
			budget = new Budget(LocalDate.now(),PERCENTAGE);
			user.addBudet(budget);
			DBBudgetDAO.getInstance().saveBudget(user.getId(), budget);
		}else{
			//get all payments for this budget
			user.addBudet(budget);
			//getPayments is changed on 4.4.16.returns only payments for the specific budget
			DBBudgetDAO.getInstance().getPayments(budget);
		}
		
		System.out.println("Budget in UserManager: " + budget.toString());
		
		return user;
	}
	
	/*
	 * change user password
	 * 
	 * */
	public static boolean changePassword(User user, String newPassword){
		if(newPassword.length() > 6) {
			IUserDAO.getInstance().changePassword(user.getId(), newPassword);	
			return true;
		}
		return false;
	}

	


}
