package com.addressbook.businessobjects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ContactTest {
	@Test
	public void testSetterAndGetter() {
		int contactId = 1;
		String firstname = "name";
		String lastname = "last";
		long phone = 12312341;
		int userId = 2;
		
		Contact contact = new Contact();
		contact.setContactId(contactId);
		contact.setFirstname(firstname);
		contact.setLastname(lastname);
		contact.setPhone(phone);
		contact.setUserId(userId);
		
		assertEquals(contactId, contact.getContactId());
		assertEquals(firstname, contact.getFirstname());
		assertEquals(lastname, contact.getLastname());
		assertEquals(phone, contact.getPhone());
		assertEquals(userId, contact.getUserId());
	}
}
