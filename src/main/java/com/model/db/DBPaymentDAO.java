package com.model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.model.Expense;
import com.model.Income;
import com.model.Payment;

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
	public void addPayment(Payment payment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePayment(Payment payment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Payment> getAllIncomes(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payment> getAllExpenses(int userId) {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public List<Payment> getAllPaymentsForPeriod(int userId,
			LocalDate startingDate, Period period) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payment> getAllIncomesByCategory(int userId, String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payment> getAllExpensesByCategory(int userId, String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payment> getAllIncomesForSpecificMonth(int month) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Payment> getAllExpensesForSpecificMonth(int month) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
