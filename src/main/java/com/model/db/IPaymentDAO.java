package com.model.db;

import java.util.List;

import com.google.gson.JsonObject;
import com.model.Payment;
import com.model.User;

public interface IPaymentDAO {
	
	static IPaymentDAO getInstance() {
		return DBPaymentDAO.getInstance();
	}
	//OK
	List<Payment> getAllPayments(int userId);
	//OK
	void deletePayment(int paymentId);
	//OK
	void addNewCategory(String category, String type, User user);
	//OK
	void deleteCategory(String category, int userId);
	//OK
	JsonObject getCategoriesJSON(int userId);
	
}
