package com.fileuploader.businesslogic;

import org.jasypt.util.password.StrongPasswordEncryptor;

import com.fileuploader.dao.AuthenticationDao;
import com.fileuploader.dao.FileExpenseDao;
import com.fileuploader.dao.UserDao;
import com.fileuploader.dao.util.HibernateUtil;
import com.fileuploader.dao.util.PasswordManager;

/**
 * Utility class to provide dependency injections. 
 * Should be removed once Spring framework is in use.
 */
public class HandlerInitializer {

	public UserAccountHandler getUserAccountHandler() {
		UserDao userDao = new UserDao(HibernateUtil.getSessionFactory());
		AuthenticationDao authDao = new AuthenticationDao(HibernateUtil.getSessionFactory());
		PasswordManager pwdMgr = new PasswordManager(new StrongPasswordEncryptor());
		return new UserAccountHandler(userDao, authDao, pwdMgr);	
	}
	
	public UploadFileHandler getUploadFileHandler() {
		FileExpenseDao fileDao = new FileExpenseDao(HibernateUtil.getSessionFactory());
		return new UploadFileHandler(fileDao);
	}
	
	public FileExpenseHandler getFileExpenseHandler() {
		FileExpenseDao fileDao = new FileExpenseDao(HibernateUtil.getSessionFactory());
		return new FileExpenseHandler(fileDao);
	}
}
