package com.fileuploader.servlets.beans;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExpenseCsvEntryTest {
	@Test
	public void testSetterAndGetter() {
		String expenseDateString = "12/1/2013";
		String category = "Travel";
		String employeeName = "Don Draper";
		String address = "\"783 Park Ave, New York, NY 10021\"";
		String expenseDescription = "Taxi ride";
		String preTaxAmountString = " 350.00 ";
		String taxName = "NY Sales tax";
		String taxAmountString = "31.06";
		
		ExpenseCsvEntry entry = new ExpenseCsvEntry();
		entry.setCategory(category);
		entry.setEmployeeAddress(address);
		entry.setEmployeeName(employeeName);
		entry.setExpenseDateString(expenseDateString);
		entry.setExpenseDescription(expenseDescription);
		entry.setPreTaxAmountString(preTaxAmountString);
		entry.setTaxAmountString(taxAmountString);
		entry.setTaxName(taxName);
		
		assertEquals(entry.getCategory(), category);
		assertEquals(entry.getEmployeeAddress(), address);
		assertEquals(entry.getEmployeeName(), employeeName);
		assertEquals(entry.getExpenseDateString(), expenseDateString);
		assertEquals(entry.getExpenseDescription(), expenseDescription);
		assertEquals(entry.getPreTaxAmountString(), preTaxAmountString);
		assertEquals(entry.getTaxAmountString(), taxAmountString);
		assertEquals(entry.getTaxName(), taxName);
	}
}
