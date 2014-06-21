package com.addressbook.servlets;

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

import com.addressbook.businesslogic.HandlerInitializer;
import com.addressbook.businesslogic.UploadFileHandler;
import com.addressbook.businessobjects.User;

/**
 * Servlet responsible to handle requests for uploading a file.
 * 
 * @author kashifu
 * 
 */
public class UploadFile extends HttpServlet {
	private final Log logger = LogFactory.getLog(getClass());
	private UploadFileHandler handler;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		if (isNotLoggedIn(session)) {
			synchronized (session) {
				session.setAttribute("message", "Please login first");
			}
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.sendRedirect("../login_form.jsp");
		}
		User user = new User();
		user.setUserId((Integer) session.getAttribute("userId"));
		//REFACTOR SENDING MESAGE
		// checks if the request actually contains upload file
		if (!ServletFileUpload.isMultipartContent(request)) {
			synchronized (session) {
				session.setAttribute("message", "Invalid Request");
			}
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.sendRedirect("jsp/home.jsp");
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = new ArrayList<FileItem>();
		try {
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			logger.error(e.getMessage(), e);
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.sendRedirect("jsp/home.jsp");
		}
		synchronized (session) {
			session.setAttribute("message", "File uploaded successfully");
		}
		response.setStatus(HttpServletResponse.SC_OK);
		
		Iterator<FileItem> iter = items.iterator();
		
		while (iter.hasNext()) {
			FileItem item = iter.next();
			if (!item.isFormField()) {
				try {
					handler.processFile(user, item);
				} catch (RuntimeException e) {
					synchronized (session) {
						session.setAttribute("message", "Invalid Input!");
					}
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				}
			}
		}
		response.sendRedirect("jsp/home.jsp");
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
