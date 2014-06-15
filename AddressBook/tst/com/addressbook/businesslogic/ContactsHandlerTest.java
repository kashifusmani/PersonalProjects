package com.addressbook.businesslogic;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.addressbook.businessobjects.Contact;
import com.addressbook.businessobjects.User;
import com.addressbook.dao.ContactsDao;

@RunWith(MockitoJUnitRunner.class)
public class ContactsHandlerTest {
	private ContactsHandler handlerInTest;
	@Mock
	private ContactsDao mockDao;
	
	@Before()
	public void before() {		
		handlerInTest = new ContactsHandler(mockDao);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullInput() {
		 new ContactsHandler(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateContactWithNullInput() {
		handlerInTest.createContact(null);
	}
	
	@Test
	public void testCreateContactHappyPath() {
		Contact contact = new Contact();
		handlerInTest.createContact(contact);
		verify(mockDao, times(1)).insert(contact);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testUpdateContactWithNullInput() {
		handlerInTest.updateContact(null);
	}
	
	@Test
	public void testUpdateContactHappyPath() {
		Contact contact = new Contact();
		handlerInTest.updateContact(contact);
		verify(mockDao, times(1)).update(contact);
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetContactWithNullInput() {
		handlerInTest.getContacts(null);
	}
	
	@Test
	public void testGetContactsHappyPath() {
		Contact contact = new Contact();
		int contactId = 1;
		String firstName = "name";
		String lastName = "lastName";
		long phone = (long)416111001;
		int userId = 2;
		contact.setContactId(contactId);
		contact.setFirstname(firstName);
		contact.setLastname(lastName);
		contact.setPhone(phone);
		contact.setUserId(userId);
		List<Contact> contacts = new ArrayList<Contact>();
		contacts.add(contact);
		User user = new User();
		when(mockDao.getContacts(user)).thenReturn(contacts);
		
		List<Contact> actualContacts = handlerInTest.getContacts(user);
		assertEquals(actualContacts.size(), 1);
		assertEquals(actualContacts.get(0), contact);
	}
	
}
