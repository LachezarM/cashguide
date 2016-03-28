package com.model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.model.Budget;
import com.model.Expense;
import com.model.Income;
import com.model.Payment;

public class DBBudgetDAO implements IBudgetDAO{
	
private static DBBudgetDAO instance = null;
	
	private DBBudgetDAO(){
		
	}
	
	public static synchronized DBBudgetDAO getInstance(){
		if(instance==null){
			instance = new DBBudgetDAO();
		}
		return instance;
	}

	@Override
	public void addBudget(int userId, Budget budget) {
		String sql = "INSERT INTO "+DBManager.DB_NAME+".budgets(userId, balance, percentage, date) VALUES(?,?,?,?);";
		double balance = budget.getBalance();
		double percentage = budget.getPercentageOfIncome();
		LocalDate date = budget.getDate();
		java.sql.Date sqlDate = java.sql.Date.valueOf(date);
		System.out.println("date: " + date);
		System.out.println("sqlDate: " + sqlDate);
		
		try(PreparedStatement pr = DBManager.getDBManager().
											getConnection().
											prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
			pr.setInt(1, userId);
			pr.setDouble(2, balance);
			pr.setDouble(3, percentage);
			pr.setDate(4, sqlDate);
			
			int affectedRows = pr.executeUpdate();
			
			if(affectedRows == 0){
				throw new SQLException("Creating budget failed, no rows affected.");
			}
			try (ResultSet generatedKeys = pr.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                budget.setId((int)(generatedKeys.getLong(1)));
	            }
	            else {
	                throw new SQLException("Creating budget failed, no ID obtained.");
	            }
	        }
		} catch (SQLException e) {
			System.out.println("Error in DB");
			e.printStackTrace();
		}		
	}

	@Override
	public void removeBudget(int budgetId) {
		String sql = "DELETE FROM "+DBManager.DB_NAME+".budgets WHERE id=?;";
		
		try(PreparedStatement pr = DBManager.getDBManager().
				getConnection().
				prepareStatement(sql)){
				pr.setInt(1, budgetId);
				
				pr.executeUpdate();
				
		} catch (SQLException e) {
			System.out.println("Error with deleting budget");
			e.printStackTrace();
		}
				
	}

	@Override
	public boolean changePercentage(int budgetId, double percentage) {
		String sql="UPDATE " +DBManager.DB_NAME+".budgets SET percentage=? WHERE id=?;";
		boolean result = false;
		try(PreparedStatement pr = DBManager.getDBManager().
				getConnection().
				prepareStatement(sql)){
			pr.setInt(2, budgetId);
			pr.setDouble(1, percentage);
			int rows = pr.executeUpdate();
			if(rows==0){
				result = false;
			}else{
				result = true;
			}
		} catch (SQLException e) {
			System.out.println("Error with changing percentage in budget");
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Budget getBudget(int userId) {
		String sql = "SELECT budgets.id as id, balance, percentage, budgets.date as date, "
				+ "payments.id as paymentId, description, amount, category, type, payments.date as paymentDate "
				+ "FROM "+DBManager.DB_NAME+".users "
				+ "JOIN "+DBManager.DB_NAME+".budgets ON users.id=budgets.userId "
				+ "JOIN "+DBManager.DB_NAME+".payments ON budgets.id=payments.budgetId "
				+ "JOIN "+DBManager.DB_NAME+".categories ON payments.categoryId=categories.id "
				+ "JOIN "+DBManager.DB_NAME+".payment_types ON payment_types.id = payments.typeId "
				+ "WHERE users.id=? AND MONTH(budgets.date)=MONTH(CURDATE());";
		Budget budget = null;

		try(PreparedStatement pr = DBManager.getDBManager().getConnection().prepareStatement(sql)){
			pr.setInt(1, userId);
			
			try(ResultSet rs = pr.executeQuery()){
				while(rs.next()){
					//budget
					int id = rs.getInt("id");
					double balance = rs.getDouble("balance");
					double percentage = rs.getDouble("Percentage");
					LocalDate date = rs.getDate("date").toLocalDate();
					
					//payments
					int paymentId = rs.getInt("paymentId");
					String category = rs.getString("category");
					String description = rs.getString("description");
					double amount = rs.getDouble("amount");
					LocalDate paymentDate = rs.getDate("paymentDate").toLocalDate();
					String type = rs.getString("type");
					
					//create payment
					Payment payment = null;
				
					if(type.equals("INCOME")){
						payment = new Income(category, description, amount, paymentDate);
					}
					if(type.equals("EXPENSE")){
						payment = new Expense(category,description, amount, paymentDate);
					}
					
					payment.setId(paymentId);
					
					if(budget==null){
						budget = new Budget(date,percentage);
						budget.setId(id);
						budget.setBalance(balance);
					}		
					//add payment to budget
					budget.addPayment(payment);
				}
			}			
		} catch (SQLException e) {
			System.out.println("Error with db select budgets");
			e.printStackTrace();
		}
		
		
		return budget;
	}

	@Override
	public void addIncome(Income income, int budgetId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addExpense(Expense expense, int budgetId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeIncome(int incomeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeExpense(int expenseId) {
		// TODO Auto-generated method stub
		
	}

	//not tested
	@Override
	public List<Budget> getAllBudgets(int userId) {

		String sql = "SELECT budgets.id as id, balance, percentage, budgets.date as date, "
				+ "payments.id as paymentId, description, amount, category, type, payments.date as paymentDate "
				+ "FROM "+DBManager.DB_NAME+".users "
				+ "JOIN "+DBManager.DB_NAME+".budgets ON users.id=budgets.userId "
				+ "JOIN "+DBManager.DB_NAME+".payments ON budgets.id=payments.budgetId "
				+ "JOIN "+DBManager.DB_NAME+".categories ON payments.categoryId=categories.id "
				+ "JOIN "+DBManager.DB_NAME+".payment_types ON payment_types.id = payments.typeId "
				+ "WHERE users.id=?;";
		List<Budget> budgets = new ArrayList<Budget>();
		Budget budget = null;

		try(PreparedStatement pr = DBManager.getDBManager().getConnection().prepareStatement(sql)){
			pr.setInt(1, userId);
			int lastId = -1;
			try(ResultSet rs = pr.executeQuery()){
				while(rs.next()){
					//budget
					int id = rs.getInt("id");
					if(lastId == -1){
						lastId=id;
					}
					double balance = rs.getDouble("balance");
					double percentage = rs.getDouble("Percentage");
					LocalDate date = rs.getDate("date").toLocalDate();
					
					//payments
					int paymentId = rs.getInt("paymentId");
					String category = rs.getString("category");
					String description = rs.getString("description");
					double amount = rs.getDouble("amount");
					LocalDate paymentDate = rs.getDate("paymentDate").toLocalDate();
					String type = rs.getString("type");
					
					//create payment
					Payment payment = null;
				
					if(type.equals("INCOME")){
						payment = new Income(category, description, amount, paymentDate);
					}
					if(type.equals("EXPENSE")){
						payment = new Expense(category,description, amount, paymentDate);
					}
					
					payment.setId(paymentId);
					
					if(budget==null){
						budget = new Budget(date,percentage);
						budget.setId(id);
						budget.setBalance(balance);
					}
					
					//all budgets for 1 user
					if(id != lastId){
						budgets.add(budget);
						budget = new Budget(date,percentage);
						budget.setId(id);
						budget.setBalance(balance);
						lastId = id;
					}
					
					//add payment to budget
					budget.addPayment(payment);
				}
				
				budgets.add(budget);
			}			
		} catch (SQLException e) {
			System.out.println("Error with db select budgets");
			e.printStackTrace();
		}
		
		
		return budgets;
		
	}

	
}
