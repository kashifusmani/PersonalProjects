package com.fileuploader.util;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fileuploader.businessobjects.MonthlyExpense;

public class JacksonMarshalerTest {

	@Test
	public void testToMonthlyExpenseListJsonString() {
		String month = "Jan";
		String year = "2011";
		BigDecimal preTaxAmount = new BigDecimal("400");
		BigDecimal taxAmount = new BigDecimal("30");
		BigDecimal totalAmount = new BigDecimal("430");
		
		MonthlyExpense entry = new MonthlyExpense();
		entry.setMonth(month);
		entry.setTotalExpense(totalAmount);
		entry.setTotalPreTaxAmount(preTaxAmount);
		entry.setTotalTaxAmount(taxAmount);
		entry.setYear(year);

		List<MonthlyExpense> monthlyExpenseEntries = new ArrayList<MonthlyExpense>();
		monthlyExpenseEntries.add(entry);
		
		String expectedJson = "[{\"month\":\"Jan\",\"year\":\"2011\",\"totalPreTaxAmount\":400,\"totalTaxAmount\":30,\"totalExpense\":430}]"; 
		assertEquals(JacksonMarshaler.toMonthlyExpenseListJsonString(monthlyExpenseEntries), expectedJson);
	}
}
