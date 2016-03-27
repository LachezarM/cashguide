package com.model;

import java.time.LocalDate;

public class Income extends Payment{

	public Income(String category, String description,
			double amount, LocalDate date) {
		super(category, "Income", description, amount, date);
	}

}
