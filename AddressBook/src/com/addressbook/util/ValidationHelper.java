package com.addressbook.util;

import org.apache.commons.lang3.StringUtils;
/**
 * Utility class to provide validation of method arguments.
 * @author kashifu
 *
 */
public class ValidationHelper {
	
	public static void validateForNull(Object obj, String msg) {
		if(obj == null) {
			throw new IllegalArgumentException("Invalid input: " + msg + " is null");
		}
	}
	
	public static void validateNotBlank(String s, String msg) {
        if (StringUtils.isBlank(s)) {
            throw new IllegalArgumentException("Argument \"" + msg + "\" must not be null or empty (" + s + ")");
        }
    }
}
 