package com.mazebank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WithdrawMoney {
	Connection conn;
	int newTransactionID;
	private String currentDate;
	CheckBalance cb;

	public WithdrawMoney() {
		DBConnection connection = new DBConnection();
		conn = connection.getConnection();
		currentDate = getCurrentDate();
		cb = new CheckBalance();
	}

	public void WithdrawMoneyInit(String accountNumber, String amount, String type) throws Exception {

		if (checkIfAccountExist(accountNumber)) {
			int Bal = Integer.parseInt(cb.checkAccountBalance(accountNumber));
			int minbalAfterPossibleTransaction = Bal - Integer.parseInt(amount);
			if (Integer.parseInt(cb.checkAccountBalance(accountNumber)) > 0 && minbalAfterPossibleTransaction >= 500) {
				try {
					String withDrawMoney = "update accounts set balance = balance - ? where account_id = ?";

					PreparedStatement withdrawStatement = conn.prepareStatement(withDrawMoney);

					withdrawStatement.setString(1, amount);
					withdrawStatement.setString(2, accountNumber);

					withdrawStatement.executeQuery();

					String newTransaction = "insert into transaction values (?,?,?,?,?,?)";
					PreparedStatement newTransactionStatement = conn.prepareStatement(newTransaction);

					newTransactionStatement.setString(1, String.valueOf(getNewTransactionID()));
					newTransactionStatement.setString(2, currentDate);
					newTransactionStatement.setString(3, amount);
					newTransactionStatement.setString(4, accountNumber);
					newTransactionStatement.setString(5, type);
					newTransactionStatement.setString(6, accountNumber);

					newTransactionStatement.executeQuery();
				} catch (Exception e) {
					System.out.println(e);
				}
			} else {
				// Todo: throw custom exception
				System.out.println("Aborting Transaction");
				throw new AmountInsufficient(
						"The entered amount is either greater than the existing balance or its is crossing the minimum balance of 500");
			}
		} else {
			// Todo: throw custom exception
			throw new AccountDoesNotExist("The entered account number doesnt exsit !");
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
