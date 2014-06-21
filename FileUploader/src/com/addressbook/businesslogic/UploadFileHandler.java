package com.addressbook.businesslogic;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.addressbook.businessobjects.ExpenseEntry;
import com.addressbook.businessobjects.User;
import com.addressbook.businessobjects.UserFileMap;
import com.addressbook.dao.FileExpenseDao;
import com.addressbook.servlets.beans.ExpenseCsvEntry;
import com.addressbook.util.ValidationHelper;

/**
 * This class is responsible for dealing with business logic of uploading a file.
 * @author kashifu
 *
 */
public class UploadFileHandler {
	private FileExpenseDao fileExpenseDao;
	private final Log logger = LogFactory.getLog(getClass());
	
	public UploadFileHandler(FileExpenseDao fileExpenseDao) {
		ValidationHelper.validateForNull(fileExpenseDao, "fileExpenseDao");
		this.fileExpenseDao = fileExpenseDao;
	}
	
	public void processFile (User user, FileItem fileItem) {
		//First, lets parse the user input
		List<ExpenseCsvEntry> csvEntriesList;
		try {
			csvEntriesList = parseFileItem(fileItem);
		} catch (RuntimeException e) {
			throw e;
		}
		//All is good with user input, we can proceed to insert file entries in database
		
		//Insert a new entry in USER_FILE_MAPPINGS table
		UserFileMap map = fileExpenseDao.insertNewMapping(user);
		
		//Convert user input to desired format in database and then insert
		List<ExpenseEntry> expenseEntriesList = toExpenseEntryList(map.getId(), csvEntriesList);
		
		fileExpenseDao.insertExpenseEntries(expenseEntriesList);		
	}
	
	private List<ExpenseCsvEntry> parseFileItem (FileItem fileItem) {
		logger.info("Parsing a new fileItem");
		ICsvBeanReader beanReader = null;
        List<ExpenseCsvEntry> csvEntriesList = new ArrayList<ExpenseCsvEntry>();
        InputStream uploadedStream;
        try {
			uploadedStream = fileItem.getInputStream();
			beanReader = new CsvBeanReader(new InputStreamReader(uploadedStream),
	                CsvPreference.STANDARD_PREFERENCE);
			//Map the columns of Input file to ExpenseCsvEntry objects, in the order they appear in file
			
			final String[] nameMapping = new String[]{"expenseDateString","category",
					"employeeName", "employeeAddress", "expenseDescription",
					"preTaxAmountString", "taxName",  "taxAmountString"};
            //just read the header, so that it doesn't get mapped to object
            beanReader.getHeader(true);
            ExpenseCsvEntry emp;
            
            while ((emp = beanReader.read(ExpenseCsvEntry.class, nameMapping)) != null) {
                csvEntriesList.add(emp);
            }
            beanReader.close();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);	
			throw new RuntimeException(e);
		}
        return csvEntriesList;
	}
	
	private List<ExpenseEntry> toExpenseEntryList(int fileId, List<ExpenseCsvEntry> csvEntriesList) {
		List<ExpenseEntry> expenseEntryList = new ArrayList<ExpenseEntry>();
		for (ExpenseCsvEntry entry: csvEntriesList) {
			expenseEntryList.add(toExpenseEntry(fileId, entry));
		}
		return expenseEntryList;
	}
	
	/**
	 * toExpenseEntry converts entries from ExpenseCsvEntry to ExpenseEntry
	 * @param fileId the fileId to which ExpenseEntry belongs
	 * @param expenseCsvEntry the bean containing source information
	 * @return
	 */
	private ExpenseEntry toExpenseEntry(int fileId, ExpenseCsvEntry expenseCsvEntry) {
		ExpenseEntry expenseEntry = new ExpenseEntry();
		Date expenseDate = parseDate(expenseCsvEntry.getExpenseDateString());
		BigDecimal preTaxAmount = null;
		BigDecimal taxAmount = null;
		
			logger.info("ExpenseDate: " + expenseDate);		
			String preTaxAmountString = parseAmount(expenseCsvEntry.getPreTaxAmountString());
			preTaxAmount = new BigDecimal(preTaxAmountString);
			logger.info("PretaxAmount: " + preTaxAmount);
	
			String taxAmountString = parseAmount(expenseCsvEntry.getTaxAmountString());
			taxAmount = new BigDecimal(taxAmountString);
			logger.info("taxAmount: " + taxAmount);
		
		expenseEntry.setCategory(expenseCsvEntry.getCategory());
		expenseEntry.setEmployeeAddress(expenseCsvEntry.getEmployeeAddress());
		expenseEntry.setEmployeeName(expenseCsvEntry.getEmployeeName());
		expenseEntry.setExpenseDate(expenseDate);
		expenseEntry.setExpenseDescription(expenseCsvEntry.getExpenseDescription());
		expenseEntry.setPreTaxAmout(preTaxAmount);
		expenseEntry.setTaxAmount(taxAmount);
		expenseEntry.setTaxName(expenseCsvEntry.getTaxName());
		expenseEntry.setFileId(fileId);
		return expenseEntry;
	}
	
	/**
	 * parseDate parses the input date in format "MM/dd/yyyy"
	 * @param dateString The string representation of the date to be parsed.
	 * @return
	 */
	private Date parseDate(String dateString) {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date expenseDate;
		try {
			java.util.Date utilDate = df.parse(dateString);
			expenseDate = new Date(utilDate.getTime());			
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		return expenseDate;
	}
	
	/**
	 * parseAmount parses a monetary value by doing following steps
	 * 1. Trims any beginning or tailing double quotes ""
	 * 2. Trims any beginning or trailing white spaces
	 * 3. Removes any commas ( 1,000 -> 1000)
	 * @param amount the value to be parsed
	 * @return
	 */
	private String parseAmount (String amount) {
		amount = trimQuotesIfPresent(amount);
		amount = amount.trim(); //remove beginning and trailing white space
		try {
			//remove commas "," in the number
			Number number = NumberFormat.getNumberInstance(Locale.US).parse(amount);
			return number.toString();
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * trimQuotesIfPresent trims any beginning or ending double quotes
	 * @param input the input to be sanitized
	 * @return
	 */
	private String trimQuotesIfPresent(String input) {
		if (input.startsWith("\"")) {
			input = input.substring(1);
		}
		if (input.endsWith("\"")) {
			input = input.substring(0, input.length() - 1);
		}
		return input;
	}

}
