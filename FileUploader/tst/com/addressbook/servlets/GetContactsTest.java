package com.addressbook.servlets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
import com.addressbook.businessobjects.User;
import com.addressbook.util.JacksonMarshaler;

@RunWith(MockitoJUnitRunner.class)
public class GetContactsTest {
	private GetContacts servletInTest;
	
	@Mock
	private ContactsHandler mockContactsHandler;
	@Mock
	private HttpServletRequest mockRequest;
	@Mock
	private HttpServletResponse mockResponse;
	@Mock
	private HttpSession mockSession;
	@Mock
	private PrintWriter mockPrintWriter;
	@Before
	public void setup() {
		servletInTest = new GetContacts(mockContactsHandler);
	}
	
	@Test
	public void testDoGetWithoutLogIn() {
		when(mockRequest.getSession(true)).thenReturn(mockSession);
		servletInTest.doGet(mockRequest, mockResponse);
		ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(mockResponse, times(1)).setStatus(intCaptor.capture());
		 
		assertEquals(intCaptor.getValue().intValue(), HttpServletResponse.SC_FORBIDDEN);
	}
	
	@Test
	public void testDoGetHappyPath() throws IOException {
		int contactId = 1;
		String firstname = "name";
		String lastname = "last";
		long phone = 12312341;
		int userId = 2;
		
		Contact contact = new Contact();
		contact.setContactId(contactId);
		contact.setFirstname(firstname);
		contact.setLastname(lastname);
		contact.setPhone(phone);
		contact.setUserId(userId);
		
		List<Contact> contactsList = new ArrayList<Contact>();
		contactsList.add(contact);
		
		when(mockRequest.getSession(true)).thenReturn(mockSession);
		when(mockSession.getAttribute("userId")).thenReturn(userId);
		when(mockContactsHandler.getContacts(any(User.class))).thenReturn(contactsList);
		when(mockResponse.getWriter()).thenReturn(mockPrintWriter);
		
		servletInTest.doGet(mockRequest, mockResponse);
		
		ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(mockResponse, times(1)).setStatus(intCaptor.capture());
		 
		assertEquals(intCaptor.getValue().intValue(), HttpServletResponse.SC_OK);
		
		ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockPrintWriter, times(1)).write(stringCaptor.capture());
		
		assertEquals(stringCaptor.getValue(), JacksonMarshaler.toJsonString(contactsList));
	}
}
