package com.model.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.model.Payment;
import com.model.User;

public interface IPaymentDAO {
	
	static IPaymentDAO getInstance() {
		return DBPaymentDAO.getInstance();
	}
	
	List<Payment> getAllPayments(int userId);
	
	void deletePayment(int paymentId);
	
	void addNewCategory(String category, String type, User user);
	
	void deleteCategory(String category, int userId);

	JsonObject getCategoriesJSON(int userId);
	
	ArrayList<String> getCustomCategories(int id);
	
	Map<String, ArrayList<String>> getAllCategories(int id);
	
}
