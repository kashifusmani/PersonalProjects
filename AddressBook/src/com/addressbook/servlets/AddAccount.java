package com.addressbook.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.addressbook.businesslogic.HandlerInitializer;
import com.addressbook.businesslogic.UserAccountHandler;
import com.addressbook.servlets.beans.RegistrationBean;
import com.addressbook.util.Converter;

public class AddAccount extends HttpServlet {

	private final Log logger = LogFactory.getLog(getClass());
	private UserAccountHandler userAcctHandler;
	private final String userNameExistsError = "The username you entered already exists, please try a different one! ";
	private final String userRegistrationSuccessMessage = "You have successfully registered. Please login now.";

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		RegistrationBean registerBean = (RegistrationBean) session
				.getAttribute("userbean");
		logger.info("Got request for account creation:  " + registerBean);

		boolean userExists = userAcctHandler.userExists(registerBean.getEmail(), registerBean.getRegistrationType());
		if (userExists) {
			try {
				response.setStatus(HttpServletResponse.SC_CONFLICT);
				synchronized (session) {
					session.setAttribute("message", userNameExistsError);
				}
				response.sendRedirect("registration_form.jsp");
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		} else {
			userAcctHandler.createAccount(Converter.toUser(registerBean), registerBean.getPassword());
			response.setStatus(HttpServletResponse.SC_OK);
			synchronized (session) {
				session.setAttribute("message", userRegistrationSuccessMessage);
			}
			try {				
				response.sendRedirect("../login_form.jsp");
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}

	}
	
	//TODO: This constructor should go away after Spring Injections
	public AddAccount() {
		HandlerInitializer initializer = new HandlerInitializer();
		userAcctHandler = initializer.getUserAccountHandler();
	}
	
	public AddAccount(UserAccountHandler userAcctHandler) {
		this.userAcctHandler = userAcctHandler;
	}
}
