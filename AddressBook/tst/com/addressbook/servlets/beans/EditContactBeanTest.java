package com.addressbook.servlets.beans;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EditContactBeanTest {
	@Test
	public void testSetterAndGetter() {
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
		assertEquals(bean.getFirstname(), firstname);
		assertEquals(bean.getLastname(), lastname);
		assertEquals(bean.getPhone(), phone);
		assertEquals(bean.getUserId(), userId);
		assertEquals(bean.getContactId(), contactId);
	}
}
