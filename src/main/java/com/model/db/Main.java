package com.model.db;

import java.awt.image.DataBufferByte;
import java.time.LocalDate;

import com.model.Budget;

public class Main {
	public static void main(String[] args) {
		DBManager x = DBManager.getDBManager().getDBManager();
		//DBBudgetDAO.getInstance().addBudget(1, new Budget(LocalDate.now(), 0.7));
		//DBBudgetDAO.getInstance().removeBudget(2);
		//DBBudgetDAO.getInstance().changePercentage(1, 0.9);
		System.out.println(DBBudgetDAO.getInstance().getBudget(1).getPercentageOfIncome());
		System.out.println(DBBudgetDAO.getInstance().getBudget(1).getBalance());
		System.out.println(DBBudgetDAO.getInstance().getBudget(1).getDate());
		System.out.println(DBBudgetDAO.getInstance().getBudget(1).getPayments().get("INCOME"));
		System.out.println(DBBudgetDAO.getInstance().getBudget(1).getPayments().get("EXPENSE"));
	}
}
