package com.model.db;

import java.util.List;

import com.model.Budget;
import com.model.Expense;
import com.model.Income;

public interface IBudgetDAO {
	
	void addBudget(int userId, Budget budget);
	
	void removeBudget(int budgetId);
	
	boolean changePercentage(int budgetId, double percentage);
	
	Budget getBudget(int userId);//get budget + all payments for this budget in current month;
	
	void addIncome(Income income, int budgetId);
	
	void addExpense(Expense expense, int budgetId);
	
	void removeIncome(int incomeId);
	
	void removeExpense(int expenseId);
		
	List<Budget> getAllBudgets(int userId);
}
