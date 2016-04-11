package com.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import com.model.db.DBBudgetDAO;
import com.model.db.DBManager;
import com.model.db.IUserDAO;

//Tested
public class UserManager {
	
	//register
	public static User createUserAfterRegister(String username, String password, String email){		
		User user = new User(username,email, password);
		Budget budget = new Budget(LocalDate.now(), 1);
		//with transaction
		IUserDAO.getInstance().addUser(user, budget);
		//IBudgetDAO.getInstance().addBudget(user.getId(), budget);
		user.addBudet(budget);
		return user;
	}
	 
	//login initialize user
	//this method gets the user, the budget for the current month if exists else creates new budget and add all payments for current budget
	public static User createUserAfterLogin(String username, String password){
		//------------------------------------------------------------------------------
		//-------------------------------FUCK THIS SHIT---------------------------------
		//------------------------------------------------------------------------------
			Connection con = DBManager.getDBManager().getConnection();
			
			try {
				con.setAutoCommit(false);
				System.out.println("autocommit is false");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
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
				user.addBudet(budget);
				DBBudgetDAO.getInstance().getPayments(budget);
				
				System.out.println("budget after login: " + budget);
			}
			System.out.println("Budget in UserManager: " + budget.toString());
			
		
			try {
				con.commit();
			} catch (SQLException e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				System.out.println("rollback");
				e.printStackTrace();
			}finally{
				try {
					con.setAutoCommit(true);
					System.out.println("autocommit is true");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		
			//----------------------------------------------------------------------------
			//----------------------------------------------------------------------------
			//----------------------------------------------------------------------------
		return user;
	}
	
	//if this works, it'll be great
	public static void addPayment(User user, Payment payment){
		Budget budget = user.getBudget();
		LocalDate startDate = budget.getDate();
		LocalDate endDate = budget.getDate().plusMonths(1).minusDays(1);

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
		if(payment.getType().equalsIgnoreCase(Payment.EXPENSE)){
			budget.setBalance(budget.getBalance() - payment.getAmount());
			budget.setExpense(budget.getExpense()+payment.getAmount());
		}else if(payment.getType().equalsIgnoreCase(Payment.INCOME)){
			budget.setBalance(budget.getBalance() + budget.getPercentageOfIncome()*payment.getAmount());
			budget.setIncome(budget.getIncome()+payment.getAmount());
		}
	}
}
