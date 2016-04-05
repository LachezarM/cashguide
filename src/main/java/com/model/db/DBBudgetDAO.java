package com.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.Budget;
import com.model.Expense;
import com.model.Income;
import com.model.Payment;
import com.model.User;

public class DBBudgetDAO implements IBudgetDAO {

	private static DBBudgetDAO instance = null;
	private static final int BUDGET_START_DATE = 1;

	private DBBudgetDAO() {

	}

	public static synchronized DBBudgetDAO getInstance() {
		if (instance == null) {
			instance = new DBBudgetDAO();
		}
		return instance;
	}

	//OK
	@Override
	public void saveBudget(int userId, Budget budget) {
		String sql = "INSERT INTO "
				+ DBManager.DB_NAME
				+ ".budgets(userId, balance, percentage, date) VALUES(?,?,?,?);";
		double balance = budget.getBalance();
		double percentage = budget.getPercentageOfIncome();
		LocalDate date = budget.getDate();
		java.sql.Date sqlDate = java.sql.Date.valueOf(date);
		
		
		System.out.println("date: " + date);
		System.out.println("sqlDate: " + sqlDate);
		
		try (PreparedStatement pr = DBManager.getDBManager().getConnection()
				.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pr.setInt(1, userId);
			pr.setDouble(2, balance);
			pr.setDouble(3, percentage);
			pr.setDate(4, sqlDate);

			int affectedRows = pr.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException(
						"Creating budget failed, no rows affected.");
			}
			try (ResultSet generatedKeys = pr.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					budget.setId((int) (generatedKeys.getLong(1)));
				} else {
					throw new SQLException(
							"Creating budget failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			System.out.println("Error in DB");
			e.printStackTrace();
		}
	}

	//Not used
	@Override
	public void removeBudget(int budgetId) {
		String sql = "DELETE FROM " + DBManager.DB_NAME
				+ ".budgets WHERE id=?;";

		try (PreparedStatement pr = DBManager.getDBManager().getConnection()
				.prepareStatement(sql)) {
			pr.setInt(1, budgetId);

			pr.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Error with deleting budget");
			e.printStackTrace();
		}
	}
	
	//OK
	//used when percentage is changed
	public void updateBudget(Budget budget){
		String sql = "UPDATE " +DBManager.DB_NAME + ".budgets "
				+ "SET balance=?, percentage=? "
				+ "WHERE id=?;";
		try(PreparedStatement ps = DBManager.getDBManager().getConnection().prepareStatement(sql)){
			ps.setDouble(1, budget.getBalance());
			ps.setDouble(2, budget.getPercentageOfIncome());
			ps.setInt(3, budget.getId());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//not used i think
	@Override
	public boolean changePercentage(int budgetId, double percentage) {
		String sql = "UPDATE " + DBManager.DB_NAME
				+ ".budgets SET percentage=? WHERE id=?;";
		boolean result = false;
		try (PreparedStatement pr = DBManager.getDBManager().getConnection()
				.prepareStatement(sql)) {
			pr.setInt(2, budgetId);
			pr.setDouble(1, percentage);
			int rows = pr.executeUpdate();
			if (rows == 0) {
				result = false;
			} else {
				result = true;
			}
		} catch (SQLException e) {
			System.out.println("Error with changing percentage in budget");
			e.printStackTrace();
		}

		return result;
	}

	//must be refactored
	public Budget getBudget(User user, LocalDate date) {
		String sql = "SELECT budgets.id as id, balance, percentage, budgets.date as date "
				+ "FROM "
				+ DBManager.DB_NAME
				+ ".users "
				+ "JOIN "
				+ DBManager.DB_NAME
				+ ".budgets ON users.id=budgets.userId "
				+ "WHERE users.id=? AND budgets.date=?;";
		Budget budget = null;

		try (PreparedStatement pr = DBManager.getDBManager().getConnection().prepareStatement(sql)) {
			//change date to 1-st day of the say month and year.
			date = LocalDate.of(date.getYear(), date.getMonthValue(), BUDGET_START_DATE);
			java.sql.Date sqlDate = java.sql.Date.valueOf(date);
			pr.setInt(1, user.getId());
			pr.setDate(2, sqlDate);

			try (ResultSet rs = pr.executeQuery()) {
				while (rs.next()) {
					// budget
					int id = rs.getInt("id");
					double percentage = rs.getDouble("Percentage");
					LocalDate budgetDate = rs.getDate("date").toLocalDate();

					budget = new Budget(budgetDate, percentage);
					budget.setId(id);
				}
			}
		} catch (SQLException e) {
			System.out.println("Error with db:getBudget for a user");
			e.printStackTrace();
		}

		return budget;
	}

	//changed on 4.4.16. Returns payments for the specific budget
	//this method must be either renamed or its return type must be changed
	public void getPayments(Budget budget){
		String sql = "SELECT payments.id as paymentId, description, amount, category, type, payments.date as paymentDate "
				+ "FROM "+ DBManager.DB_NAME + ".budgets "
				+ "JOIN "+ DBManager.DB_NAME + ".payments ON budgets.id=payments.budgetId "
				+ "JOIN "+ DBManager.DB_NAME + ".categories ON payments.categoryId=categories.id "
				+ "JOIN "+ DBManager.DB_NAME + ".payment_types ON payment_types.id = categories.typeId "
				+ "WHERE budgets.id=? AND budgets.date=?;";
		try (PreparedStatement pr = DBManager.getDBManager().getConnection()
				.prepareStatement(sql)) {
			java.sql.Date sqlDate = java.sql.Date.valueOf(budget.getDate());
			pr.setInt(1, budget.getId());
			pr.setDate(2, sqlDate);

			try (ResultSet rs = pr.executeQuery()) {
				while (rs.next()) {
					// payments
					int paymentId = rs.getInt("paymentId");
					String category = rs.getString("category");
					String description = rs.getString("description");
					double amount = rs.getDouble("amount");
					LocalDate paymentDate = rs.getDate("paymentDate").toLocalDate();
					String type = rs.getString("type");

					// create payment
					Payment payment = null;

					if (type.equals("INCOME")) {
						payment = new Income(category, description, amount,
								paymentDate);
					}
					if (type.equals("EXPENSE")) {
						payment = new Expense(category, description, amount,
								paymentDate);
					}

					payment.setId(paymentId);

					// add payment to budget
					budget.addPayment(payment);
				}
			}
		} catch (SQLException e) {
			System.out.println("Error with db select budgets");
			e.printStackTrace();
		}
		
	}
	
	//used 
	//tested
	//adds payments and updates budget's balance, because it must be a transaction;
	public void addPayment(Payment payment, Budget budget) {
		
		String sqlInsert = "INSERT INTO " + DBManager.DB_NAME
				+ ".payments(categoryId, description, amount, date, budgetId) "
				+ "VALUES( " + "(SELECT id FROM " + DBManager.DB_NAME
				+ ".categories WHERE category=?),?,?,?,?)";
		
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		//ERRORS WHEN I TRY TO USE updateBudget instead of this query, because of con auto commit
		String sqlUpdate = "UPDATE " + DBManager.DB_NAME+ ".budgets SET balance=? WHERE id=?;";
		//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		int budgetId = budget.getId();
		String category = payment.getCategory();
		String description = payment.getDescription();
		double amount = payment.getAmount();
		LocalDate date = payment.getDate();
		java.sql.Date sqlDate = java.sql.Date.valueOf(date);
		
		Connection con = DBManager.getDBManager().getConnection();
		
		try {
			con.setAutoCommit(false);
			System.out.println("autocommit is false");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try(PreparedStatement ps1 = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS); 
				PreparedStatement ps2 = con.prepareStatement(sqlUpdate)){
			ps1.setString(1, category);
			ps1.setString(2, description);
			ps1.setDouble(3, amount);
			ps1.setDate(4, sqlDate);
			ps1.setInt(5, budgetId);
			
			//ps1.executeUpdate();
			
			int affectedRows = ps1.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException(
						"Adding payment failed, no rows affected.");
			}
			try (ResultSet generatedKeys = ps1.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					payment.setId((int) (generatedKeys.getLong(1)));
				} else {
					throw new SQLException(
							"Adding payment failed, no ID obtained.");
				}
			}
			
			System.out.println("Payment has id: " + payment.toString());
			
			ps2.setDouble(1,budget.getBalance());
			ps2.setInt(2, budgetId);
			
			ps2.executeUpdate();
			
			con.commit();
			System.out.println("changes are commited");
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

	// not tested
	//not sure if used
	@Override
	public List<Budget> getAllBudgets(int userId) {

		String sql = "SELECT budgets.id as id, balance, percentage, budgets.date as date, "
				+ "payments.id as paymentId, description, amount, category, payments.date as paymentDate "
				+ "FROM "
				+ DBManager.DB_NAME
				+ ".users "
				+ "JOIN "
				+ DBManager.DB_NAME
				+ ".budgets ON users.id=budgets.userId "
				+ "JOIN "
				+ DBManager.DB_NAME
				+ ".payments ON budgets.id=paym	ents.budgetId "
				+ "JOIN "
				+ DBManager.DB_NAME
				+ ".categories ON payments.categoryId=categories.id "
				//+ "JOIN "
				//+ DBManager.DB_NAME
				//+ ".payment_types ON payment_types.id = payments.typeId "
				+ "WHERE users.id=?;";
		List<Budget> budgets = new ArrayList<Budget>();
		Budget budget = null;

		try (PreparedStatement pr = DBManager.getDBManager().getConnection()
				.prepareStatement(sql)) {
			pr.setInt(1, userId);
			int lastId = -1;
			try (ResultSet rs = pr.executeQuery()) {
				while (rs.next()) {
					// budget
					int id = rs.getInt("id");
					if (lastId == -1) {
						lastId = id;
					}
					double balance = rs.getDouble("balance");
					double percentage = rs.getDouble("Percentage");
					LocalDate date = rs.getDate("date").toLocalDate();

					// payments
					int paymentId = rs.getInt("paymentId");
					String category = rs.getString("category");
					String description = rs.getString("description");
					double amount = rs.getDouble("amount");
					LocalDate paymentDate = rs.getDate("paymentDate")
							.toLocalDate();
					String type = rs.getString("type");

					// create payment
					Payment payment = null;

					if (type.equals("INCOME")) {
						payment = new Income(category, description, amount,
								paymentDate);
					}
					if (type.equals("EXPENSE")) {
						payment = new Expense(category, description, amount,
								paymentDate);
					}

					payment.setId(paymentId);

					if (budget == null) {
						budget = new Budget(date, percentage);
						budget.setId(id);
						budget.setBalance(balance);
					}

					// all budgets for 1 user
					if (id != lastId) {
						budgets.add(budget);
						budget = new Budget(date, percentage);
						budget.setId(id);
						budget.setBalance(balance);
						lastId = id;
					}

					// add payment to budget
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

	//this method should be changed after another table for categories is made
	public Map<String, ArrayList<String>> getAllCategories() {
		String sql = "SELECT category, type " + "FROM " + DBManager.DB_NAME
				+ ".categories " + "JOIN " + DBManager.DB_NAME
				+ ".payment_types ON typeId=payment_types.id;";
		Map<String, ArrayList<String>> categories = new HashMap<String, ArrayList<String>>();
		categories.put("EXPENSE", new ArrayList<String>());
		categories.put("INCOME", new ArrayList<String>());
		try (Statement ps = DBManager.getDBManager().getConnection()
				.createStatement()) {
			try (ResultSet rs = ps.executeQuery(sql)) {
				while (rs.next()) {
					String category = rs.getString("category");
					String type = rs.getString("type");
					categories.get(type).add(category);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return categories;
	}
}
