package com.model;

import java.util.List;

public class User {
	
	private int id;
	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private double balance;
	List<Payment> payments;
	
	public User(String username, String email, String password){
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public double getBalance() {
		return balance;
	}

	public List<Payment> getPayments() {
		return payments;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
