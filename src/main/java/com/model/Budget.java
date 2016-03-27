package com.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Budget {
	int id;
	int currentMonth;
	double percentageOfIncome;
	HashMap<String, ArrayList<Payment>> payments;//income/expense->Income/Expense
	double balance;//precetage*income by default;
	
	public Budget(int currentMonth, double percentageOfIncome) {
		super();
		this.currentMonth = currentMonth;
		this.percentageOfIncome = percentageOfIncome;
		this.payments = new HashMap<String, ArrayList<Payment>>();
		this.payments.put("Income", new ArrayList<Payment>());
		this.payments.put("Expense", new ArrayList<Payment>());
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
		return currentMonth;
	}

	public HashMap<String, ArrayList<Payment>> getPayments() {
		return payments;
	}

	public double getBalance() {
		return balance;
	}
	
	
}
