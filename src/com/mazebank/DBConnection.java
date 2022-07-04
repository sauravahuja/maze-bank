package com.mazebank;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	Connection conn;

	public DBConnection() {

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

			String url = "jdbc:oracle:thin:@localhost:1521/xe";
			String username = "sys as sysdba";
			String password = "system";

			conn = DriverManager.getConnection(url, username, password);
//			if (conn != null) {
////				System.out.println("Database Connection Successful");
//			}
		} catch (Exception e) {
			System.out.println("Connection Exception: " + e);
		}
	}
	public Connection getConnection() {
		return conn;
	}
}
