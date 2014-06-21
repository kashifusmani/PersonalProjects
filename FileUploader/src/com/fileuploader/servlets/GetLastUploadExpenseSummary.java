package com.fileuploader.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fileuploader.businesslogic.FileExpenseHandler;
import com.fileuploader.businesslogic.HandlerInitializer;
import com.fileuploader.businessobjects.MonthlyExpense;
import com.fileuploader.businessobjects.User;
import com.fileuploader.util.JacksonMarshaler;

public class GetLastUploadExpenseSummary extends HttpServlet {
	private final Log logger = LogFactory.getLog(getClass());
	private FileExpenseHandler fileExpenseHandler;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		if (isNotLoggedIn(session)) {
			synchronized (session) {
				session.setAttribute("message", "Please login first");
			}
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.sendRedirect("../login_form.jsp");
		} else {
			User user = new User();
			user.setUserId((Integer) session.getAttribute("userId"));

			List<MonthlyExpense> monthlyExpenses = fileExpenseHandler.getLastUploadEntries(user);
			String json = JacksonMarshaler.toMonthlyExpenseListJsonString(monthlyExpenses);
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/x-json;charset=UTF-8");
			try {
				response.getWriter().write(json);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
	}

	public GetLastUploadExpenseSummary() {
		HandlerInitializer initializer = new HandlerInitializer();
		fileExpenseHandler = initializer.getFileExpenseHandler();
	}

	private boolean isNotLoggedIn(HttpSession session) {
		return (session == null || session.getAttribute("userId") == null);
	}

	public GetLastUploadExpenseSummary(FileExpenseHandler fileExpenseHandler) {
		this.fileExpenseHandler = fileExpenseHandler;
	}
}
