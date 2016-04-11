package com.model.db;

import com.model.User;

public interface IUserDAO {
	
	static IUserDAO getInstance() {		
		return DBUserDAO.getInstance();
	}
	
	//used
	boolean checkIfUserExists(String username, String email);
	
	//used
	boolean checkIfEmailExists(String email);
	
	//used
	boolean checkForCorrectUsernameAndPassword(String username, String password);
	
	//after adding the user in DB, the id property in user object is set to id form DB
	//used
	void addUser(User user);
	
	//used
	User getUser(String username);
	
	//not used
	//List<User> getAllUsers();
	
	//used
	boolean changePassword(int id, String newPassword);
	
	//used
	boolean changeEmail(int id, String newEmail);
	
	//boolean changeUserProfile(int id, User newUser);//changing password, email, firstName, lastName

	//not used
	//boolean changeUserProfile(int id, String newUsername);

	//not used
	//boolean checkIfPasswordExists(String newPassword);
}
