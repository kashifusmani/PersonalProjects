package com.fileuploader.businessobjects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fileuploader.businessobjects.Auth;

public class AuthTest {

	@Test
	public void testSetterAndGetter() {
		int userId = 1;
		String pwdHash = "xsdfsdsad";

		Auth auth = new Auth();
		auth.setUserId(userId);
		auth.setPwdhash(pwdHash);

		assertEquals(userId, auth.getUserId());
		assertEquals(pwdHash, auth.getPwdhash());
	}
}
