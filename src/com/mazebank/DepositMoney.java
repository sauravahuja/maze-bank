package com.mazebank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DepositMoney {
	Connection conn;
	int newTransactionID;
	private String currentDate;

	public DepositMoney() {
		DBConnection connection = new DBConnection();
		conn = connection.getConnection();
		currentDate = getCurrentDate();
	}

	public void depositMoney(String sender, String receiver, String type, String amount) {
		if (checkIfAccountExist(sender) && checkIfAccountExist(receiver)) {
			try {
				String depositMoneyToReceiver = "update accounts set balance = balance + ? where account_id = ?";

				PreparedStatement depositStatement = conn.prepareStatement(depositMoneyToReceiver);

				depositStatement.setString(1, amount);
				depositStatement.setString(2, receiver);

				depositStatement.executeQuery();

				String deductMoneyFromSender = "update accounts set balance = balance - ? where account_id = ?";

				PreparedStatement deductStatement = conn.prepareStatement(deductMoneyFromSender);

				deductStatement.setString(1, amount);
				deductStatement.setString(2, sender);

				deductStatement.executeQuery();

				String newTransaction = "insert into transaction values (?,?,?,?,?,?)";
				PreparedStatement newTransactionStatement = conn.prepareStatement(newTransaction);

				newTransactionStatement.setString(1, String.valueOf(getNewTransactionID()));
				newTransactionStatement.setString(2, currentDate);
				newTransactionStatement.setString(3, amount);
				newTransactionStatement.setString(4, sender);
				newTransactionStatement.setString(5, type);
				newTransactionStatement.setString(6, receiver);

				newTransactionStatement.executeQuery();
			} catch (Exception e) {
				System.out.println(e);
			}
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

	public int getNewTransactionID() {
		String oldTransactionID = null;

		try {
			Statement s = conn.createStatement();

			ResultSet rs = s.executeQuery("select max(transaction_id) from transaction");
			while (rs.next()) {
				oldTransactionID = rs.getString(1);

			}
		} catch (Exception e) {
			System.out.println(e);
		}

		newTransactionID = Integer.parseInt(oldTransactionID);
		newTransactionID += 1;
		return newTransactionID;
	}

	public String getCurrentDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String currentDate = dtf.format(now);
		return currentDate;
	}
}
