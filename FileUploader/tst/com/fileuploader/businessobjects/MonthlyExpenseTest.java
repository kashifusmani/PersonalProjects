package com.fileuploader.businessobjects;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

public class MonthlyExpenseTest {
	@Test
	public void testSettersAndGetters() {
		String month = "January";
		String year = "2012";
		BigDecimal totalPreTaxAmount = new BigDecimal("350");
		BigDecimal totalTaxAmount = new BigDecimal("31.06");
		BigDecimal totalExpense = new BigDecimal("381.06");
		
		MonthlyExpense expense = new MonthlyExpense();
		expense.setMonth(month);
		expense.setTotalExpense(totalExpense);
		expense.setTotalPreTaxAmount(totalPreTaxAmount);
		expense.setTotalTaxAmount(totalTaxAmount);
		expense.setYear(year);
		
		assertEquals(expense.getMonth(), month);
		assertEquals(expense.getTotalExpense(), totalExpense);
		assertEquals(expense.getTotalPreTaxAmount(), totalPreTaxAmount);
		assertEquals(expense.getTotalTaxAmount(), totalTaxAmount);
		assertEquals(expense.getYear(), year);
	}
}
