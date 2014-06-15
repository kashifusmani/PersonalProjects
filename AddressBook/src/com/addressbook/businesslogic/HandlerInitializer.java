package com.addressbook.businesslogic;

import org.jasypt.util.password.StrongPasswordEncryptor;

import com.addressbook.dao.AuthenticationDao;
import com.addressbook.dao.ContactsDao;
import com.addressbook.dao.UserDao;
import com.addressbook.dao.util.HibernateUtil;
import com.addressbook.dao.util.PasswordManager;

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
}
