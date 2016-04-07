package com.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.model.Expense;
import com.model.Income;
import com.model.Payment;
import com.model.User;

public class DBPaymentDAO implements IPaymentDAO{
	
	private static DBPaymentDAO instance = null;
	
	private DBPaymentDAO(){
		
	}
	
	public static synchronized DBPaymentDAO getInstance(){
		if(instance==null){
			instance = new DBPaymentDAO();
		}
		return instance;
	}

	@Override
	public List<Payment> getAllPayments(int userId) {
		String query = "SELECT budgets.id,userId,payments.date,description,amount,categoryId,category,payment_types.type,payments.id"
					+  " FROM cashguidedb.budgets"
					+  " JOIN cashguidedb.payments ON (budgets.id = payments.budgetId)"
					+  " JOIN cashguidedb.categories on (payments.categoryId = categories.id)"
					+  " JOIN cashguidedb.payment_types on (payment_types.id = categories.typeId)"
					+  " WHERE userId = ?";
		List<Payment> payments = new ArrayList<Payment>();
		try(PreparedStatement pst = DBManager.getDBManager().getConnection().prepareStatement(query)) {
			pst.setInt(1, userId);
			ResultSet rs = pst.executeQuery();
			if(rs == null) {
				throw new SQLException("ResultSet in getAllPayments is empty");
			}
			while(rs.next()) {
				Payment p;
				if(rs.getString("type").equalsIgnoreCase("INCOME")) {
					 p = new Income(rs.getString("category"),rs.getString("description"),rs.getDouble("amount"),
						rs.getDate("date").toLocalDate());
					p.setId(rs.getInt("payments.id"));
				}
				else {
					p = new Expense(rs.getString("category"), rs.getString("description"), rs.getDouble("amount"),
							rs.getDate("date").toLocalDate());

					p.setId(rs.getInt("payments.id"));
				}
				payments.add(p);
			}
		} catch (SQLException e) {
				System.out.println("error getting payments from db" + e.getMessage());
		}
		return payments;
	}

	public void addNewCategory(String category, String type, User user){
		String sqlSelect = "SELECT id FROM "+DBManager.DB_NAME+".categories "
				+ "WHERE category=?";
 		String sqlInsert1 = "INSERT INTO "+DBManager.DB_NAME+".categories(category, isDefault, typeId) VALUES(?, FALSE ,"
				+ "(SELECT id FROM "+DBManager.DB_NAME+".payment_types WHERE type=?));";
		//get generated keys
		String sqlInsert2 = "INSERT INTO "+DBManager.DB_NAME+".customCategories(userId, categoryId) VALUES(?,?)";
		
		Connection con = DBManager.getDBManager().getConnection();
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try(PreparedStatement ps1 = con.prepareStatement(sqlSelect); 
				PreparedStatement ps2 = con.prepareStatement(sqlInsert1, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement ps3 = con.prepareStatement(sqlInsert2);){
			
			ps1.setString(1, category);
			ResultSet rs = ps1.executeQuery();
			
			
			//category doesn't exist
			int id = 0;
			
			boolean result = rs.next();
			if(!result){
				ps2.setString(1, category);
				ps2.setString(2, type);
				int affectedRows = ps2.executeUpdate();
				
				if(affectedRows>0){
					try(ResultSet generatedKeys = ps2.getGeneratedKeys()){
						if (generatedKeys.next()) {
							id = (int) (generatedKeys.getLong(1));
						}
					}
				}
			}else{
				id = rs.getInt("id");
			}
			ps3.setInt(1, user.getId());
			ps3.setInt(2, id);
			
			ps3.executeUpdate();
			
			con.commit();
			
		} catch (SQLException e) {
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

	public void deleteCategory(String category, int id){
		String sql = "DELETE FROM "+DBManager.DB_NAME+".customCategories "
				+ "WHERE userId=? AND categoryId=(SELECT categories.id FROM "+DBManager.DB_NAME+".categories WHERE category=?)";
		try(PreparedStatement ps = DBManager.getDBManager().getConnection().prepareStatement(sql)){
			ps.setInt(1, id);
			ps.setString(2, category);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
