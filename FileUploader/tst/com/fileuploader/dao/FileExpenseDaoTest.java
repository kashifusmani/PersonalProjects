package com.fileuploader.dao;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.hibernate.SQLQuery;
import org.mockito.runners.MockitoJUnitRunner;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fileuploader.businessobjects.MonthlyExpense;
import com.fileuploader.businessobjects.User;
import com.fileuploader.businessobjects.UserFileMap;
import com.fileuploader.businessobjects.ExpenseEntry;

@RunWith(MockitoJUnitRunner.class)
public class FileExpenseDaoTest {
	private FileExpenseDao daoInTest;
	@Mock
	private SessionFactory mockSessionFactory;
	@Mock
	private Session mockSession;
	@Mock
	private Transaction mockTransaction;
	@Mock
	private SQLQuery queryMock;

	@Before
	public void setup() {
		daoInTest = new FileExpenseDao(mockSessionFactory);
		when(mockSessionFactory.openSession()).thenReturn(mockSession);
		when(mockSession.getTransaction()).thenReturn(mockTransaction);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInsertNewMappingNullUser() {
		daoInTest.insertNewMapping(null);
	}

	@Test
	public void testInsertNewMappingHappyPath() {
		User user = new User();
		int userId = 1;
		user.setUserId(userId);
		daoInTest.insertNewMapping(user);
		ArgumentCaptor<UserFileMap> mapCaptor = ArgumentCaptor.forClass(UserFileMap.class);
		verify(mockSession, times(1)).save(mapCaptor.capture());

		UserFileMap map = mapCaptor.getValue();
		assertEquals(map.getUserId(), userId);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInsertExpenseEntriesNullInput() {
		daoInTest.insertExpenseEntries(null);
	}

	@Test
	public void testInsertExpenseEntriesHappyPath() {
		List<ExpenseEntry> entries = new ArrayList<ExpenseEntry>();
		entries.add(new ExpenseEntry());
		entries.add(new ExpenseEntry());

		daoInTest.insertExpenseEntries(entries);
		verify(mockSession, times(2)).save(any(ExpenseEntry.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetLatestFileMapNullUser() {
		daoInTest.getLatestFileMap(null);
	}

	@Test
	public void testGetLatestFileMapHappyPath() {
		int userId = 1;
		User user = new User();
		user.setUserId(1);
		int fileId = 2;

		when(mockSession.createSQLQuery(any(String.class))).thenReturn(queryMock);
		when(queryMock.uniqueResult()).thenReturn(new Integer(fileId));

		UserFileMap map = daoInTest.getLatestFileMap(user);
		assertEquals(map.getId(), fileId);
		assertEquals(map.getUserId(), userId);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetExpenseEntriesNullUser() {
		daoInTest.getExpenseEntries(null);
	}

	@Test
	public void testGetExpenseEntries() {
		UserFileMap map = new UserFileMap();
		map.setId(1);

		String month = "January";
		String year = "2012";
		BigDecimal totalPreTaxAmount = new BigDecimal("350");
		BigDecimal totalTaxAmount = new BigDecimal("31.06");
		BigDecimal totalExpense = new BigDecimal("381.06");

		Object[] row = { month, year, totalPreTaxAmount, totalTaxAmount, totalExpense };
		List<Object[]> rows = new ArrayList<Object[]>();
		rows.add(row);

		when(mockSession.createSQLQuery(any(String.class))).thenReturn(queryMock);
		when(queryMock.list()).thenReturn(rows);

		List<MonthlyExpense> result = daoInTest.getExpenseEntries(map);
		assertNotNull(result);
		assertEquals(result.size(), 1);
		MonthlyExpense exp = result.get(0);
		assertEquals(exp.getMonth(), month);
		assertEquals(exp.getTotalExpense(), totalExpense);
		assertEquals(exp.getTotalPreTaxAmount(), totalPreTaxAmount);
		assertEquals(exp.getTotalTaxAmount(), totalTaxAmount);
		assertEquals(exp.getYear(), year);
	}

}
