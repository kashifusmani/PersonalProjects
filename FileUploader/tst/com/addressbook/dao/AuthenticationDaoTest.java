package com.addressbook.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.addressbook.businessobjects.Auth;
import com.addressbook.businessobjects.User;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationDaoTest {
	private AuthenticationDao daoInTest;
	@Mock
	private SessionFactory mockSessionFactory;
	@Mock
	private Session mockSession;
	@Mock
	private Criteria mockCriteria;
	@Mock
	private Transaction mockTransaction;

	@Before
	public void setup() {
		daoInTest = new AuthenticationDao(mockSessionFactory);
		when(mockSessionFactory.openSession()).thenReturn(mockSession);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullFactory() {
		daoInTest = new AuthenticationDao(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAuthNullUser() {
		daoInTest.getAuth(null);
	}

	@Test
	public void testGetAuthHappyPath() {
		Auth auth = new Auth();
		int userId = 1;
		String pwdhash = "asjd";
		auth.setPwdhash(pwdhash);
		auth.setUserId(userId);
		when(mockSession.createCriteria(Auth.class)).thenReturn(mockCriteria);
		when(mockCriteria.uniqueResult()).thenReturn(auth);
		when(mockSession.getTransaction()).thenReturn(mockTransaction);
		User user = new User();
		user.setUserId(userId);
		Auth actualValue = daoInTest.getAuth(user);
		assertEquals(actualValue, auth);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveNullUser() {
		daoInTest.save(null, "pwd");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveBlankHash() {
		daoInTest.save(new User(), "  ");
	}

	@Test
	public void testSaveHappyPath() {
		int userId = 1;
		String pwdhash = "asdasd";
		User user = new User();
		user.setUserId(userId);
		when(mockSession.getTransaction()).thenReturn(mockTransaction);
		daoInTest.save(user, pwdhash);
		ArgumentCaptor<Auth> authCaptor = ArgumentCaptor.forClass(Auth.class);
		verify(mockSession, times(1)).save(authCaptor.capture());

		Auth auth = authCaptor.getValue();
		assertEquals(auth.getUserId(), userId);
		assertEquals(auth.getPwdhash(), pwdhash);
	}
	
}
