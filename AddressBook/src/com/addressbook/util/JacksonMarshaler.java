package com.addressbook.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

import com.addressbook.businessobjects.Contact;
public class JacksonMarshaler {
	private static final Log logger = LogFactory.getLog(JacksonMarshaler.class);
	public static String  toJsonString(List<Contact> contacts) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(contacts);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return json;
	}
	
}
