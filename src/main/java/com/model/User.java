package com.model;

public class User {
	
	private int id;
	private String username;
	private String password;
	private String email;
	private Budget budget;
	
	public User(String username, String password){
		this.username = username;
		this.password = password;
	}
	
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

	public void setEmail(String email){
		this.email = email;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void addBudet(Budget budget){
		this.budget = budget;
	}

	public Budget getBudget(){
		return this.budget;
	}
	
	@Override
	public String toString() {
		return "id: " + id + ", username: " + username + ", password: " + budget.toString();
	} 
}
