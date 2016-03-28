package com.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Budget {
	int id;
	LocalDate date;
	double percentageOfIncome;
	HashMap<String, ArrayList<Payment>> payments;//income/expense->Income/Expense
	double balance;//precetage*income by default;
	
	public Budget(LocalDate date, double percentageOfIncome) {
		super();
		this.date = date;
		this.percentageOfIncome = percentageOfIncome;
		this.payments = new HashMap<String, ArrayList<Payment>>();
		this.payments.put("INCOME", new ArrayList<Payment>());
		this.payments.put("EXPENSE", new ArrayList<Payment>());
		this.balance = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPercentageOfIncome() {
		return percentageOfIncome;
	}

	public void setPercentageOfIncome(double percentageOfIncome) {
		this.percentageOfIncome = percentageOfIncome;
	}

	public int getCurrentMonth() {
		return date.getMonthValue();
	}
	
	public LocalDate getDate() {
		return date;
	}

	public HashMap<String, ArrayList<Payment>> getPayments() {
		return payments;
	}

	public double getBalance() {
		return balance;
	}
	
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public void addPayment(Payment payment){
		this.payments.get(payment.getType()).add(payment);
	}
}
