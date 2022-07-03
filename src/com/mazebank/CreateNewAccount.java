package com.mazebank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreateNewAccount {

	private String currentDate;
	private int newAccountID;
	private int newCustomerID;
	Connection conn;

	public CreateNewAccount() {
		DBConnection connection = new DBConnection();
		conn = connection.getConnection();
		System.out.println(getCurrentDate());
//		System.out.println(getNewAccountID());
//		System.out.println(getNewCustomerID());
	}

	public void newAccountProcedure(String name, String phone, String address, String DOB, String aadhar, String pan,
			int acctype) {

		int newCustomerID = getNewCustomerID();
		int newAccountID = getNewAccountID();
		String newCurrentDate = getCurrentDate();

		System.out.println("name" + name);
		try {
			String query = "insert into customer values (?,?,?,?,?,?)";

			PreparedStatement myStmt = conn.prepareStatement(query);

			myStmt.setString(1, String.valueOf(newCustomerID));
			myStmt.setString(2, name);
			myStmt.setInt(3, Integer.valueOf(phone));
			myStmt.setString(4, address);
			myStmt.setString(5, newCurrentDate);
			myStmt.setString(6, DOB);

			System.out.println(myStmt);
			ResultSet Rs = myStmt.executeQuery();
			if (Rs != null) {
				System.out.println("Customer Created");
			}

			String queryAcc = "insert into accounts values (?,?,?,?,?,?,?)";

			PreparedStatement myStmtAcc = conn.prepareStatement(queryAcc);

			myStmtAcc.setString(1, String.valueOf(newAccountID));
			myStmtAcc.setString(2, newCurrentDate);
			myStmtAcc.setString(3,aadhar);
			myStmtAcc.setString(4, pan);
			myStmtAcc.setString(5, String.valueOf(newCustomerID));
			myStmtAcc.setInt(6, acctype);
			myStmtAcc.setInt(7, 0);

			System.out.println(myStmt);
			ResultSet AccRs = myStmtAcc.executeQuery();
			if (AccRs != null) {
				System.out.println("Account Created");
			}
			
			System.out.println("-------------------------------");			
			System.out.println("Your Account Number is: " + newAccountID);
			System.out.println("Your Customer Number is: " + newCustomerID);
			System.out.println("-------------------------------");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public int getNewAccountID() {
		String oldAccountId = null;

		try {
			Statement s = conn.createStatement();

			ResultSet rs = s.executeQuery("select max(account_id) from accounts");
			while (rs.next()) {
				oldAccountId = rs.getString(1);

			}
		} catch (Exception e) {
			System.out.println(e);
		}

		newAccountID = Integer.parseInt(oldAccountId);
		newAccountID += 1;
		return newAccountID;
	}

	public int getNewCustomerID() {
		String oldCustomerId = null;

		try {
			Statement s = conn.createStatement();

			ResultSet rs = s.executeQuery("select max(customer_id) from customer");
			while (rs.next()) {
				oldCustomerId = rs.getString(1);

			}
		} catch (Exception e) {
			System.out.println(e);
		}

		newCustomerID = Integer.parseInt(oldCustomerId);
		newCustomerID += 1;
		return newCustomerID;
	}

	public String getCurrentDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		currentDate = dtf.format(now);
		return currentDate;
	}

}
