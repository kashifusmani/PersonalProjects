package com.fileuploader.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.fileuploader.businessobjects.User;
import com.fileuploader.dao.UserDao;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoTest {
	private UserDao daoInTest;
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
		daoInTest = new UserDao(mockSessionFactory);
		when(mockSessionFactory.openSession()).thenReturn(mockSession);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullFactory() {
		daoInTest = new UserDao(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUserBlankEmail() {
		daoInTest.getUser(" ", "registrationType");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUserRegistrationTypeBlank() {
		daoInTest.getUser("a@a.com", "  ");
	}

	@Test
	public void testGetUserHappyPath() {
		String email = "a@a.com";
		String registrationType = "internal";
		when(mockSession.createCriteria(User.class)).thenReturn(mockCriteria);
		when(mockSession.getTransaction()).thenReturn(mockTransaction);
		User user = new User();
		user.setEmail(email);
		user.setRegistrationType(registrationType);
		when(mockCriteria.uniqueResult()).thenReturn(user);

		User returnedUser = daoInTest.getUser(email, registrationType);
		assertEquals(returnedUser, user);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInsertNullUser() {
		daoInTest.insert(null);
	}

	@Test
	public void testInsertHappyPath() {
		String email = "a@a.com";
		String registrationType = "internal";
		User user = new User();
		user.setEmail(email);
		user.setRegistrationType(registrationType);
		when(mockSession.getTransaction()).thenReturn(mockTransaction);
		daoInTest.insert(user);
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(mockSession, times(1)).save(userCaptor.capture());

		User capturedUser = userCaptor.getValue();
		assertEquals(user, capturedUser);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateNullUser() {
		daoInTest.update(null);
	}

	@Test
	public void testUpdateHappyPath() {
		String email = "a@a.com";
		String registrationType = "internal";
		User user = new User();
		user.setEmail(email);
		user.setRegistrationType(registrationType);
		when(mockSession.getTransaction()).thenReturn(mockTransaction);
		daoInTest.update(user);
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(mockSession, times(1)).update(userCaptor.capture());

		User capturedUser = userCaptor.getValue();
		assertEquals(user, capturedUser);
	}

}
