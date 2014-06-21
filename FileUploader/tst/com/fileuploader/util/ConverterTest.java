package com.fileuploader.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fileuploader.businessobjects.User;
import com.fileuploader.servlets.beans.RegistrationBean;
import com.fileuploader.util.Converter;

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

}
