package com.fileuploader.businesslogic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fileuploader.businessobjects.ExpenseEntry;
import com.fileuploader.businessobjects.User;
import com.fileuploader.businessobjects.UserFileMap;
import com.fileuploader.dao.FileExpenseDao;
import com.fileuploader.servlets.beans.ExpenseCsvEntry;

@RunWith(MockitoJUnitRunner.class)
public class UploadFileHandlerTest {
	private UploadFileHandler handlerInTest;
	private static final int threshold = 16;
	private String expenseDateString = "12/1/2013";
	private String category = "Travel";
	private String employeeName = "Don Draper";
	private String address = "\"783 Park Ave, New York, NY 10021\"";
	private String expenseDescription = "Taxi ride";
	private String preTaxAmountString = " 350.00 ";
	private String taxName = "NY Sales tax";
	private String taxAmountString = "31.06";

	@Mock
	private FileExpenseDao mockDao;

	@Before
	public void before() {
		handlerInTest = new UploadFileHandler(mockDao);
	}

	/**
	 * In this test case, we create a File Item with csv values and expect
	 * parseFileItem method to return a correct ExpenseCsvEntry bean
	 */
	@Test
	public void testParseFileItemHappyPath() {
		FileItem item = createFileItem();
		List<ExpenseCsvEntry> entries = handlerInTest.parseFileItem(item);
		assertNotNull(entries);
		assertEquals(entries.size(), 1);
		ExpenseCsvEntry entry = entries.get(0);
		assertEquals(entry.getCategory(), category);
		assertEquals("\"" + entry.getEmployeeAddress() + "\"", address);
		assertEquals(entry.getEmployeeName(), employeeName);
		assertEquals(entry.getExpenseDateString(), expenseDateString);
		assertEquals(entry.getExpenseDescription(), expenseDescription);
		assertEquals(entry.getPreTaxAmountString(), preTaxAmountString);
		assertEquals(entry.getTaxAmountString(), taxAmountString);
		assertEquals(entry.getTaxName(), taxName);
	}

	/**
	 * Create a FileItem with mock csv values.
	 */
	private FileItem createFileItem() {
		FileItemFactory factory = new DiskFileItemFactory(threshold, null);
		String header = "date,category,employee name,employee address,expense description,pre-tax amount,tax name,tax amount";
		String textFieldName = "someName";
		String content = header;
		content += "\n";
		content += expenseDateString + "," + category + "," + employeeName + "," + address + "," + expenseDescription
				+ "," + preTaxAmountString + "," + taxName + "," + taxAmountString;

		FileItem item = factory.createItem(textFieldName, "text", false, "");
		try {
			OutputStream os = item.getOutputStream();
			os.write(content.getBytes());
			os.close();
		} catch (IOException e) {
			//
		}

		return item;
	}

	@Test
	public void testToExpenseEntryList() {
		FileItem item = createFileItem();
		int fileId = 1;
		List<ExpenseCsvEntry> entries = handlerInTest.parseFileItem(item);
		List<ExpenseEntry> expenseEntries = handlerInTest.toExpenseEntryList(fileId, entries);

		assertNotNull(expenseEntries);
		assertEquals(expenseEntries.size(), 1);

		ExpenseEntry entry = expenseEntries.get(0);
		assertEquals(entry.getCategory(), category);
		assertEquals("\"" + entry.getEmployeeAddress() + "\"", address);
		assertEquals(entry.getEmployeeName(), employeeName);
		assertEquals(entry.getExpenseDescription(), expenseDescription);
		assertEquals(entry.getTaxName(), taxName);
		assertEquals(entry.getFileId(), fileId);
		assertEquals(entry.getPreTaxAmount(), new BigDecimal("350"));
		assertEquals(entry.getTaxAmount(), new BigDecimal(taxAmountString));

		// check that the date get parsed correctly
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date date = null;
		try {
			date = new Date(df.parse(expenseDateString).getTime());
		} catch (ParseException e) {
			//
		}
		assertEquals(entry.getExpenseDate(), date);
	}

	@Test
	public void testParseDate() {
		Date date = handlerInTest.parseDate(expenseDateString);
		assertNotNull(date);
	}

	@Test
	public void testParseAmount() {
		String testAmount = "\"  1,000  \"";
		String expected = "1000";

		assertEquals(handlerInTest.parseAmount(testAmount), expected);
	}

	@Test
	public void testTrimQuotesIfPresent() {
		String strWithQuotes = "\" astring \"";
		assertEquals(handlerInTest.trimQuotesIfPresent(strWithQuotes), " astring ");

		assertEquals(handlerInTest.trimQuotesIfPresent("noquotes"), "noquotes");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProcessFileNullUser() {
		handlerInTest.processFile(null, createFileItem());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProcessFileNullFileItem() {
		handlerInTest.processFile(new User(), null);
	}

	@Test
	public void testProcessFileHappyPath() {
		int userId = 1;
		int fileId = 2;
		User user = new User();
		user.setUserId(userId);
		UserFileMap map = new UserFileMap();
		map.setId(fileId);
		map.setUserId(userId);

		when(mockDao.insertNewMapping(user)).thenReturn(map);
		handlerInTest.processFile(user, createFileItem());

		verify(mockDao, times(1)).insertNewMapping(user);
		verify(mockDao, times(1)).insertExpenseEntries(any(List.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullDao() {
		new UploadFileHandler(null);
	}
}
