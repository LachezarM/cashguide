package com.model;

import java.time.LocalDate;
import java.time.Period;

public class Payment {
	private int id;
	private String name;
	private String type;
	private boolean isIncome;
	private String description;
	private double amount;
	private LocalDate date;
	private Period period;
}
