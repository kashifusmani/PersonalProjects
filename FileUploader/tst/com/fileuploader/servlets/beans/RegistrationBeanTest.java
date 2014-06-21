package com.fileuploader.servlets.beans;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fileuploader.servlets.beans.RegistrationBean;

public class RegistrationBeanTest {
	@Test
	public void testSetterAndGetter() {
		RegistrationBean bean = new RegistrationBean();
		String email = "a@a.com";
		String password = "pwd";
		String registrationType = "internal";
		String firstname = "firstname";
		String lastname = "last";

		bean.setEmail(email);
		bean.setPassword(password);
		bean.setRegistrationType(registrationType);
		bean.setFirstname(firstname);
		bean.setLastname(lastname);
		assertEquals(bean.getEmail(), email);
		assertEquals(bean.getPassword(), password);
		assertEquals(bean.getRegistrationType(), registrationType);
		assertEquals(bean.getFirstname(), firstname);
		assertEquals(bean.getLastname(), lastname);
	}
}
