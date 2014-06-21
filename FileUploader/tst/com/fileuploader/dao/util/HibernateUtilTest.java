package com.fileuploader.dao.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.fileuploader.dao.util.HibernateUtil;

public class HibernateUtilTest {
	@Test
	public void testGetSessionFactory() {
		assertNotNull(HibernateUtil.getSessionFactory());
	}
}
