package com.model;

import java.util.List;

public class User {
	
	private String username;
	private String password;
	private String email;
	
	private double balance;
	List<Payment> payments;

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
}
