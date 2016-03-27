package com.model;

import java.time.LocalDate;

public class Expense extends Payment {

	public Expense(String category, String description,
			double amount, LocalDate date) {
		super(category, "Expensse", description, amount, date);
	}

}
