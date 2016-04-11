package com.model;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

public class Utils {

	private static final String TOKEN = "cashguide";
	public final static Logger logger = Logger.getLogger("LOGS");

	//private static final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	private final static String EMAIL_REGEX = "^[A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-]{3,7})(\\.[A-Za-z]{2,})$";
	//password max length 16
	private final static String PASSWORD_REGEX="[A-Za-z0-9_@#$%]{6,16}";
	//usernames max length 11
	private final static String USERNAME_REGEX="^[A-Za-z]([A-Za-z0-9]){4,9}$";
	
	public static boolean isValidEmail(String email){
		return email.matches(EMAIL_REGEX);
	}
	
	public static boolean isValidPassword(String password){
		if(password.trim().length()==0||!password.matches(PASSWORD_REGEX)){
			System.out.println("password is false");
			return false;
		}
		System.out.println("password is true");
		return true;
	}
	
	public static boolean isValidUsername(String username){
		if(username.trim().length()==0||!username.matches(USERNAME_REGEX)){
			System.out.println("username is false");
			return false;
		}
		System.out.println("username is true");
		return true;
	}
	
	public static String hashPassword(String password){
		String hash = DigestUtils.md5Hex(TOKEN+password);
		return hash;
	}
	
	
}
