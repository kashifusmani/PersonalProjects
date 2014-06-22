package com.fileuploader.util;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

import com.fileuploader.businessobjects.MonthlyExpense;

/**
 * Utility class to provide methods to convert Object to json representation and vice-versa.
 * @author kashifu
 *
 */
public class JacksonMarshaler {
	private static final Log logger = LogFactory.getLog(JacksonMarshaler.class);

	public static String toMonthlyExpenseListJsonString(List<MonthlyExpense> monthlyExpenseList) {
		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(monthlyExpenseList);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return json;
	}
}
