package com.addressbook.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.addressbook.businesslogic.ContactsHandler;
import com.addressbook.businesslogic.HandlerInitializer;
import com.addressbook.businessobjects.Contact;
import com.addressbook.businessobjects.User;
import com.addressbook.util.JacksonMarshaler;

public class GetContacts extends HttpServlet {
	private final Log logger = LogFactory.getLog(getClass());
	private ContactsHandler contactsHandler;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		if (isNotLoggedIn(session)) {
			synchronized(session) {
				session.setAttribute("message", "Please login first");
			}
			try {				
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.sendRedirect("../login_form.jsp");
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		} else {
			User user = new User();
			user.setUserId((int)session.getAttribute("userId"));
			List<Contact> contacts = contactsHandler.getContacts(user);
			String contactsJson = JacksonMarshaler.toJsonString(contacts);
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/x-json;charset=UTF-8"); 
			try {
				response.getWriter().write(contactsJson);
			} catch (IOException e) {
				logger.error(e.getMessage());
			} 
		}
	}
	
	//TODO: This constructor should go away after Spring Injections
	public GetContacts() {
		HandlerInitializer initializer = new HandlerInitializer();
		contactsHandler = initializer.getContactsHandler();
	}
	
	private boolean isNotLoggedIn(HttpSession session) {
		return (session == null || session.getAttribute("userId") == null);
	}
	
	public GetContacts(ContactsHandler contactsHandler) {
		this.contactsHandler = contactsHandler;
	}
}
