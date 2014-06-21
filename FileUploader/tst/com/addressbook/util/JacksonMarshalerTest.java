package com.addressbook.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.addressbook.businessobjects.Contact;

public class JacksonMarshalerTest {

	@Test
	public void testToJsonString() {
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
		
		List<Contact> contacts = new ArrayList<Contact>();
		contacts.add(contact);
		String expectedJson = "[{\"contactId\":" + contactId + ",\"firstname\":\"" + firstname + "\",\"lastname\":\"" + lastname
				+ "\",\"phone\":" + phone + ",\"userId\":" + userId + "}]";
		
		assertEquals(JacksonMarshaler.toJsonString(contacts), expectedJson);
	}
}
