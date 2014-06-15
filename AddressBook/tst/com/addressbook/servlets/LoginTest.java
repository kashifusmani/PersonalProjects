package com.addressbook.servlets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.addressbook.businesslogic.UserAccountHandler;
import com.addressbook.businessobjects.User;
import com.addressbook.servlets.beans.LoginBean;

@RunWith(MockitoJUnitRunner.class)
public class LoginTest {
	private Login servletInTest;
	@Mock
	private UserAccountHandler mockUserAccountHandler;
	@Mock
	private HttpServletRequest mockRequest;
	@Mock
	private HttpServletResponse mockResponse;
	@Mock
	private HttpSession mockSession;	
	@Before
	public void setup() {
		servletInTest = new Login(mockUserAccountHandler);
	}
	
	@Test
	public void testDoPostAuthenticationFailure() {
		when(mockRequest.getSession(true)).thenReturn(mockSession);
		
		String email = "a@a.com";
		String password = "password";
		String registrationType = "internal";
		LoginBean bean = new LoginBean();
		bean.setEmail(email);
		bean.setPassword(password);
		bean.setRegistrationType(registrationType);
		
		when(mockSession.getAttribute("loginbean")).thenReturn(bean);
		when(mockUserAccountHandler.authenticate(email, registrationType, password)).thenReturn(false);
		
		servletInTest.doPost(mockRequest, mockResponse);
		ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(mockResponse, times(1)).setStatus(intCaptor.capture());
		 
		assertEquals(intCaptor.getValue().intValue(), HttpServletResponse.SC_UNAUTHORIZED);
	}
	
	@Test
	public void testDoPostAuthenticationPass() {
		when(mockRequest.getSession(true)).thenReturn(mockSession);
		
		String email = "a@a.com";
		String password = "password";
		String registrationType = "internal";
		LoginBean bean = new LoginBean();
		bean.setEmail(email);
		bean.setPassword(password);
		bean.setRegistrationType(registrationType);
		
		when(mockSession.getAttribute("loginbean")).thenReturn(bean);
		when(mockUserAccountHandler.authenticate(email, registrationType, password)).thenReturn(true);
		
		int userId = 1;
		User user = new User();
		user.setEmail(email);
		user.setRegistrationType(registrationType);
		user.setUserId(userId);
		
		when(mockUserAccountHandler.getUser(email, registrationType)).thenReturn(user);
		
		servletInTest.doPost(mockRequest, mockResponse);
		ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(mockResponse, times(1)).setStatus(intCaptor.capture());
		 
		assertEquals(intCaptor.getValue().intValue(), HttpServletResponse.SC_OK);
		
		ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
		verify(mockSession, times(2)).setAttribute(any(String.class), stringCaptor.capture());
		
		List<String> capturedValues = stringCaptor.getAllValues();
		assertTrue(capturedValues.contains(email));
	}
}
