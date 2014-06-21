package com.addressbook.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.addressbook.businessobjects.Contact;
import com.addressbook.businessobjects.User;
import com.addressbook.servlets.beans.AddContactBean;
import com.addressbook.servlets.beans.EditContactBean;
import com.addressbook.servlets.beans.RegistrationBean;

public class ConverterTest {
	@Test
	public void testToUser() {
		RegistrationBean bean = new RegistrationBean();
		String email = "a@a.com";
		String registrationType = "internal";
		String firstname = "firstname";
		String lastname = "last";

		bean.setEmail(email);
		bean.setRegistrationType(registrationType);
		bean.setFirstname(firstname);
		bean.setLastname(lastname);

		User user = Converter.toUser(bean);
		assertEquals(user.getEmail(), email);
		assertEquals(user.getFirstname(), firstname);
		assertEquals(user.getLastname(), lastname);
		assertEquals(user.getRegistrationType(), registrationType);
	}

	@Test
	public void testAddContactBeanToContact() {
		AddContactBean bean = new AddContactBean();
		String firstname = "name";
		String lastname = "last";
		long phone = 1231231;
		int userId = 1;
		bean.setFirstname(firstname);
		bean.setLastname(lastname);
		bean.setPhone(phone);
		bean.setUserId(userId);

		Contact contact = Converter.toContact(bean);
		assertEquals(contact.getFirstname(), firstname);
		assertEquals(contact.getLastname(), lastname);
		assertEquals(contact.getPhone(), phone);
		assertEquals(contact.getUserId(), userId);
	}

	@Test
	public void testEditContactBeanToContact() {
		EditContactBean bean = new EditContactBean();
		String firstname = "name";
		String lastname = "last";
		long phone = 1231231;
		int userId = 1;
		int contactId = 2;
		bean.setFirstname(firstname);
		bean.setLastname(lastname);
		bean.setPhone(phone);
		bean.setUserId(userId);
		bean.setContactId(contactId);

		Contact contact = Converter.toContact(bean);
		assertEquals(contact.getContactId(), contactId);
		assertEquals(contact.getFirstname(), firstname);
		assertEquals(contact.getLastname(), lastname);
		assertEquals(contact.getPhone(), phone);
		assertEquals(contact.getUserId(), userId);
	}

}
