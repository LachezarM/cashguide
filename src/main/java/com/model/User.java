package com.model;

public class User {
	
	private int id;
	private String username;
	private String password;
	private String email;
	//private String firstName;
	//private String lastName;
 	//private double balance;
  	//List<Payment> payments;

	private Budget budget;
	
	public User(){
		
	}
	
	public User(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public User(String username, String email, String password){
		this.username = username;
		this.password = password;
		this.email = email;
	}

	/*
	public User(String username, String email, String password, String firstNane, String lastName){
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstNane;
		this.lastName = lastName;
	}
	*/
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

	/*public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}*/

	public void setId(int id) {
		this.id = id;
	}
	
	public void addBudet(Budget budget){
		this.budget = budget;
	}

	public Budget getBudget(){
		return this.budget;
	}
}
