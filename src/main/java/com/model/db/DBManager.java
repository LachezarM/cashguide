package com.model.db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DBManager {

	private static DBManager instance;
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public  static final String DB_NAME = "cashguidedb";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/";
	private static final String DB_USER = "root";

	private static final String DB_PASS = "ds941213";
	private static volatile Connection con;

	private DBManager() {

		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			System.out.println("con: " + con);
			System.out.println("Connection created successfully");
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Error connection to DB " + e.getMessage());
		}
	}

	public static synchronized DBManager getDBManager() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

 	public Connection getConnection() {
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
