package com.addressbook.businesslogic;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.addressbook.businessobjects.Auth;
import com.addressbook.businessobjects.User;
import com.addressbook.dao.AuthenticationDao;
import com.addressbook.dao.UserDao;
import com.addressbook.dao.util.PasswordManager;
import com.addressbook.util.ValidationHelper;

/**
 * This class is responsible to preform Business logic related to User.
 * @author kashifu
 *
 */
public class UserAccountHandler {
	private UserDao userDao;
	private AuthenticationDao authdao;
	private PasswordManager pwdMgr;
	private final Log logger = LogFactory.getLog(getClass());
	
	public UserAccountHandler(UserDao userDao, AuthenticationDao authDao, PasswordManager pwdMgr) {
		ValidationHelper.validateForNull(userDao, "userDao");
		ValidationHelper.validateForNull(authDao, "authDao");
		ValidationHelper.validateForNull(pwdMgr, "pwdMgr");
		this.userDao = userDao;
		this.authdao = authDao;
		this.pwdMgr = pwdMgr;
	} 
	
	public boolean userExists(String email, String registrationType) {
		ValidationHelper.validateNotBlank(email, "email");
		ValidationHelper.validateNotBlank(registrationType, "registrationType");		
		User user = userDao.getUser(email, registrationType);
		return user != null;		
	}
	
	public void createAccount(User user, String password) {		
		ValidationHelper.validateForNull(user, "user");
		ValidationHelper.validateNotBlank(password, "password");
		logger.info("Trying to create account for user: " + user);
		userDao.insert(user);
		User insertedUser = userDao.getUser(user.getEmail(), user.getRegistrationType());
		String encryptedPassword = pwdMgr.encrypt(password);
		authdao.save(insertedUser, encryptedPassword);
	}
	
	public void createOpenIdAccount(User user) {
		logger.info("Creating open id account for user: " + user);
		ValidationHelper.validateForNull(user, "user");//TODO: Make fields in user table nullable
		userDao.insert(user);
	}
	
	public boolean authenticate(String email, String registrationType, String password) {
		ValidationHelper.validateNotBlank(email, "email");
		ValidationHelper.validateNotBlank(registrationType, "registrationType");
		ValidationHelper.validateNotBlank(password, "pwdHash");
		User user = userDao.getUser(email, registrationType);
		if (user == null) {
			return false;
		}
		Auth auth = authdao.getAuth(user);
		if (pwdMgr.authenticate(password, auth.getPwdhash())) {
			return true;
		}
		return false;
	}
	
	public User getUser(String email, String registrationType) {
		ValidationHelper.validateNotBlank(email, "email");
		ValidationHelper.validateNotBlank(registrationType, "registrationType");
		
		return userDao.getUser(email, registrationType);
	}
}
