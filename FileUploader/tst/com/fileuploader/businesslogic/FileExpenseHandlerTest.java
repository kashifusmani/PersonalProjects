package com.fileuploader.businesslogic;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fileuploader.businessobjects.MonthlyExpense;
import com.fileuploader.businessobjects.User;
import com.fileuploader.businessobjects.UserFileMap;
import com.fileuploader.dao.FileExpenseDao;

@RunWith(MockitoJUnitRunner.class)
public class FileExpenseHandlerTest {
	private FileExpenseHandler handlerInTest;
	@Mock
	private FileExpenseDao mockDao;

	@Before
	public void before() {
		handlerInTest = new FileExpenseHandler(mockDao);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorNullDao() {
		new FileExpenseHandler(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetLastUploadEntriesNullUser() {
		handlerInTest.getLastUploadEntries(null);
	}

	@Test
	public void testGetLastUploadEntriesEmptyResponse() {
		User user = new User();
		user.setUserId(1);
		when(mockDao.getLatestFileMap(user)).thenReturn(null);

		List<MonthlyExpense> result = handlerInTest.getLastUploadEntries(user);
		assertEquals(result.size(), 0);
		verify(mockDao, never()).getExpenseEntries(any(UserFileMap.class));
	}

	@Test
	public void testGetLastUploadEntriesHappyPath() {
		int userId = 1;
		int fileId = 2;
		User user = new User();
		user.setUserId(userId);
		UserFileMap map = new UserFileMap();
		map.setId(fileId);

		String month = "Jan";
		String year = "2011";
		BigDecimal preTaxAmount = new BigDecimal("400");
		BigDecimal taxAmount = new BigDecimal("30");
		BigDecimal totalAmount = new BigDecimal("430");

		when(mockDao.getLatestFileMap(user)).thenReturn(map);
		MonthlyExpense entry = new MonthlyExpense();
		entry.setMonth(month);
		entry.setTotalExpense(totalAmount);
		entry.setTotalPreTaxAmount(preTaxAmount);
		entry.setTotalTaxAmount(taxAmount);
		entry.setYear(year);

		List<MonthlyExpense> monthlyExpenseEntries = new ArrayList<MonthlyExpense>();
		monthlyExpenseEntries.add(entry);

		when(mockDao.getExpenseEntries(map)).thenReturn(monthlyExpenseEntries);

		List<MonthlyExpense> result = handlerInTest.getLastUploadEntries(user);
		assertEquals(result, monthlyExpenseEntries);
	}
}
