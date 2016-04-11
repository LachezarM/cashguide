package com.model;

import java.time.LocalDate;

import com.model.db.DBBudgetDAO;
import com.model.db.IUserDAO;

//Tested
public class UserManager {
	
	//register
	public static User createUserAfterRegister(String username, String password, String email){
		User user = new User(username,email, password);
		IUserDAO.getInstance().addUser(user);
		Budget budget = new Budget(LocalDate.now(), 1);
		user.addBudet(budget);
		DBBudgetDAO.getInstance().addBudget(user.getId(), budget);
		return user;
	}
	 
	//login
	//initialize user
	//this method gets the user, the budget for the current month if exists else creates new budget and all payments for current budget
	//TODO
	public static User createUserAfterLogin(String username, String password){
		//getting user only by username is not cool
		User user = IUserDAO.getInstance().getUser(username);
		Budget budget = DBBudgetDAO.getInstance().getBudget(user, LocalDate.now());
		if(budget == null){
			//no budget for this user for current month.
			//create budget for this user for current month
			budget = new Budget(LocalDate.now(),1);
			user.addBudet(budget);
			DBBudgetDAO.getInstance().addBudget(user.getId(), budget);
		}else{
			//get all payments for this budget
			user.addBudet(budget);
			//getPayments is changed on 4.4.16.returns only payments for the specific budget
			DBBudgetDAO.getInstance().getPayments(budget);
			
			System.out.println("budget after login: " + budget);
			
		}
		
		System.out.println("Budget in UserManager: " + budget.toString());
		return user;
	}
	
	//if this works, it'll be great
	public static void addPayment(User user, Payment payment){
		Budget budget = user.getBudget();
		LocalDate startDate = budget.getDate();
		LocalDate endDate = budget.getDate().plusMonths(1).minusDays(1);
		System.out.println("StartDate: " + startDate + ", EndDate: " + endDate);

		if((payment.getDate().compareTo(startDate)>=0)&&(payment.getDate().compareTo(endDate)<=0)){
			//add to current budget
			System.out.println("In range");
			//budget.addPayment(payment);
			UserManager.updateBudget(budget, payment);
			System.out.println("Budget balance to be added in db is: " + budget.getBalance());
			DBBudgetDAO.getInstance().addPayment(payment, budget);
		}else{
			//create new budget or add to old one
			System.out.println("new budget");
			//checks if a budget exists
			Budget budgetForOtherDate = DBBudgetDAO.getInstance().getBudget(user, payment.getDate());
			//budget doesn't exist->old or new budget doesn't matter
			if(budgetForOtherDate==null){
				//create budget
				Budget newBudget =new Budget(payment.getDate(), 1);
				DBBudgetDAO.getInstance().addBudget(user.getId(), newBudget);
				//newBudget.addPayment(payment);
				
				UserManager.updateBudget(newBudget, payment);
				DBBudgetDAO.getInstance().addPayment(payment, newBudget);
			}else{
				//budget exists->old or new budget
				//add to it
				//budgetForOtherDate.addPayment(payment);
				UserManager.updateBudget(budgetForOtherDate, payment);
				DBBudgetDAO.getInstance().addPayment(payment, budgetForOtherDate);
			}
		}
	}
	
	
	public static void updateBudget(Budget budget, Payment payment){
		budget.addPayment(payment);
		if(payment.getType().equalsIgnoreCase("expense")){
			budget.setBalance(budget.getBalance() - payment.getAmount());
			budget.setExpense(budget.getExpense()+payment.getAmount());
		}else if(payment.getType().equalsIgnoreCase("income")){
			budget.setBalance(budget.getBalance() + budget.getPercentageOfIncome()*payment.getAmount());
			budget.setIncome(budget.getIncome()+payment.getAmount());
		}
	}
	
	
	
}
