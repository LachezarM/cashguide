package com.model;

import java.time.LocalDate;

import com.model.db.DBBudgetDAO;

public class PaymentManager {

	// adds a payment to a budget
	public static void savePayment(User user, Payment payment) {
		Budget budget = user.getBudget();
		LocalDate startDate = budget.getDate();
		LocalDate endDate = budget.getDate().plusMonths(1).minusDays(1);
		System.out.println("StartDate: " + startDate + ", EndDate: " + endDate);

		if ((payment.getDate().compareTo(startDate) >= 0)
				&& (payment.getDate().compareTo(endDate) <= 0)) {
			// add to current budget
			System.out.println("In range");
			budget.addPayment(payment);
			System.out.println("Budget balance to be added in db is: "
					+ budget.getBalance());
			DBBudgetDAO.getInstance().addPayment(payment, budget);
		} else {
			// create new budget or add to old one
			System.out.println("new budget");
			// checks if a budget exists
			Budget budgetForOtherDate = DBBudgetDAO.getInstance().getBudget(
					user, payment.getDate());
			// budget doesn't exist->old or new budget doesn't matter
			if (budgetForOtherDate == null) {
				// create budget
				Budget newBudget = new Budget(payment.getDate(), 1);
				DBBudgetDAO.getInstance().saveBudget(user.getId(), newBudget);
				newBudget.addPayment(payment);
				DBBudgetDAO.getInstance().addPayment(payment, newBudget);
			} else {
				// budget exists->old or new budget
				// add to it
				budgetForOtherDate.addPayment(payment);
				//saves budget to db
				DBBudgetDAO.getInstance().addPayment(payment,budgetForOtherDate);
			}
		}

	}

	public static Payment createPayment(String paymentType, String category, String description,  double amount, LocalDate date){
		Payment payment = null;
		if(paymentType.equalsIgnoreCase("EXPENSE")){
			payment = new Expense(category,description, amount, date);
		}else if(paymentType.equalsIgnoreCase("INCOME")){
			payment = new Income(category, description, amount, date);
		}
		return payment;
	}
}
