package com.model.db;

import java.util.List;

import com.model.User;

public interface IUserDAO {
	
	static IUserDAO getInstance() {		
		return DBUserDAO.getInstance();
	}
	
	boolean checkIfUserExests(String username);
	
	boolean checkForCorrectPassword(String username, String password);
	
	//after adding the user in DB, the id property in user object is set to id form DB
	void addUser(User user);
	
	User getUser(String username);
	
	List<User> getAllUsers();
	
	boolean changePassword(int id, String newPassword);
	
	boolean changeEmail(int id, String newEmail);
	
	//boolean changeUserProfile(int id, User newUser);//changing password, email, firstName, lastName

	boolean changeUserProfile(int id, String newUsername);

	boolean checkIfPasswordExists(String newPassword);
}
