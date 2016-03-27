package com.model;

import java.time.LocalDate;
import java.time.Period;

public abstract class Payment {
	private int id;
	private String category;
	private String type;
	private String description;
	private double amount;
	private LocalDate date;
	

	//private String name;
	//private boolean isIncome;
	//private Period period;

	
	public Payment(String category, String type,
			double amount, LocalDate date) {
		this(category,type,"",amount, date);
	}
	
	public Payment(String category, String type, String description, double amount, LocalDate date) {
		super();
		this.category = category;
		this.type = type;
		this.description = description;
		this.amount = amount;
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
	
}
