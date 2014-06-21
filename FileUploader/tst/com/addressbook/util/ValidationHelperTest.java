package com.addressbook.util;

import org.junit.Test;

public class ValidationHelperTest {

	@Test(expected = IllegalArgumentException.class)
	public void testValidateForNull() {
		ValidationHelper.validateForNull(null, "someobj");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testValidateNotBlank() {
		ValidationHelper.validateNotBlank(" ", "someobj");
	}
}
