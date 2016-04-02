package com.model.db;

import java.time.LocalDate;

import com.model.Budget;
import com.model.Expense;
import com.model.Income;
import com.model.Payment;
import com.model.User;
import com.model.UserManager;

public class Main {
	public static void main(String[] args) {
		//DBBudgetDAO.getInstance().addBudget(1, new Budget(LocalDate.now(), 0.7));
		//DBBudgetDAO.getInstance().removeBudget(2);
		//DBBudgetDAO.getInstance().changePercentage(1, 0.9);
		/*System.out.println(DBBudgetDAO.getInstance().getBudget(1).getPercentageOfIncome());
		System.out.println(DBBudgetDAO.getInstance().getBudget(1).getBalance());
		System.out.println(DBBudgetDAO.getInstance().getBudget(1).getDate());
		System.out.println(DBBudgetDAO.getInstance().getBudget(1).getPayments().get("INCOME"));
		System.out.println(DBBudgetDAO.getInstance().getBudget(1).getPayments().get("EXPENSE"));*/
		
		//User user = UserManager.createUserAfterRegister("dimitar", "123456A", "dimitar@abv.bg");
		User user = UserManager.createUserAfterLogin("dimitar", "123456A");
		System.out.println(user);
		
		Payment salary1 = new Income("salary", "myTestSalary", 1000, LocalDate.now());
		Payment food1 = new Expense("food", "myTestFood", 1000, LocalDate.now());
		Payment salary2 = new Income("salary", "myTestSalary", 1000, LocalDate.now().plusMonths(1));
		Payment food2 = new Expense("food", "myTestFood", 100, LocalDate.now().minusMonths(1));
		Payment transport = new Expense("transport", "MyTrasportTest", 50, LocalDate.now().minusMonths(1));
		UserManager.addPayment(user, salary1);
		UserManager.addPayment(user, food1);
		UserManager.addPayment(user, salary2);
		UserManager.addPayment(user, food2);
		UserManager.addPayment(user, transport);
		
		
		
		
	}
}
