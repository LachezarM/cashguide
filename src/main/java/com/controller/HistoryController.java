package com.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.model.Payment;
import com.model.User;
import com.model.db.IBudgetDAO;
import com.model.db.IPaymentDAO;


@Controller
public class HistoryController {
	String showGlobal = "All";
	String categoriesGlobal = null;
	@RequestMapping(value="/showOnlyTypes", method = RequestMethod.GET)
	String showOnly(@RequestParam(value = "Show") String choise,HttpSession s,
			Model m) {
		User user = (User)s.getAttribute("logedUser");
		Map<String, ArrayList<String>> result = IBudgetDAO.getInstance().getAllCategories(user.getId());
		showGlobal = choise;
		List<Payment> payments = IPaymentDAO.getInstance().getAllPayments(user.getId());
		List<String> categories = generateCategoriesByType(choise,result,payments);
		//generates categories by type and modyfies payments to be only (all,expense or income)
		m.addAttribute("currPayments", payments);
		m.addAttribute("currCategories",categories);
		s.setAttribute("currCategoriesSession", categories);
		return "history";
	}
	
	private List<String> generateCategoriesByType(String choise, Map<String, ArrayList<String>> result,
			List<Payment> payments) {
		List<String> categories = new ArrayList<String>();
		if(choise.equalsIgnoreCase("ALL")) {
			categories.addAll(result.get("EXPENSE"));
			categories.addAll(result.get("INCOME"));
		}else if(choise.equalsIgnoreCase("EXPENSE")) {
			for(Iterator<Payment> itt = payments.iterator();itt.hasNext();) {
				Payment p = itt.next();
				if(p.getType().equalsIgnoreCase("INCOME"))
					itt.remove();
			}
			categories.addAll(result.get("EXPENSE"));
		}
		else if(choise.equalsIgnoreCase("INCOME")) {
			for(Iterator<Payment> itt = payments.iterator();itt.hasNext();) {
				Payment p = itt.next();
				if(p.getType().equalsIgnoreCase("EXPENSE"))
					itt.remove();
			}
			categories.addAll(result.get("INCOME"));
	}
		return categories;
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
		m.addAttribute("currCategories",s.getAttribute("currCategoriesSession"));
		m.addAttribute("currPayments",payments);
		return "history";
	}
	
	@RequestMapping(value="/showByDate", method = RequestMethod.POST)
	String showByDate(@RequestParam(value = "date") String  date) {
		System.out.println(date);
		return "history";
}
}
