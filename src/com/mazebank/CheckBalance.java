package com.mazebank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CheckBalance {
	Connection conn;
	String balanceAmount = "0";
	Boolean accountExist = false; 
	public CheckBalance() {
		DBConnection connection = new DBConnection();
		conn = connection.getConnection();
		
	}

	public String checkAccountBalance(String AccountNumber) throws Exception {
		accountExist = checkIfAccountExist(AccountNumber);
		if(accountExist) {
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
		else {
			throw new AccountDoesNotExist("The entered account number doesn't exist !");
		}
	}

	public boolean checkIfAccountExist(String accountnumber) {
		ResultSet checkRS = null;
		String flag = null;
		try {

			String checkifAccountExist = "select account_id from accounts where account_id = ?";

			PreparedStatement checkStatement = conn.prepareStatement(checkifAccountExist);

			checkStatement.setString(1, accountnumber);

			checkRS = checkStatement.executeQuery();
			if (checkRS.next()) {
				flag = checkRS.getString(1);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		if (flag != null) {
			return true;
		} else {
			return false;
		}
	}
}
