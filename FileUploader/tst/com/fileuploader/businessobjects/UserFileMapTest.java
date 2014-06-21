package com.fileuploader.businessobjects;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UserFileMapTest {
	@Test
	public void testSetterAndGetter() {
		int userId = 1;
		int fileId = 2;
		
		UserFileMap map = new UserFileMap();
		map.setId(fileId);
		map.setUserId(userId);
		assertEquals(map.getId(), fileId);
		assertEquals(map.getUserId(), userId);
	}
}
