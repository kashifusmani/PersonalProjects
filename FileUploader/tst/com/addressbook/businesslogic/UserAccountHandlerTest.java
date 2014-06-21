package com.addressbook.businesslogic;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.addressbook.businessobjects.Auth;
import com.addressbook.businessobjects.User;
import com.addressbook.dao.AuthenticationDao;
import com.addressbook.dao.UserDao;
import com.addressbook.dao.util.PasswordManager;

@RunWith(MockitoJUnitRunner.class)
public class UserAccountHandlerTest {
	private UserAccountHandler handlerInTest;
	@Mock
	private UserDao mockUserDao;
	@Mock
	private AuthenticationDao mockAuthDao;
	@Mock
	private PasswordManager mockPwdMgr;
	
	@Before
	public void before() {
		handlerInTest = new UserAccountHandler(mockUserDao, mockAuthDao, mockPwdMgr);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testContructorNullUserDao() {
		handlerInTest = new UserAccountHandler(null, mockAuthDao, mockPwdMgr);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullAuthDao(){
		handlerInTest = new UserAccountHandler(mockUserDao, null, mockPwdMgr);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullPwdMgr() {
		handlerInTest = new UserAccountHandler(mockUserDao, mockAuthDao, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUserExistsInvalidEmail() {
		handlerInTest.userExists("", "internal");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUserExistsInvalidRegistrationType() {
		handlerInTest.userExists("email", "");		
	}
	
	@Test
	public void testUserExistsTrue() {
		String email = "a@a.com";
		String registrationType = "internal";
		when(mockUserDao.getUser(email, registrationType)).thenReturn(new User());
		assertTrue(handlerInTest.userExists(email, registrationType));
	}
	
	@Test
	public void testUserExistsFalse() {
		String email = "a@a.com";
		String registrationType = "internal";
		when(mockUserDao.getUser(email, registrationType)).thenReturn(null);
		assertFalse(handlerInTest.userExists(email, registrationType));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAccountNullUser(){
		handlerInTest.createAccount(null, "pwd");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAccountBlankPwd() {
		handlerInTest.createAccount(new User(), "");
	}
	
	@Test
	public void testCreateUserHappyPath() {
		User user = new User();
		String email = "a@a.com";
		String registrationType = "internal";
		String password = "password";
		user.setEmail(email);
		user.setRegistrationType(registrationType);
		String encryptedPwd = "sadfhsdf";
		
		when(mockUserDao.getUser(email, registrationType)).thenReturn(user);
		when(mockPwdMgr.encrypt(password)).thenReturn(encryptedPwd);
		
		handlerInTest.createAccount(user, password);
		verify(mockUserDao, times(1)).insert(user);
		verify(mockUserDao, times(1)).getUser(email, registrationType);
		verify(mockPwdMgr, times(1)).encrypt(password);
		verify(mockAuthDao, times(1)).save(user, encryptedPwd);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateOpenIdAccountNullUser() {
		handlerInTest.createOpenIdAccount(null);
	}
	
	@Test
	public void testCreateOpenIdAccountHappyPath() {
		User user = new User();
		handlerInTest.createOpenIdAccount(user);
		verify(mockUserDao, times(1)).insert(user);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAuthenticateBlankEmail() {
		handlerInTest.authenticate("", "registrationType", "password");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAuthenticateBlankRegistrationType() {
		handlerInTest.authenticate("email", "", "password");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAuthenticateBlankPassword(){
		handlerInTest.authenticate("email", "registrationType", "");
	}
	
	@Test
	public void testAuthenitcateUserNotExists() {
		String email = "a@a.com";
		String registrationType = "internal";
		String password = "password";
		when(mockUserDao.getUser(email, registrationType)).thenReturn(null);
		assertFalse(handlerInTest.authenticate(email, registrationType, password));		
	}
	
	@Test
	public void testAuthenticationWrongPassword() {
		String email = "a@a.com";
		String registrationType = "internal";
		String password = "password";
		User user = new User();
		when(mockUserDao.getUser(email, registrationType)).thenReturn(user);
		Auth auth = new Auth();
		String encryptedPwd = "xasxa";
		auth.setPwdhash(encryptedPwd);
		when(mockAuthDao.getAuth(user)).thenReturn(auth);
		when(mockPwdMgr.authenticate(password, encryptedPwd)).thenReturn(false);
		assertFalse(handlerInTest.authenticate(email, registrationType, password));
	}
	
	@Test
	public void testAuthenticationPass() {
		String email = "a@a.com";
		String registrationType = "internal";
		String password = "password";
		User user = new User();
		when(mockUserDao.getUser(email, registrationType)).thenReturn(user);
		Auth auth = new Auth();
		String encryptedPwd = "xasxa";
		auth.setPwdhash(encryptedPwd);
		when(mockAuthDao.getAuth(user)).thenReturn(auth);
		when(mockPwdMgr.authenticate(password, encryptedPwd)).thenReturn(true);
		assertTrue(handlerInTest.authenticate(email, registrationType, password));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetUserBlankEmail() {
		handlerInTest.getUser("", "registrationType");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetUserBlankRegistrationType() {
		handlerInTest.getUser("a@a.com", "");
	}
	
	@Test
	public void testGetUserHappyPath() {
		User user  = new User();
		String email = "a@a.com";
		String registrationType = "internal";
		
		when(mockUserDao.getUser(email, registrationType)).thenReturn(user);
		User returnedUser = handlerInTest.getUser(email, registrationType);
		assertEquals(returnedUser, user); 
	}
}
