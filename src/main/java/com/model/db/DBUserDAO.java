package com.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.model.Budget;
import com.model.User;

public class DBUserDAO implements IUserDAO{

	private static DBUserDAO instance = null;
	//TODO see if this code is ok
	private DBUserDAO()	{
	}
	
	public static synchronized DBUserDAO getInstance(){
		if(instance==null){
			instance = new DBUserDAO();
		}
		return instance;
	}
	
	public boolean checkIfEmailExists(String email){
		String sql = "SELECT email FROM " + DBManager.DB_NAME + ".users WHERE email=?;";
		boolean result = false;
		try(PreparedStatement pr = DBManager.getDBManager().getConnection().prepareStatement(sql)){
			pr.setString(1, email);
			ResultSet rs= pr.executeQuery();
			while(rs.next()){
				String user = rs.getString("email");
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
	public boolean checkIfUserExists(String username, String email) {
		String sql = "SELECT username, email FROM " + DBManager.DB_NAME + ".users WHERE username=? OR email=?;";
		boolean result = false;
		try(PreparedStatement pr = DBManager.getDBManager().getConnection().prepareStatement(sql)){
			pr.setString(1, username);
			pr.setString(2, email);
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

	public boolean checkForCorrectUsernameAndPassword(String username, String password){
		String sql = "SELECT username, password "
				+ "FROM " + DBManager.DB_NAME+ ".users "
				+ "WHERE username=? AND password=?;";
		boolean result = false;
		try(PreparedStatement pr = DBManager.getDBManager().getConnection().prepareStatement(sql)){
			pr.setString(1, username);
			pr.setString(2, password);
			ResultSet rs = pr.executeQuery();
			if(rs == null) {
				result = false;
				return result;
			}
			while(rs.next()){
				String user = rs.getString("username");
				if(user==null){
					result = false;
				}else{
					result=true;
				}
				break;
			}
			rs.close();
			
		} catch (SQLException e) {
			System.out.println("DB couldn't select the user see getUser(String) in DBUserDAO");
			e.printStackTrace();
		}
		
		return result;
	}
	
	@Override
	public void addUser(User user, Budget budget) {

		String sql = "INSERT INTO " + DBManager.DB_NAME + ".users(username, password, email) "
				+ "VALUES(?,?,?);";
		
		Connection con = DBManager.getDBManager().getConnection();
		
		try {
			con.setAutoCommit(false);
			System.out.println("autocommit is false");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try(PreparedStatement pr = DBManager.getDBManager().getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			pr.setString(1, user.getUsername());
			pr.setString(2, user.getPassword());
			pr.setString(3, user.getEmail());
			
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
			
			IBudgetDAO.getInstance().addBudget(user.getId(), budget);
			
			
			con.commit();
			System.out.println("changes are commited");
		} catch (SQLException e) {
			System.out.println("Error inserting user");
			try {
				con.rollback();
				e.printStackTrace();
				System.out.println("rollback");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			try {
				con.setAutoCommit(true);
				System.out.println("autocommit is true");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public User getUser(String username) {
		String sql = "SELECT id, password, email "
				+ "FROM " + DBManager.DB_NAME+ ".users "
				+ "WHERE username=?;";
		User user = null;
		try(PreparedStatement pr = DBManager.getDBManager().getConnection().prepareStatement(sql)){
			pr.setString(1, username);
			ResultSet rs = pr.executeQuery();
			while(rs.next()){
				int id = rs.getInt("id");
				String password = rs.getString("password");
				String email = rs.getString("email");
				user = new User(username,email, password);
				user.setId(id);
				break;
			}
			rs.close();
			
		} catch (SQLException e) {
			System.out.println("DB couldn't select the user see getUser(String) in DBUserDAO");
			e.printStackTrace();
		}
		
		return user;
	}

	@Override
	public boolean changePassword(int id, String newPassword) {
		String sql = "UPDATE "+DBManager.DB_NAME+".users "
				+ "SET password=? "
				+ "WHERE id=?;";
		boolean result = false;
		try(PreparedStatement pr = DBManager.getDBManager().getConnection().prepareStatement(sql)){
			pr.setString(1, newPassword);
			pr.setInt(2, id);
			int rs = pr.executeUpdate();
			if(rs == 0){
				result = false;
			}else{
				result = true;
			}			
		} catch (SQLException e) {
			System.out.println("DB in change password");
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public boolean changeEmail(int id, String newEmail) {
		String sql = "UPDATE "+DBManager.DB_NAME+".users "
				+ "SET email=? "
				+ "WHERE id=?;";
		boolean result = false;
		try(PreparedStatement pr = DBManager.getDBManager().getConnection().prepareStatement(sql)){
			pr.setString(1, newEmail);
			pr.setInt(2, id);
			int rs = pr.executeUpdate();
			if(rs == 0){
				result = false;
			}else{
				result = true;
			}			
		} catch (SQLException e) {
			System.out.println("DB error in changeEmail");
			e.printStackTrace();
		}
		
		return result;
	}
}
