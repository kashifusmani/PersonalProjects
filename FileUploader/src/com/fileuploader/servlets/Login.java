package com.fileuploader.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fileuploader.businesslogic.HandlerInitializer;
import com.fileuploader.businesslogic.UserAccountHandler;
import com.fileuploader.businessobjects.User;
import com.fileuploader.servlets.beans.LoginBean;
/**
 * This servlet is responsible for handling authenitcation requests for internally registared users.
 * @author kashifu
 *
 */
public class Login extends HttpServlet {
	private final Log logger = LogFactory.getLog(getClass());
	private UserAccountHandler userAcctHandler; 
	private final String loginFailure = "Incorrect Email or Password. Please try again.";

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		LoginBean loginBean = (LoginBean) session.getAttribute("loginbean");
		logger.info("Got login request from email: " + loginBean.getEmail()
				+ ", regitrationType: " + loginBean.getRegistrationType());
		boolean authenticate = userAcctHandler.authenticate(
				loginBean.getEmail(), loginBean.getRegistrationType(),
				loginBean.getPassword());
		if (authenticate) {
			synchronized (session) { // synchronize the session prior to modification
				//Possible improvement can be made here
				User user = userAcctHandler.getUser(loginBean.getEmail(), loginBean.getRegistrationType());
				session.setAttribute("userId", user.getUserId());
				session.setAttribute("email", user.getEmail());
			}
			response.setStatus(HttpServletResponse.SC_OK);
				try {
					response.sendRedirect("home.jsp");
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			synchronized (session) {
				session.setAttribute("message", loginFailure);
			}
			try {
				response.sendRedirect("../login_form.jsp");
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}

	//TODO: This constructor should go away after Spring Injections
	public Login() {
		HandlerInitializer initializer = new HandlerInitializer();
		userAcctHandler = initializer.getUserAccountHandler();
	}
	
	public Login(UserAccountHandler userAcctHandler) {
		this.userAcctHandler = userAcctHandler;
	}
}
