package com.model.db;

import java.util.List;

import com.model.Payment;

public interface IPaymentDAO {
	
	static IPaymentDAO getInstance() {
		return DBPaymentDAO.getInstance();
	}	
	List<Payment> getAllPayments(int userId);
	
}
