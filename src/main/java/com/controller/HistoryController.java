package com.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.model.Utils;
import com.model.db.IBudgetDAO;
import com.model.db.IPaymentDAO;


@Controller
public class HistoryController {
	String showGlobal = "All";
	String categoriesGlobal = null;
	@RequestMapping(value="/showOnlyTypes", method = RequestMethod.GET)
	String showOnly(@RequestParam(value = "Show") String choise,HttpSession s,
			Model m) {
		showGlobal = choise;
		User u = (User) s.getAttribute("logedUser");
		List<Payment> payments = IPaymentDAO.getInstance().getAllPayments(u.getId());
		Map<String, ArrayList<String>> result = IBudgetDAO.getInstance().getAllCategories(u.getId());
		List<String> categories = generateCategoriesByType(choise,result,payments);
		//generates categories by type and modyfies payments to be only (all,expense or income)
		m.addAttribute("currPayments", payments);
		m.addAttribute("currCategories",categories);
		s.setAttribute("currCategoriesSession", categories);
		Utils.logger.info("showOnlyTypes link was clicked");
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
		Utils.logger.info("showOnlyCategories link was clicked");
		return "history";
	}
	
	@RequestMapping(value="/showByDate", method = RequestMethod.GET)
	String showByDate(@RequestParam(value = "date") String  date,
			HttpSession s,
			Model m) {
		User u = (User) s.getAttribute("logedUser");
		List<Payment> payments = IPaymentDAO.getInstance().getAllPayments(u.getId());
		modifyByDate(date,payments);
		m.addAttribute("currCategories",s.getAttribute("currCategoriesSession"));
		m.addAttribute("currPayments",payments);
		Utils.logger.info("showByDate link was clicked");
		return "history";
		
}
	@RequestMapping(value="/sortByDate" ,method = RequestMethod.GET)
	String sortByDate(Model m,HttpSession s) {
		User u = (User) s.getAttribute("logedUser");
		List<Payment> payments = IPaymentDAO.getInstance().getAllPayments(u.getId());
		if(!showGlobal.equalsIgnoreCase("ALL"))
			modifyByType(showGlobal,payments);
		Collections.sort(payments, new Comparator<Payment>() {

			@Override
			public int compare(Payment o1, Payment o2) {
				if(o1.getDate().isAfter(o2.getDate()))
					return 1;
				return -1;
			}
		});
		
		m.addAttribute("currCategories",s.getAttribute("currCategoriesSession"));
		m.addAttribute("currPayments",payments);
		Utils.logger.info("sortByDate link was clicked");
		return "history";
		
	}
	
	private void modifyByType(String showGlobal2, List<Payment> payments) {
		for(Iterator<Payment> itt = payments.iterator();itt.hasNext();){
			Payment p = itt.next();
			if(!p.getType().equalsIgnoreCase(showGlobal2)) 
				itt.remove();
		}
	}

	public static void modifyByDate(String date, List<Payment> payments) {
		LocalDate dateJava;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		dateJava = LocalDate.parse(date, formatter);
		for(Iterator<Payment> itt = payments.iterator();itt.hasNext();) {
			Payment p = itt.next();
			if(!p.getDate().equals(dateJava)){
				itt.remove();
			}
		}
	}
}
