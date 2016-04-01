package com.model.db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class DBManager {

	private static DBManager instance;
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public  static final String DB_NAME = "cashguidedb";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/";
	private static final String DB_USER = "root";
	//password different for users
	private static final String DB_PASS = "ds941213";
	private Connection con;

	private DBManager() {

		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			System.out.println("con: " + con);
			System.out.println("Connection created successfully");
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Error connection to DB " + e.getMessage());
		}
		createDB();

	}

	private void createDB() {
		try (Statement st = con.createStatement()) {
			String query;
			query = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
			st.executeUpdate(query);
			query = "USE " + DB_NAME;
			st.executeUpdate(query);
			st.close();
			closeConnection();
		} catch (SQLException e) {
			System.out.println("Error creating DB" + e.getMessage());
		}

	}

	public static synchronized DBManager getDBManager() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

 	public Connection getConnection() {
		try {
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch (SQLException e) {
			System.out.println("Error creating connection" + e.getMessage());
		}
		return con;
	}

	public  void closeConnection() {
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}


}
