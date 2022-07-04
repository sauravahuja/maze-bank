package com.mazebank;

public class AccountDoesNotExist extends Exception {
	public AccountDoesNotExist(String str) {
		super(str);
	}
}
