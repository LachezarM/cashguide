package com.model.db;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.model.Payment;

public interface IPaymentDAO {
		
	void addPayment(Payment payment);
	
	void removePayment(Payment payment);

	List<Payment> getAllIncomes(int userId);
	
	List<Payment> getAllExpenses(int userId);
	
	List<Payment> getAllPayments(int userId);
	
	List<Payment> getAllPaymentsForPeriod(int userId, LocalDate startingDate, Period period);
	
	List<Payment> getAllIncomesByCategory(int userId, String category);
	
	List<Payment> getAllExpensesByCategory(int userId, String category);
	
	List<Payment> getAllIncomesForSpecificMonth(int month);
	
	List<Payment> getAllExpensesForSpecificMonth(int month);
	//more methods
	
}
