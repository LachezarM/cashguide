package com.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
import com.model.Utils;

@Controller
public class HistoryController {
	String showGlobal = "All";
	int ifAsc = 1;

	@RequestMapping(value = "/showOnlyTypes", method = RequestMethod.GET)
	String showOnly(@RequestParam(value = "Show") String type,
			HttpSession session, Model model) {

		showGlobal = type;
		if (!session.isNew()) {
			List<Payment> allPayments = (List<Payment>) session
					.getAttribute("AllPaymentsSession");
			List<String> currCategories = HomePageController
					.generateCategoriesByType(type,
							(Map<String, ArrayList<String>>) session
									.getAttribute("AllCategoriesSession"));
			// generates categories by type
			System.out.println(allPayments.size());
			System.out.println(currCategories.size());
			if (!type.equalsIgnoreCase(HomePageController.ALL))
				allPayments = getPaymentsByType(type, allPayments);
			// modyfies payments to be only (all,expense or income)
			model.addAttribute("currPayments", allPayments);
			model.addAttribute("currCategories", currCategories);
			model.addAttribute("totalAmount",
					HomePageController.total(allPayments));
			Utils.logger.info("showOnlyTypes link was clicked");
			return "history";
		} else {
			return "index";
		}
	}

	private List<Payment> getPaymentByCategory(String category,
			List<Payment> payments) {
		List<Payment> result = new ArrayList<>();
		result.addAll(payments);
		for (Iterator<Payment> itt = result.iterator(); itt.hasNext();) {
			Payment payment = itt.next();
			if (!payment.getCategory().equalsIgnoreCase(category)) {
				itt.remove();
			}
		}
		return result;
	}

	private List<Payment> getPaymentsByType(String type, List<Payment> payments) {
		List<Payment> result = new ArrayList<>();
		result.addAll(payments);
		for (Iterator<Payment> itt = result.iterator(); itt.hasNext();) {
			Payment payment = itt.next();
			if (!payment.getType().equalsIgnoreCase(type)) {
				itt.remove();
			}
		}
		return result;
	}

	@RequestMapping(value = "/showOnlyCategories", method = RequestMethod.GET)
	String showOnlyCategories(
			@RequestParam(value = "categories") String category,
			HttpSession session, Model model) {
		String type = showGlobal;
		if (!session.isNew()) {
			List<Payment> allPayments = (List<Payment>) session
					.getAttribute("AllPaymentsSession");
			List<Payment> currPayments = getPaymentByCategory(category,
					allPayments);
			List<String> currCategories = HomePageController
					.generateCategoriesByType(type,
							(Map<String, ArrayList<String>>) session
									.getAttribute("AllCategoriesSession"));
			model.addAttribute("currCategories", currCategories);
			model.addAttribute("currPayments", currPayments);
			model.addAttribute("totalAmount",
					HomePageController.total(currPayments));
			Utils.logger.info("showOnlyCategories link was clicked");
			return "history";
		} else {
			// i think the best way to handle session problems
			return "index";
		}
	}

	@RequestMapping(value = "/showByDate", method = RequestMethod.GET)
	String showByDate(@RequestParam(value = "date1") String firstDate,
			@RequestParam(value = "date2") String secondDate,
			HttpSession session, Model model) {
		if (!session.isNew()) {
			try {
				LocalDate firstDateJava = stringToLocalDate(firstDate);
				LocalDate secondDateJava = stringToLocalDate(secondDate);
				List<Payment> allPayments = (List<Payment>) session
						.getAttribute("AllPaymentsSession");
				List<Payment> payments = getPaymentsBetweenDates(firstDateJava,
						secondDateJava, allPayments);
				List<String> categories = HomePageController
						.generateCategoriesByType(showGlobal,
								(Map<String, ArrayList<String>>) session
										.getAttribute("AllCategoriesSession"));
				model.addAttribute("currCategories", categories);
				model.addAttribute("currPayments", payments);
				model.addAttribute("totalAmount",
						HomePageController.total(payments));
				Utils.logger.info("showByDate link was clicked");
			} catch (IllegalArgumentException | DateTimeParseException e) {
				Utils.logger.error(e.getMessage());
			}
		}
		return "history";

	}

	private List<Payment> getPaymentsBetweenDates(LocalDate firstDateJava,
			LocalDate secondDateJava, List<Payment> allPayments) {
		List<Payment> result = new ArrayList<Payment>();
		result.addAll(allPayments);
		for (Iterator<Payment> itt = result.iterator(); itt.hasNext();) {
			Payment payment = itt.next();
			if (payment.getDate().isAfter(secondDateJava)
					|| payment.getDate().isBefore(firstDateJava))
				itt.remove();
		}
		return result;
	}

	private LocalDate stringToLocalDate(String date)
			throws IllegalArgumentException, DateTimeParseException {
		LocalDate dateJava = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		try {
			dateJava = LocalDate.parse(date, formatter);
		} catch (DateTimeParseException e) {
			Utils.logger.error(e.getMessage());
			throw e;
		}
		return dateJava;
	}

	@RequestMapping(value = "/sortByDate", method = RequestMethod.GET)
	String sortByDate(Model model, HttpSession session) {

		if (!session.isNew()) {
			String type = showGlobal;
			List<Payment> payments = (List<Payment>) session
					.getAttribute("AllPaymentsSession");
			List<String> categories = HomePageController
					.generateCategoriesByType(type,
							(Map<String, ArrayList<String>>) session
									.getAttribute("AllCategoriesSession"));
			if (!type.equalsIgnoreCase("ALL"))
				payments = getPaymentsByType(type, payments);
			Collections.sort(payments, new Comparator<Payment>() {
				@Override
				public int compare(Payment o1, Payment o2) {
					if (o1.getDate().isAfter(o2.getDate()))
						return ifAsc;
					return ifAsc * -1;
				}

			});
			ifAsc *= -1;
			model.addAttribute("currCategories", categories);
			session.setAttribute("currPayments", payments);
			System.out.println("PAYMENTS IN HISTORY controller: " + payments);
			model.addAttribute("totalAmount",
					HomePageController.total(payments));
			Utils.logger.info("sortByDate link was clicked");
			return "history";
		} else {
			return "index";
		}
	}

	public static void modifyByDate(String date, List<Payment> payments)
			throws IllegalArgumentException {
		LocalDate dateJava;
		try {
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("dd-MM-yyyy");

			dateJava = LocalDate.parse(date, formatter);
			for (Iterator<Payment> itt = payments.iterator(); itt.hasNext();) {
				Payment p = itt.next();
				if (!p.getDate().equals(dateJava)) {
					itt.remove();
				}
			}
		} catch (IllegalArgumentException e) {
			Utils.logger.error("date field empty");
			throw e;
		}
	}
}
