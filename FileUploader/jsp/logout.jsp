<% 
	if(null != session.getAttribute("userId")) {
		session.invalidate();		
		response.sendRedirect("../login_form.jsp");	
	}
	
%>