package com.addressbook.servlets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.expressme.openid.Authentication;
import org.expressme.openid.OpenIdManager;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import com.addressbook.businesslogic.UserAccountHandler;
import com.addressbook.businessobjects.User;
import com.mysql.fabric.xmlrpc.base.Array;

@RunWith(MockitoJUnitRunner.class)
public class OpenIdLoginTest {
	private OpenIdLogin servletInTest;
	@Mock
	private OpenIdManager mockManager;
	@Mock
	private UserAccountHandler mockUserAccountHandler;
	@Mock
	private HttpServletRequest mockRequest;
	@Mock
	private HttpServletResponse mockResponse;
	@Mock
	private HttpSession mockSession;
	
	private String attrMac = "openid_mac";
	private String attrAlias = "openid_alias";
	private String nonceResponse = "openid.response_nonce";
	private String email = "a@a.com";
	@Before
	public void setup() {
		servletInTest = new OpenIdLogin(mockManager, mockUserAccountHandler);
		when(mockRequest.getSession()).thenReturn(mockSession);
		
		byte[] mac_key = {};
		String alias = "";
		Authentication authentication = new Authentication();
		
		authentication.setEmail(email);
		
		when(mockSession.getAttribute(attrMac)).thenReturn(mac_key);
		when(mockSession.getAttribute(attrAlias)).thenReturn(alias);
		when(mockManager.getAuthentication(mockRequest, mac_key, alias)).thenReturn(authentication);
		when(mockRequest.getSession(true)).thenReturn(mockSession);
	}
	
	@Test
	public void testUserDoesNotExists() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date now = new Date(System.currentTimeMillis());
		String strDate = sdf.format(now);
		strDate += "randomNonce";
		when(mockRequest.getParameter(nonceResponse)).thenReturn(strDate);
		when(mockUserAccountHandler.getUser(email, "openid")).thenReturn(null);
		
		
		servletInTest.doGet(mockRequest, mockResponse);
		//assert user got created
		verify(mockUserAccountHandler, times(1)).createOpenIdAccount(any(User.class));
		ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(mockResponse, times(1)).setStatus(intCaptor.capture());
		 
		assertEquals(intCaptor.getValue().intValue(), HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testUserExists() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date now = new Date(System.currentTimeMillis());
		String strDate = sdf.format(now);
		strDate += "randomNonce";
		when(mockRequest.getParameter(nonceResponse)).thenReturn(strDate);
		when(mockUserAccountHandler.getUser(email, "openid")).thenReturn(new User());
		
		
		servletInTest.doGet(mockRequest, mockResponse);
		//assert user did not get created
		verify(mockUserAccountHandler, times(0)).createOpenIdAccount(any(User.class));
		ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(mockResponse, times(1)).setStatus(intCaptor.capture());
		 
		assertEquals(intCaptor.getValue().intValue(), HttpServletResponse.SC_OK);
	}
	
	@Test
	public void testBadNonce() {
		//We will set the nonce in our timezone, as opposed to UTC.
		//this makes the nonce check fail
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		Date now = new Date(System.currentTimeMillis());
		String strDate = sdf.format(now);
		strDate += "randomNonce";
		when(mockRequest.getParameter(nonceResponse)).thenReturn(strDate);
		
		
		servletInTest.doGet(mockRequest, mockResponse);
		ArgumentCaptor<Integer> intCaptor = ArgumentCaptor.forClass(Integer.class);
		verify(mockResponse, times(1)).setStatus(intCaptor.capture());
		 
		assertEquals(intCaptor.getValue().intValue(), HttpServletResponse.SC_BAD_REQUEST);
	}
	
}
