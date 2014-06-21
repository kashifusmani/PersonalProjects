package com.fileuploader.servlets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fileuploader.businesslogic.FileExpenseHandler;
import com.fileuploader.businessobjects.MonthlyExpense;
import com.fileuploader.businessobjects.User;
import com.fileuploader.util.JacksonMarshaler;

@RunWith(MockitoJUnitRunner.class)
public class GetLastUploadExpenseSummaryTest {
	private GetLastUploadExpenseSummary servletInTest;
	@Mock
	private FileExpenseHandler mockFileExpenseHandler;
	@Mock
	private HttpServletRequest mockRequest;
	@Mock
	private HttpServletResponse mockResponse;
	@Mock
	private HttpSession mockSession;
	@Mock
	private PrintWriter mockPrintWriter;

	@Before
	public void setUp() {
		servletInTest = new GetLastUploadExpenseSummary(mockFileExpenseHandler);
	}

	@Test
	public void testDoGetWithoutLogin() throws ServletException, IOException {
		when(mockRequest.getSession(true)).thenReturn(mockSession);
		servletInTest.doGet(mockRequest, mockResponse);
		ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(mockResponse, times(1)).setStatus(intCaptor.capture());

		assertEquals(intCaptor.getValue().intValue(), HttpServletResponse.SC_FORBIDDEN);
	}

	@Test
	public void testDoGetHappyPath() throws ServletException, IOException {
		int userId = 1;
		when(mockRequest.getSession(true)).thenReturn(mockSession);
		when(mockSession.getAttribute("userId")).thenReturn(userId);

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

		when(mockFileExpenseHandler.getLastUploadEntries(any(User.class))).thenReturn(monthlyExpenseEntries);
		when(mockResponse.getWriter()).thenReturn(mockPrintWriter);

		servletInTest.doGet(mockRequest, mockResponse);

		ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(mockResponse, times(1)).setStatus(intCaptor.capture());

		assertEquals(intCaptor.getValue().intValue(), HttpServletResponse.SC_OK);
		ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockPrintWriter, times(1)).write(stringCaptor.capture());

		assertEquals(stringCaptor.getValue(), JacksonMarshaler.toMonthlyExpenseListJsonString(monthlyExpenseEntries));

	}
}
