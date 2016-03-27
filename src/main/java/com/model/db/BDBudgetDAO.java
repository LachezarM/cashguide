package com.model.db;

import java.util.List;

import com.model.Budget;
import com.model.Expense;
import com.model.Income;

public class BDBudgetDAO implements IBudgetDAO{
	
private static BDBudgetDAO instance = null;
	
	private BDBudgetDAO(){
		
	}
	
	public static synchronized BDBudgetDAO getInstance(){
		if(instance==null){
			instance = new BDBudgetDAO();
		}
		return instance;
	}

	@Override
	public void addBudget(int userId, Budget budget) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeBudget(int budgetId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean changePercentage(int budgetId, double percentage) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Budget getBudget(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addIncome(Income income, int budgetId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addExpense(Expense expense, int budgetId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeIncome(int incomeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeExpense(int expenseId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Budget> getAllBudgets(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
