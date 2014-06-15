package com.addressbook.servlets;


import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.addressbook.businesslogic.ContactsHandler;
import com.addressbook.businessobjects.Contact;
import com.addressbook.servlets.beans.AddContactBean;

@RunWith(MockitoJUnitRunner.class)
public class AddContactTest {
	private AddContact servletInTest;
	@Mock
	private ContactsHandler mockContactsHandler;
	@Mock
	private HttpServletRequest mockRequest;
	@Mock
	private HttpServletResponse mockResponse;
	@Mock
	private HttpSession mockSession;
	
	@Before
	public void setup() {
		servletInTest = new AddContact(mockContactsHandler);
	}
	
	@Test
	public void testPostAddContactWithoutLogIn() {
		when(mockRequest.getSession(true)).thenReturn(mockSession);
		servletInTest.doPost(mockRequest, mockResponse);
		ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(mockResponse, times(1)).setStatus(intCaptor.capture());
		 
		assertEquals(intCaptor.getValue().intValue(), HttpServletResponse.SC_FORBIDDEN);
	}
	
	@Test
	public void testPostAddContactHappyPath() {
		String firstname = "firstname";
		String lastname = "lastname";
		long phone = 123123123;
		int userId = 1;
		when(mockRequest.getSession(true)).thenReturn(mockSession);
		when(mockSession.getAttribute("userId")).thenReturn(userId);
		
		AddContactBean bean = new AddContactBean();
		bean.setFirstname(firstname);
		bean.setLastname(lastname);
		bean.setPhone(phone);
		bean.setUserId(userId);
		
		when(mockSession.getAttribute("addcontactbean")).thenReturn(bean);
		servletInTest.doPost(mockRequest, mockResponse);
		ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(mockResponse, times(1)).setStatus(intCaptor.capture());
		 
		assertEquals(intCaptor.getValue().intValue(), HttpServletResponse.SC_OK);
		verify(mockContactsHandler, times(1)).createContact(any(Contact.class));
	}
}
