package com.fileuploader.businessobjects;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.sql.Date;

import org.junit.Test;

public class ExpenseEntryTest {
	@Test
	public void testSettersAndGetters() {
		int fileId = 1;
		int entryId = 4;
		Date expenseDate = new Date(System.currentTimeMillis());
		String category = "Travel";
		String employeeName = "Don Draper";
		String address = "783 Park Ave, New York, NY 10021";
		String expenseDescription = "Taxi ride";
		BigDecimal preTaxAmount = new BigDecimal("350");
		String taxName = "NY Sales tax";
		BigDecimal taxAmount = new BigDecimal("31.06");

		ExpenseEntry entry = new ExpenseEntry();
		entry.setCategory(category);
		entry.setEmployeeAddress(address);
		entry.setEmployeeName(employeeName);
		entry.setExpenseDate(expenseDate);
		entry.setExpenseDescription(expenseDescription);
		entry.setFileId(fileId);
		entry.setPreTaxAmout(preTaxAmount);
		entry.setTaxAmount(taxAmount);
		entry.setTaxName(taxName);
		entry.setExpenseEntryId(entryId);

		assertEquals(entry.getCategory(), category);
		assertEquals(entry.getEmployeeAddress(), address);
		assertEquals(entry.getEmployeeName(), employeeName);
		assertEquals(entry.getExpenseDate(), expenseDate);
		assertEquals(entry.getExpenseDescription(), expenseDescription);
		assertEquals(entry.getFileId(), fileId);
		assertEquals(entry.getPreTaxAmount(), preTaxAmount);
		assertEquals(entry.getTaxAmount(), taxAmount);
		assertEquals(entry.getTaxName(), taxName);
		assertEquals(entry.getExpenseEntryId(), entryId);
	}
}
