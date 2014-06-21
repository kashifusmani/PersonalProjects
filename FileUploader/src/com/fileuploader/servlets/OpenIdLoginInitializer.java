package com.fileuploader.servlets;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.expressme.openid.Association;
import org.expressme.openid.Endpoint;
import org.expressme.openid.OpenIdManager;
/**
 * This servlet is responsible for initiating handshake with OpenId providers (Google, etc)
 * to facilitate OpenId login.
 * @author kashifu
 *
 */
public class OpenIdLoginInitializer extends HttpServlet {

	private OpenIdManager manager;
	private final Log logger = LogFactory.getLog(getClass());
	static final String ATTR_MAC = "openid_mac";
    static final String ATTR_ALIAS = "openid_alias";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		String op = request.getParameter("op"); //Get the Open ID Provider
		logger.info("Got an OpenId login request for: " + op);
		Endpoint endpoint = null;
		if ("Google".equals(op)) {
			endpoint = manager.lookupEndpoint("Google");
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			synchronized (session) {
				session.setAttribute("message", "Invalid OP identifer");
			}
			try {
				response.sendRedirect("login_form.jsp");
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		Association association = manager.lookupAssociation(endpoint);
        request.getSession().setAttribute(ATTR_MAC, association.getRawMacKey());
        request.getSession().setAttribute(ATTR_ALIAS, endpoint.getAlias());
        String url = manager.getAuthenticationUrl(endpoint, association);
        try {
			response.sendRedirect(url);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}

	}
	
	 public void init(ServletConfig config) {
		 try {
			super.init(config);
		} catch (ServletException e) {
			logger.error(e.getMessage(), e);
		}
		 ServletContext context = getServletContext();
		 String  realm = context.getInitParameter("realm");
		 String  returnTo = context.getInitParameter("returnTo");
		 manager = new OpenIdManager();
		 manager.setRealm(realm);
		 manager.setReturnTo(returnTo);
	 }
}
