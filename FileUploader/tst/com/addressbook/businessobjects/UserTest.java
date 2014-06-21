package com.addressbook.businessobjects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UserTest {
	@Test
	public void testSetterAndGetter() {
		int userId = 1;
		String email = "a@a.com";
		String firstname = "name";
		String lastname = "last";
		String registrationType = "internal";
		
		User user = new User();
		user.setEmail(email);
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setRegistrationType(registrationType);
		user.setUserId(userId);
		
		assertEquals(userId, user.getUserId());
		assertEquals(email, user.getEmail());
		assertEquals(firstname, user.getFirstname());
		assertEquals(lastname, user.getLastname());
		assertEquals(registrationType, user.getRegistrationType());
	}
}
