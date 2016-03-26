package com.model.db;

import java.util.List;

import com.model.User;

public interface IUserDAO {
	
	boolean checkIfUserExests(String username);
	
	User addUser(User user);
	
	User getUser(String username);
	
	List<User> getAllUsers();
	
	void changePassword(int id, String newPassword);
	
	void changeEmail(int id, String newEmail);
	
	void changeUserProfile(int id, User newUser);//changing password, email, firstName, lastName
}
