package com.fileuploader.util;

import org.junit.Test;

import com.fileuploader.util.ValidationHelper;

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
