package com.mazebank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MazeInitializer {

	public static void main(String[] args) throws IOException {

		int initializeInput;
		Scanner sc = new Scanner(System.in);
		CreateNewAccount newAcc;
		System.out.println("Welcome to Maze Bank \n" + "Select the options from below to perform your operations: \n"
				+ "1) Create new account \n" + "2) Services from existing account \n" + "3) Exit");

		initializeInput = sc.nextInt();
		System.out.println("You choose input: " + initializeInput);
		String customerName;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		switch (initializeInput) {
		case 1:
			System.out.println("Create New Account Procedure Initiated");
			// Input Customer Name
			System.out.println("Enter Customer Name: ");
			customerName = reader.readLine();

			// Input Customer Phone
			System.out.println("Enter Customer Phone: ");
			String customerPhone = reader.readLine();

			// Input Customer Address
			System.out.println("Enter Customer Address: ");
			String customerAddress = reader.readLine();

			// Input Customer DOB
			System.out.println("Enter Customer DOB: (DD/MM/YYYY) ");
			String customerDOB = reader.readLine();

			// Input Customer AAdhar
			System.out.println("Enter Customer Aadhar: ");
			String customerAadhar = reader.readLine();

			// Input Customer PAN
			System.out.println("Enter Customer PAN: ");
			String customerPAN = reader.readLine();

			// Input Customer AAdhar
			System.out.println("Enter Customer Account Type (1. Saving 2. Current and 3.Salary Account): ");
			int customerAccType = Integer.parseInt(reader.readLine());

			newAcc = new CreateNewAccount();
			newAcc.newAccountProcedure(customerName, customerPhone, customerAddress, customerDOB, customerAadhar,
					customerPAN, customerAccType);
			break;

		case 2:
			System.out.println("You can choose from the following service:\n1. Account Balance 2. Deposit 3. Withdraw");
			int serviceOption = Integer.parseInt(reader.readLine());
			switch (serviceOption) {
			case 1:
				System.out.println("Enter Account Number");
				String accountNumber = reader.readLine();
				System.out.println("Balance checking procedure started");
				CheckBalance cb = new CheckBalance();
				System.out.println("Balance for Account Number " + accountNumber +" is: " +cb.checkAccountBalance(accountNumber));
				break;
				
			case 2:
				System.out.println("Deposit Money process initiated !");
				System.out.println("Enter sender account number: ");
				String senderAccountNumber = reader.readLine();
				System.out.println("Enter reciver account number: ");
				String receiverAccountNumber = reader.readLine();
//				System.out.println("Enter transaction type(1. Cash Withdrawl 2. Cash Deposit 3.Check Payment 4. Card Payment)");
				String typeOfTransaction = "2";
				System.out.println("Enter the Amount");
				String depositAmount = reader.readLine();
				
				DepositMoney dm = new DepositMoney();
				dm.depositMoney(senderAccountNumber, receiverAccountNumber, typeOfTransaction, depositAmount);
				
				break;
				
			case 3:
				System.out.println("Withdraw process initiated !");
				System.out.println("Enter your account number");
				String withdrawAccountNumber = reader.readLine();
				System.out.println("Enter Amount");
				String withdrawAmount = reader.readLine();
				String withdrawtype = "1";
				WithdrawMoney wm = new WithdrawMoney();
				wm.WithdrawMoneyInit(withdrawAccountNumber, withdrawAmount, withdrawtype);
				break;
			
			}
		}

		sc.close();
	}

}
