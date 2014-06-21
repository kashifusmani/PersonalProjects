package com.fileuploader.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fileuploader.businesslogic.HandlerInitializer;
import com.fileuploader.businesslogic.UploadFileHandler;
import com.fileuploader.businessobjects.User;

/**
 * Servlet responsible to handle requests for uploading a file.
 * 
 * @author kashifu
 * 
 */
public class UploadFile extends HttpServlet {
	private final Log logger = LogFactory.getLog(getClass());
	private UploadFileHandler handler;
	private String LOGIN_PAGE = "../login_form.jsp";
	private String HOME_PAGE = "jsp/home.jsp";

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		if (isNotLoggedIn(session)) {
			setMessage(session, "Please login first");
			sendResponse(HttpServletResponse.SC_FORBIDDEN, response, LOGIN_PAGE);			
		}
		User user = new User();
		user.setUserId((Integer) session.getAttribute("userId"));
		
		// checks if the request actually contains upload file
		if (!ServletFileUpload.isMultipartContent(request)) {
			setMessage(session, "Invalid Request");
			sendResponse(HttpServletResponse.SC_BAD_REQUEST, response, HOME_PAGE);			
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = new ArrayList<FileItem>();
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			logger.error(e.getMessage(), e);
			setMessage(session, "Invalid Request");
			sendResponse(HttpServletResponse.SC_BAD_REQUEST, response, HOME_PAGE);
		}
		
		Iterator<FileItem> iter = items.iterator();
		boolean exception = false;
		while (iter.hasNext()) {
			FileItem item = iter.next();
			if (!item.isFormField()) {
				try {
					handler.processFile(user, item);
				} catch (RuntimeException e) {
					exception = true;
					setMessage(session, "Invalid Request");
					sendResponse(HttpServletResponse.SC_BAD_REQUEST, response, HOME_PAGE);
				}
			}
		}
		if (!exception) {
			setMessage(session, "File uploaded successfully");
			sendResponse(HttpServletResponse.SC_OK, response, HOME_PAGE);
		}		
	}

	// TODO: This constructor should go away after Spring Injections
	public UploadFile() {
		HandlerInitializer initializer = new HandlerInitializer();
		handler = initializer.getUploadFileHandler();
	}

	private boolean isNotLoggedIn(HttpSession session) {
		return (session == null || session.getAttribute("userId") == null);
	}
	
	private void setMessage (HttpSession session, String message) {
		synchronized (session) {
			session.setAttribute("message", message);
		}
	}
	
	private void sendResponse(int responseCode, HttpServletResponse response, String redirectLocation) {
		response.setStatus(responseCode);
		try {
			response.sendRedirect(redirectLocation);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
		
}
