package com.addressbook.servlets.beans;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.addressbook.servlets.beans.AddContactBean;

public class AddContactBeanTest {
	@Test
	public void testSetterAndGetter() {
		AddContactBean bean = new AddContactBean();
		String firstname = "name";
		String lastname = "last";
		long phone = 1231231;
		int userId = 1;
		bean.setFirstname(firstname);
		bean.setLastname(lastname);
		bean.setPhone(phone);
		bean.setUserId(userId);
		assertEquals(bean.getFirstname(), firstname);
		assertEquals(bean.getLastname(), lastname);
		assertEquals(bean.getPhone(), phone);
		assertEquals(bean.getUserId(), userId);
	}
}
