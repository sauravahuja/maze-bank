package com.mazebank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class MazeInitializer {

	public static void main(String[] args) throws IOException {

		int initializeInput;
		Scanner sc = new Scanner(System.in);
//		DBConnection conn = new DBConnection();
		CreateNewAccount newAcc;
		System.out.println("Welcome to Maze Bank \n" + "Select the options from below to perform your operations: \n"
				+ "1) Create new account \n" + "2) Login into existing account \n" + "3) Exit");

		initializeInput = sc.nextInt();
		System.out.println("You choose input: " + initializeInput);
		String customerName;
		BufferedReader reader =new BufferedReader(new InputStreamReader(System.in));
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
		}

		sc.close();
	}

}
