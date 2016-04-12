package com.model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.model.Budget;
import com.model.Expense;
import com.model.Income;
import com.model.Payment;
import com.model.User;
import com.model.UserManager;

public class DBBudgetDAO implements IBudgetDAO {

	private static DBBudgetDAO instance = null;

	private DBBudgetDAO() {

	}

	public static synchronized DBBudgetDAO getInstance() {
		if (instance == null) {
			instance = new DBBudgetDAO();
		}
		return instance;
	}

	@Override
	public void addBudget(int userId, Budget budget) {
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
			date = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
			java.sql.Date sqlDate = java.sql.Date.valueOf(date);
			pr.setInt(1, user.getId());
			pr.setDate(2, sqlDate);

			try (ResultSet rs = pr.executeQuery()) {
				while (rs.next()) {
					// budget
					int id = rs.getInt("id");
					//double balance = rs.getDouble("balance");
					double percentage = rs.getDouble("Percentage");
					LocalDate budgetDate = rs.getDate("date").toLocalDate();

					budget = new Budget(budgetDate, percentage);
					budget.setId(id);
					//budget.setBalance(balance);
					//the budget will be 0 and it'll be calculated with addPayment in Budget
				}
			}
		} catch (SQLException e) {
			System.out.println("Error with db:getBudget for a user");
			e.printStackTrace();
		}

		return budget;
	}

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

					
					UserManager.updateBudget(budget, payment);
					// add payment to budget
					//budget.addPayment(payment);
				}
			}
		} catch (SQLException e) {
			System.out.println("Error with db select budgets");
			e.printStackTrace();
		}
		
	}
	public Budget getBudgetAndPayments(int userId, LocalDate date) {
		String sql = "SELECT budgets.id as id, balance, percentage, budgets.date as date, "
				+ "payments.id as paymentId, description, amount, category, type, payments.date as paymentDate "
				+ "FROM " + DBManager.DB_NAME + ".users "
				+ "JOIN "+ DBManager.DB_NAME+ ".budgets ON users.id=budgets.userId "
				+ "JOIN "+ DBManager.DB_NAME+ ".payments ON budgets.id=payments.budgetId "
				+ "JOIN "+ DBManager.DB_NAME+ ".categories ON payments.categoryId=categories.id "
				+ "JOIN "+ DBManager.DB_NAME+ ".payment_types ON payment_types.id = categories.typeId "
				+ "WHERE users.id=? AND budgets.date=?;";
		Budget budget = null;
		double balance = 0;
		try (PreparedStatement pr = DBManager.getDBManager().getConnection().prepareStatement(sql)) {
			java.sql.Date sqlDate = java.sql.Date.valueOf(date);
			pr.setInt(1, userId);
			pr.setDate(2, sqlDate);
			
			try (ResultSet rs = pr.executeQuery()) {
				while (rs.next()) {
					// budget
					int id = rs.getInt("id");
					balance = rs.getDouble("balance");
					double percentage = rs.getDouble("Percentage");
					LocalDate budgetDate = rs.getDate("date").toLocalDate();

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

					if (type.equals(Payment.INCOME)) {
						payment = new Income(category, description, amount,
								paymentDate);
					}
					if (type.equals(Payment.EXPENSE)) {
						payment = new Expense(category, description, amount,
								paymentDate);
					}

					payment.setId(paymentId);

					if (budget == null) {
						budget = new Budget(budgetDate, percentage);
						budget.setId(id);
						budget.setBalance(balance);
					}
					// add payment to budget
					///budget.addPayment(payment);
					UserManager.updateBudget(budget, payment);
				}
				if(budget!=null){
					budget.setBalance(balance);
					//UserManager.updateBudget(budget, payment);
				}
			}
			
		} catch (SQLException e) {
			System.out.println("Error with db select budgets");
			e.printStackTrace();
		}
		
		return budget;

	}
	public void addPayment(Payment payment, Budget budget) {
			
			String sqlInsert = "INSERT INTO " + DBManager.DB_NAME
					+ ".payments(categoryId, description, amount, date, budgetId) "
					+ "VALUES( " + "(SELECT id FROM " + DBManager.DB_NAME
					+ ".categories WHERE category=?),?,?,?,?)";
			
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
			
			try(PreparedStatement ps1 = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);){
				ps1.setString(1, category);
				ps1.setString(2, description);
				ps1.setDouble(3, amount);
				ps1.setDate(4, sqlDate);
				ps1.setInt(5, budgetId);
				
				
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

				//UPDATE USAGE
				updateBudget(budget);
				
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
}
