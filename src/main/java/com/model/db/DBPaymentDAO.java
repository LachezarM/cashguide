package com.model.db;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.model.Payment;

public class DBPaymentDAO implements IPaymentDAO{
	
	private static DBPaymentDAO instance = null;
	
	private DBPaymentDAO(){
		
	}
	
	public static synchronized DBPaymentDAO getInstance(){
		if(instance==null){
			instance = new DBPaymentDAO();
		}
		return instance;
	}

	@Override
	public void addPayment(Payment payment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePayment(Payment payment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Payment> getAllIncomes(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payment> getAllExpenses(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payment> getAllPayments(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payment> getAllPaymentsForPeriod(int userId,
			LocalDate startingDate, Period period) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payment> getAllIncomesByCategory(int userId, String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payment> getAllExpensesByCategory(int userId, String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payment> getAllIncomesForSpecificMonth(int month) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payment> getAllExpensesForSpecificMonth(int month) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
