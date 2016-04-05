package com.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.IteratedExpression;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.Payment;
import com.model.User;
import com.model.db.IBudgetDAO;
import com.model.db.IPaymentDAO;
import com.model.db.IUserDAO;

@Controller
public class HistoryController {
	
	@RequestMapping(value="/showOnlyTypes", method = RequestMethod.GET)
	String showOnly(@RequestParam(value = "Show") String choise,HttpSession s,Model m) {
		Map<String, ArrayList<String>> result = IBudgetDAO.getInstance().getAllCategories();
		List<String> categories = new ArrayList();
		User u = (User) s.getAttribute("logedUser");
		List<Payment> payments = IPaymentDAO.getInstance().getAllPayments(u.getId());
		if(choise.equalsIgnoreCase("ALL")) {
			categories.addAll(result.get("EXPENSE"));
			categories.addAll(result.get("INCOME"));
			m.addAttribute("currPayments", payments);
			m.addAttribute("currCategories",categories);
		}else if(choise.equalsIgnoreCase("EXPENSE")) {
			for(Iterator<Payment> itt = payments.iterator();itt.hasNext();) {
				Payment p = itt.next();
				if(p.getType().equalsIgnoreCase("INCOME"))
					itt.remove();
			}
			categories.addAll(result.get("EXPENSE"));
			m.addAttribute("currPayments", payments);
			m.addAttribute("currCategories",categories);

		}else if(choise.equalsIgnoreCase("INCOME")) {
			for(Iterator<Payment> itt = payments.iterator();itt.hasNext();) {
				Payment p = itt.next();
				if(p.getType().equalsIgnoreCase("EXPENSE"))
					itt.remove();
			}
			categories.addAll(result.get("INCOME"));
			m.addAttribute("currPayments", payments);
			m.addAttribute("currCategories",categories);
		}
		return "history";
	}
	@RequestMapping(value="/showOnlyCategories",method=RequestMethod.GET)
	String showOnlyCategories(@RequestParam(value = "categories") String choise,
			HttpSession s,
			Model m) {
		User u = (User) s.getAttribute("logedUser");
		List<Payment> payments = IPaymentDAO.getInstance().getAllPayments(u.getId());
		for(Iterator<Payment> itt = payments.iterator();itt.hasNext();){
			Payment p = itt.next();
			if(!p.getCategory().equalsIgnoreCase(choise))
				itt.remove();
		}
		m.addAttribute("currPayments",payments);
		return "history";
	}
}
