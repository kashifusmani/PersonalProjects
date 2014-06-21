package com.addressbook.businesslogic;

import org.jasypt.util.password.StrongPasswordEncryptor;

import com.addressbook.dao.AuthenticationDao;
import com.addressbook.dao.ContactsDao;
import com.addressbook.dao.FileExpenseDao;
import com.addressbook.dao.UserDao;
import com.addressbook.dao.util.HibernateUtil;
import com.addressbook.dao.util.PasswordManager;

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
	
	public ContactsHandler getContactsHandler() {
		ContactsDao contDao = new ContactsDao(HibernateUtil.getSessionFactory());
		return new ContactsHandler(contDao);
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
