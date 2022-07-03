package com.mazebank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CheckBalance {
	Connection conn;
	private String balanceAmount;
	public CheckBalance() {
		DBConnection connection = new DBConnection();
		conn = connection.getConnection();
	}

	public String checkAccountBalance(String AccountNumber) {
		try {
			String checkBalanceQuery = "select balance from accounts where account_id = ?";

			PreparedStatement balanceStatement = conn.prepareStatement(checkBalanceQuery);

			balanceStatement.setString(1, AccountNumber);
			

			ResultSet balanceRS = balanceStatement.executeQuery();
			if (balanceRS.next()) {
				balanceAmount = balanceRS.getString(1);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return balanceAmount;
	}
}
