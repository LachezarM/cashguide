package com.model.db;

import java.time.LocalDate;

import com.model.Budget;
import com.model.Payment;
import com.model.User;

public interface IBudgetDAO {
	
	static IBudgetDAO getInstance()  {
		return DBBudgetDAO.getInstance();
	}
	void addBudget(int userId, Budget budget);
		
	void updateBudget(Budget budget);
	
	Budget getBudget(User user, LocalDate date);//get budget only
	
	void getPayments(Budget budget);//gets all payments for specific budget
	
	Budget getBudgetAndPayments(int userId, LocalDate date);///get budget + all payments for this budget
	
	void addPayment(Payment payment, Budget budget);	
}
