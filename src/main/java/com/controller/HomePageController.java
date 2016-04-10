package com.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.naming.directory.InvalidAttributesException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.LayeredHighlighter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.model.Payment;
import com.model.User;
import com.model.db.DBBudgetDAO;
import com.model.db.DBPaymentDAO;
import com.model.db.IBudgetDAO;
import com.model.db.IPaymentDAO;

import ch.qos.logback.core.net.SyslogOutputStream;
import scala.annotation.meta.companionClass;

@Controller
public class HomePageController {
	
	static User currentUser = null;

	
	@RequestMapping(value="/add" , method = RequestMethod.GET)
	String add(Model model, HttpSession session) {
		User user = (User)session.getAttribute("logedUser");
		/*Map<String, ArrayList<String>> result = DBBudgetDAO.getInstance().getAllCategories(user.getId());
		JsonObject object = new JsonObject();
		for(String type:result.keySet()){
			JsonArray categories = new JsonArray();
			for(String category:result.get(type)){
				categories.add(category);
			}
			object.add(type, categories);
		}*/
	
		JsonObject object = DBPaymentDAO.getInstance().getCategoriesJSON(user.getId());

		// ArrayList<String> categories =
		// DBBudgetDAO.getInstance().getCustomCategories(user.getId());

		model.addAttribute("panel", "payment");
		model.addAttribute("categories", object);
		// model.addAttribute("customCategories", categories);
		session.setAttribute("categories", object);
		return "add";
	}

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	String showHistory(HttpSession s, Model m) {
		currentUser = (User) s.getAttribute("logedUser");
		List<Payment> payments = IPaymentDAO.getInstance().getAllPayments(currentUser.getId());
		Map<String, ArrayList<String>> result = DBBudgetDAO.getInstance().getAllCategories(currentUser.getId());
		List<String> categories = new ArrayList<String>();
		categories.addAll(result.get("EXPENSE"));
		categories.addAll(result.get("INCOME"));
		m.addAttribute("currPayments", payments);
		m.addAttribute("currCategories", categories);
		return "history";
	}

	


	@RequestMapping(value="/shopping" , method = RequestMethod.GET)
	String shopingList() {
		return 	"index";	
	}
	
	@RequestMapping(value="/simulator" , method = RequestMethod.GET)
	String simulator() {
		return 	"simulator";	
	}

	@RequestMapping(value = "/userProfile", method = RequestMethod.GET)
	String userProfile(HttpSession s) {

		s.removeAttribute("changePassword");
		s.removeAttribute("changeUsername");
		s.removeAttribute("addBalance");
		return "UserProfile";
	}
}
