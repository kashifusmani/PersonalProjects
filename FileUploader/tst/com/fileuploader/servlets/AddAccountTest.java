package com.fileuploader.servlets;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.fileuploader.businesslogic.UserAccountHandler;
import com.fileuploader.businessobjects.User;
import com.fileuploader.servlets.AddAccount;
import com.fileuploader.servlets.beans.RegistrationBean;

@RunWith(MockitoJUnitRunner.class)
public class AddAccountTest {
	private AddAccount servletInTest;
	@Mock
	private UserAccountHandler mockUserAcctHandler;
	@Mock
	private HttpServletRequest mockRequest;
	@Mock
	private HttpServletResponse mockResponse;
	@Mock
	private HttpSession mockSession;
	
	@Before
	public void setUp() {
		servletInTest = new AddAccount(mockUserAcctHandler);
	}
	
	@Test
	public void testPostUserExists() throws IOException {
		 when(mockRequest.getSession(true)).thenReturn(mockSession);
		 
		 RegistrationBean registrationBean = new RegistrationBean();
		 String email = "a@a.com";
		 String firstname = "firstname";
		 String lastname = "lastname";
		 String password = "password";
		 String registrationType = "internal";
		 registrationBean.setEmail(email);
		 registrationBean.setFirstname(firstname);
		 registrationBean.setLastname(lastname);
		 registrationBean.setPassword(password);
		 registrationBean.setRegistrationType(registrationType);
		 
		 when(mockSession.getAttribute("userbean")).thenReturn(registrationBean);
		 when(mockUserAcctHandler.userExists(email, registrationType)).thenReturn(true);
		 servletInTest.doPost(mockRequest, mockResponse);
		 
		 ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(Integer.class);
		 verify(mockResponse, times(1)).setStatus(intCaptor.capture());
		 
		 assertEquals(intCaptor.getValue().intValue(), HttpServletResponse.SC_CONFLICT);
	}
	
	@Test
	public void testPostUserNotExists() throws IOException {
		 when(mockRequest.getSession(true)).thenReturn(mockSession);
		 
		 RegistrationBean registrationBean = new RegistrationBean();
		 String email = "a@a.com";
		 String firstname = "firstname";
		 String lastname = "lastname";
		 String password = "password";
		 String registrationType = "internal";
		 registrationBean.setEmail(email);
		 registrationBean.setFirstname(firstname);
		 registrationBean.setLastname(lastname);
		 registrationBean.setPassword(password);
		 registrationBean.setRegistrationType(registrationType);
		 
		 when(mockSession.getAttribute("userbean")).thenReturn(registrationBean);
		 when(mockUserAcctHandler.userExists(email, registrationType)).thenReturn(false);
		 doNothing().when(mockResponse).sendRedirect(any(String.class));
		 servletInTest.doPost(mockRequest, mockResponse);
		 
		 ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(Integer.class);
		 verify(mockResponse, times(1)).setStatus(intCaptor.capture());
		 
		 assertEquals(intCaptor.getValue().intValue(), HttpServletResponse.SC_OK);
		 verify(mockUserAcctHandler, times(1)).createAccount(any(User.class), any(String.class));
	}
}
