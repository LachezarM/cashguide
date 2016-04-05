package com.model.db;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.model.Budget;
import com.model.Payment;
import com.model.User;

public interface IBudgetDAO {
	
	static IBudgetDAO getInstance()  {
		return DBBudgetDAO.getInstance();
	}
	void saveBudget(int userId, Budget budget);
	
	void removeBudget(int budgetId);
	
	void updateBudget(Budget budget);
	
	boolean changePercentage(int budgetId, double percentage);
	
	Budget getBudget(User user, LocalDate date);//get budget only
	
	void getPayments(Budget budget);//gets all payments for specific budget
	
	
	//REFACTORING 
	//THIS METHODS WILL BE DELETED
	//-----------------------------------------------------------------------------------------|
	//Budget getBudget(int userId);//get budget + all payments for this budget in current month; |
	//                                                                                         |
	//Budget getBudget(int userId, LocalDate date);///get budget + all payments for this budget  |
	//-----------------------------------------------------------------------------------------|
	
	void addPayment(Payment payment, Budget budget);
	
	List<Budget> getAllBudgets(int userId);
	
	Map<String, ArrayList<String>> getAllCategories();
}
