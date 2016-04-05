package com.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Budget {
	private int id;
	private LocalDate date;
	private double percentageOfIncome;
	private HashMap<String, ArrayList<Payment>> payments;//income/expense->Income/Expense
	private double balance;//precetage*income by default;
	private double income;
	
	public Budget(LocalDate date, double percentageOfIncome) {
		super();
		this.date = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
		this.percentageOfIncome = percentageOfIncome;
		this.payments = new HashMap<String, ArrayList<Payment>>();
		this.payments.put("INCOME", new ArrayList<Payment>());
		this.payments.put("EXPENSE", new ArrayList<Payment>());
		this.balance = 0;
		this.income = 0;
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
		if(percentageOfIncome>0&&percentageOfIncome<=1)
			this.percentageOfIncome = percentageOfIncome;
		else
			this.percentageOfIncome = 1;
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
		//System.out.println("BALANCE BEFORE ADDING new payment: " + this.balance);
		//System.out.println("Amount to be added: " + payment.getAmount());
		//System.out.println("percentage: " + this.percentageOfIncome);
		//System.out.println("gg: " + this.percentageOfIncome*payment.getAmount());
		
		if(payment.getType().equalsIgnoreCase("expense")){
			this.balance -= payment.getAmount();
		}else if(payment.getType().equalsIgnoreCase("income")){
			this.balance += this.percentageOfIncome*payment.getAmount();
			this.income += payment.getAmount();
		}
		//System.out.println("BALANCE: " + this.balance);
	}

	@Override
	public String toString() {
		return "Budget [id=" + id + ", date=" + date + ", percentageOfIncome="
				+ percentageOfIncome + ", payments=" + payments + ", balance="
				+ balance + "]";
	}

	public void addIncome(double income){
		this.income+=income;
	}
	
	public double getIncome(){
		return this.income;
	}
}
