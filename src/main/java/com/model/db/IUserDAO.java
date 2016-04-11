package com.model.db;

import com.model.Budget;
import com.model.User;

public interface IUserDAO {
	
	static IUserDAO getInstance() {		
		return DBUserDAO.getInstance();
	}
	
	boolean checkIfUserExists(String username, String email);
	
	boolean checkIfEmailExists(String email);
	
	boolean checkForCorrectUsernameAndPassword(String username, String password);
	
	void addUser(User user, Budget budget);
	
	User getUser(String username);
		
	boolean changePassword(int id, String newPassword);
	
	boolean changeEmail(int id, String newEmail);
}
