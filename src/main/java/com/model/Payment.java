package com.model;

import java.time.LocalDate;

public abstract class Payment {
	private int id;
	private String category;
	private String type;
	private String description;
	private double amount;
	private LocalDate date;
	
	public static final String INCOME = "INCOME";
	public static final String EXPENSE = "EXPENSE";
	private static final String DEFAULT_DESCRIPTION  = "";
	
	public Payment(String category, String type,
			double amount, LocalDate date) {
		this(category,type,DEFAULT_DESCRIPTION,amount, date);
	}
	
	public Payment(String category, String type, String description, double amount, LocalDate date) {
		super();
		this.category = category;
		this.type = type;
		this.description = description;
		if(amount<0){
			this.amount = 0;
		}else{
			this.amount = amount;
		}
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		return amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getType(){
		return this.type;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", category=" + category + ", type="
				+ type + ", description=" + description + ", amount=" + amount
				+ ", date=" + date + "]";
	}
}
