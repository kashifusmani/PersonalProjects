package com.addressbook.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.addressbook.businessobjects.Contact;
import com.addressbook.businessobjects.User;

@RunWith(MockitoJUnitRunner.class)
public class ContactsDaoTest {
	private ContactsDao daoInTest;
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
		daoInTest = new ContactsDao(mockSessionFactory);
		when(mockSessionFactory.openSession()).thenReturn(mockSession);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullSessionFactory() {
		daoInTest = new ContactsDao(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInsertNullContact() {
		daoInTest.insert(null);
	}

	@Test
	public void testInsertHappyPath() {
		Contact contact = new Contact();
		contact.setContactId(1);
		contact.setFirstname("firstname");
		contact.setLastname("lastname");
		contact.setPhone(123123);
		contact.setUserId(2);
		when(mockSession.getTransaction()).thenReturn(mockTransaction);
		daoInTest.insert(contact);
		verify(mockSession, times(1)).save(contact);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUpdateNullContact() {
		daoInTest.update(null);
	}

	@Test
	public void testUpdateHappyPath() {
		Contact contact = new Contact();
		contact.setContactId(1);
		contact.setFirstname("firstname");
		contact.setLastname("lastname");
		contact.setPhone(123123);
		contact.setUserId(2);
		when(mockSession.getTransaction()).thenReturn(mockTransaction);
		daoInTest.update(contact);
		verify(mockSession, times(1)).update(contact);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetContactsNullUser() {
		daoInTest.getContacts(null);
	}

	@Test
	public void testGetContactsHappyPath() {
		Contact contact = new Contact();
		contact.setContactId(1);
		contact.setFirstname("firstname");
		contact.setLastname("lastname");
		contact.setPhone(123123);
		contact.setUserId(2);
		List<Contact> contacts = new ArrayList<Contact>();
		contacts.add(contact);
		when(mockSession.createCriteria(Contact.class))
				.thenReturn(mockCriteria);
		when(mockSession.getTransaction()).thenReturn(mockTransaction);
		when(mockCriteria.list()).thenReturn(contacts);
		User user = new User();
		user.setUserId(1);
		List<Contact> actualList = daoInTest.getContacts(user);
		assertEquals(actualList.size(), 1);
		assertEquals(actualList.get(0), contact);
	}
}
