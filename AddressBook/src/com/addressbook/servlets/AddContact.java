package com.addressbook.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.addressbook.businesslogic.ContactsHandler;
import com.addressbook.businesslogic.HandlerInitializer;
import com.addressbook.businessobjects.Contact;
import com.addressbook.servlets.beans.AddContactBean;
import com.addressbook.util.Converter;

public class AddContact extends HttpServlet {
	private final Log logger = LogFactory.getLog(getClass());
	private ContactsHandler contactsHandler;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		if (isNotLoggedIn(session)) {
			synchronized (session) {
				session.setAttribute("message", "Please login first");
			}
			try {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.sendRedirect("../login_form.jsp");
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		} else {
			AddContactBean bean = (AddContactBean) session.getAttribute("addcontactbean");
			Contact contact = Converter.toContact(bean);
			contactsHandler.createContact(contact);
			response.setStatus(HttpServletResponse.SC_OK);
			try {
				response.sendRedirect("home.jsp");
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	public AddContact() {
		//TODO: Should go away after Spring Inject is used
		HandlerInitializer initializer = new HandlerInitializer();
		contactsHandler = initializer.getContactsHandler();
	}
	
	private boolean isNotLoggedIn(HttpSession session) {
		return (session == null || session.getAttribute("userId") == null);
	}
	
	public AddContact(ContactsHandler contactsHandler) {
		this.contactsHandler = contactsHandler;
	}
}

