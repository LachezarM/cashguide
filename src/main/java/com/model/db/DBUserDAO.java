package com.model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.model.User;

public class DBUserDAO implements IUserDAO{

	private static DBUserDAO instance = null;
	
	private DBUserDAO(){
		/*try(Statement st = DBManager.getConnection().createStatement();) {
			//st = DBManager.getDBManager().getConnection().createStatement();
			String query = "USE " + DBManager.DB_NAME + ";";
			st.executeUpdate(query);
			query = " CREATE TABLE IF NOT EXISTS users ("
					+ "ID int auto_increment,"
					+ "password varchar(20),"
					+ "username varchar(20),"
					+ "email varchar(20),"
					+ "PRIMARY KEY(ID));";
			st.executeUpdate(query);
			st.close();
		} catch (SQLException e) {
			System.out.println("Error creating table users " + e.getMessage());
		}*/
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
	public void addUser(User user) {

		String sql = "INSERT INTO " + DBManager.DB_NAME + ".users(username, password, email) "
				+ "VALUES(?,?,?);";
		
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
		} catch (SQLException e) {
			System.out.println("Error inserting User" + e.getMessage());
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

	/*@Override
	public List<User> getAllUsers() {
		String sql = "SELECT id, username, password, email"
				+ " FROM " + DBManager.DB_NAME + ".users;";
		List<User> users = new LinkedList<User>();
		try(Statement st = DBManager.getConnection().createStatement()){
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				//System.out.println(rs.getString("username") + " : "  + rs.getInt("id"));
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
		
		return Collections.unmodifiableList(users);
	}*/

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

	/*@Override
	public boolean changeUserProfile(int id,String newUsername) {
		String sql = "UPDATE "+DBManager.DB_NAME+".users "
				+ "SET username=? "
				+ "WHERE id=?;";
		boolean result = false;
		try(PreparedStatement pr = DBManager.getConnection().prepareStatement(sql)){
			pr.setString(1, newUsername);
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
		
	}*/

	//-------------------------------------------------------------------------------------------------
	/*@Override
	public boolean checkIfPasswordExists(String newPassword) {
		String sql = "SELECT password FROM " + DBManager.DB_NAME + ".users WHERE password=?;";
		boolean result = false;
		try(PreparedStatement pr = DBManager.getConnection().prepareStatement(sql)){
			pr.setString(1, newPassword);
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
	}*/
}
