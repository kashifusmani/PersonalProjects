package com.addressbook.dao.util;

import org.jasypt.util.password.StrongPasswordEncryptor;

import com.addressbook.util.ValidationHelper;

public class PasswordManager {
	private StrongPasswordEncryptor passwordEncryptor;
	
	public PasswordManager(StrongPasswordEncryptor passwordEncryptor) {
		ValidationHelper.validateForNull(passwordEncryptor, "passwordEncryptor");
		this.passwordEncryptor = passwordEncryptor;
	}
	
	public String encrypt(String password) {
		ValidationHelper.validateNotBlank(password, "password");
		String encryptedPassword = passwordEncryptor.encryptPassword(password);
		return encryptedPassword;
	}
	
	public boolean authenticate(String password, String encryptedPassword) {
		ValidationHelper.validateNotBlank(password, "password");
		ValidationHelper.validateNotBlank(encryptedPassword, "encryptedPassword");
		return passwordEncryptor.checkPassword(password, encryptedPassword);
	}
}

