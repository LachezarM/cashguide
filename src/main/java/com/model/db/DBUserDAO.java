package com.model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.model.User;

public class DBUserDAO implements IUserDAO{

	private static DBUserDAO instance = null;
	private DBUserDAO(){
		
	}
	
	public static synchronized DBUserDAO getInstance(){
		if(instance==null){
			instance = new DBUserDAO();
		}
		return instance;
	}
	
	@Override
	public boolean checkIfUserExests(String username) {
		String sql = "SELECT username FROM " + DBManager.DB_NAME + ".users WHERE username=?;";
		boolean result = false;
		try(PreparedStatement pr = DBManager.getDBManager().getConnection().prepareStatement(sql)){
			pr.setString(1, username);
			ResultSet rs= pr.executeQuery();
			while(rs.next()){
				String user = rs.getString("username");
				if(user==null){
					result = false;
					break;
				}else{
					result = true;
					break;
				}
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("Error in DB");
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public User addUser(User user) {
		String sql = "INSERT INTO users(username, password, email, first_name, last_name, balanse) "
				+ "VALUES(?,?,?,?,?,?);";
		
		try(PreparedStatement pr = DBManager.getDBManager().
											getConnection().
											prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			pr.setString(1, user.getUsername());
			pr.setString(2, user.getPassword());
			pr.setString(3, user.getEmail());
			pr.setString(4, user.getFirstName());
			pr.setString(5, user.getLastName());
			pr.setDouble(6, user.getBalance());
			
			int affectedRows = pr.executeUpdate();
			
			if(affectedRows == 0){
				throw new SQLException("Creating user failed, no rows affected.");
			}
			
			try (ResultSet generatedKeys = pr.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                user.setId((int)(generatedKeys.getLong(1)));
	            }
	            else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
		
	}

	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changePassword(int id, String newPassword) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeEmail(int id, String newEmail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeUserProfile(int id, User newUser) {
		// TODO Auto-generated method stub
		
	}

}
