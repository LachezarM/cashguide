package com.model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
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

	public boolean checkForCorrectPassword(String username, String password){
		String sql = "SELECT username, password "
				+ "FROM " + DBManager.DB_NAME+ ".users "
				+ "WHERE username=? AND password=?;";
		boolean result = false;
		try(PreparedStatement pr = DBManager.getDBManager().getConnection().prepareStatement(sql)){
			pr.setString(1, username);
			pr.setString(2, password);
			ResultSet rs = pr.executeQuery();
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
	public void addUser(User user) {
		/*String sql = "INSERT INTO users(username, password, email, first_name, last_name) "
				+ "VALUES(?,?,?,?,?);";*/
		String sql = "INSERT INTO users(username, password, email) "
				+ "VALUES(?,?,?,);";
		
		try(PreparedStatement pr = DBManager.getDBManager().
											getConnection().
											prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			pr.setString(1, user.getUsername());
			pr.setString(2, user.getPassword());
			pr.setString(3, user.getEmail());
			//pr.setString(4, user.getFirstName());
			//pr.setString(5, user.getLastName());
			
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
	}

	@Override
	public User getUser(String username) {
		String sql = "SELECT id, password, email, firstName, lastName "
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
				//String firstName = rs.getString("firstName");
				//String lastName = rs.getString("lastName");
				//user = new User(username,email, password, firstName, lastName);
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
	public List<User> getAllUsers() {
		String sql = "SELECT id, username, password, email, firstName, lastName "
				+ "FROM " + DBManager.DB_NAME+ ".users;";
		List<User> users = new LinkedList<User>();
		try(Statement st = DBManager.getDBManager().getConnection().createStatement()){
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String email = rs.getString("email");
				//String firstName = rs.getString("firstName");
				//String lastName = rs.getString("lastName");
				//User user = new User(username,email, password, firstName, lastName);
				User user = new User(username,email, password);
				user.setId(id);
				users.add(user);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("DB couldn't select the users see getAllUser() in DBUserDAO");
			e.printStackTrace();
		}
		
		return users;
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

	@Override
	public boolean changeUserProfile(int id, User newUser) {
		//
		return false;
		
	}

}
