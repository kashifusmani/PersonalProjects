package com.addressbook.dao.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class HibernateUtilTest {
	@Test
	public void testGetSessionFactory() {
		assertNotNull(HibernateUtil.getSessionFactory());
	}
}
