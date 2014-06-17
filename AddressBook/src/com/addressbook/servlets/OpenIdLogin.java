package com.addressbook.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.expressme.openid.Authentication;
import org.expressme.openid.OpenIdException;
import org.expressme.openid.OpenIdManager;

import com.addressbook.businesslogic.HandlerInitializer;
import com.addressbook.businesslogic.UserAccountHandler;
import com.addressbook.businessobjects.User;
/**
 * This servlet is responsible for handling authentication requests for OpenId users. 
 * @author kashifu
 *
 */
public class OpenIdLogin extends HttpServlet {
	private static final long ONE_HOUR = 3600000L;
	static final long TWO_HOUR = ONE_HOUR * 2L;
	private final Log logger = LogFactory.getLog(getClass());
	static final String ATTR_MAC = "openid_mac";
    static final String ATTR_ALIAS = "openid_alias";
    private OpenIdManager manager;
    private UserAccountHandler userAcctHandler;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		try {
			checkNonce(request.getParameter("openid.response_nonce"));
			// get authentication:
	        byte[] mac_key = (byte[]) request.getSession().getAttribute(ATTR_MAC);
	        String alias = (String) request.getSession().getAttribute(ATTR_ALIAS);
	        Authentication authentication = manager.getAuthentication(request, mac_key, alias);
	        String identity = authentication.getIdentity();
	        String email = authentication.getEmail();
	        logger.info("Successfully logged in OPENID user: " + email);
	        logger.info("identity: " + identity);
	        User user = userAcctHandler.getUser(email, "openid");
	        if (user == null) {
	        	user = new User();
	        	user.setEmail(email);
		        user.setRegistrationType("openid");
	        	userAcctHandler.createOpenIdAccount(user);
	        }	        
	        session.setAttribute("userId", user.getUserId());
			session.setAttribute("email", user.getEmail());
			response.setStatus(HttpServletResponse.SC_OK);
			try {
				response.sendRedirect("jsp/home.jsp");
			} catch (IOException e) {
				logger.error(e.getMessage());
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			synchronized (session) {
				session.setAttribute("message", "Invalid OP identifer");
			}
			try {
				response.sendRedirect("login_form.jsp");
			} catch (IOException ex) {
				logger.error(ex.getMessage());
			}
		}
	}

	private void checkNonce(String nonce) {
		logger.info("Nonce is " + nonce);
		// check response_nonce to prevent replay-attack:
		if (nonce == null || nonce.length() < 20) {
			throw new OpenIdException("Verification failed");
		}
		long nonceTime = getNonceTime(nonce);
		long diff = System.currentTimeMillis() - nonceTime;
		if (diff < 0) {
			diff = (-diff);
		}
		if (diff > ONE_HOUR) {
			throw new OpenIdException("Bad nonce time.");
		}
		if (nonceExist(nonce)) {
			logger.warn("POSSIBLE REPLAY ATTACK.");
			throw new OpenIdException("Nonce already exists");
		}
		storeNonce(nonce, nonceTime + TWO_HOUR);
	}

	private long getNonceTime(String nonce) {
		try {
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(
					nonce.substring(0, 19) + "+0000").getTime();
		} catch (ParseException e) {
			throw new OpenIdException("Bad nonce time.");
		}
	}

	private boolean nonceExist(String nonce) {
		// TODO: check if nonce is exist in database:
		return false;
	}

	private void storeNonce(String nonce, long expires) {
		// TODO: store nonce in database:
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
		 HandlerInitializer initializer = new HandlerInitializer();
		 userAcctHandler = initializer.getUserAccountHandler();
	 }
	 
	//ServletContext needs a no argument constructor 
	public OpenIdLogin() {} 
	 
	//This is for facilitating mock testing
	public OpenIdLogin(OpenIdManager openIdManager, UserAccountHandler userAccountHandler) {
		this.manager = openIdManager;
		this.userAcctHandler = userAccountHandler;
	}
}
