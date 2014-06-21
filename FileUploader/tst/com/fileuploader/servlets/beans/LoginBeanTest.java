package com.fileuploader.servlets.beans;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fileuploader.servlets.beans.LoginBean;

public class LoginBeanTest {
	@Test
	public void testSetterAndGetter() {
		LoginBean bean = new LoginBean();
		String email = "a@a.com";
		String password = "pwd";
		String registrationType = "internal";
		bean.setEmail(email);
		bean.setPassword(password);
		bean.setRegistrationType(registrationType);
		assertEquals(bean.getEmail(), email);
		assertEquals(bean.getPassword(), password);
		assertEquals(bean.getRegistrationType(), registrationType);
	}
}
