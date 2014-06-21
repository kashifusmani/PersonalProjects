<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<!-- Home page of the application -->
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head> 
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title> FileUploader </title>		
		<script data-main="../js/mainHome" src="../js/require.js"></script>
		<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"></link>
		<link rel="stylesheet" href="../css/uploader.css" type="text/css"></link>
	
	</head>
<body>
	<div id="main">
		<div id="mainHeader"><h2>Welcome to Wave. Please upload your data.</h2>
		</div>
		<div class="headermodule"> 
				<p>  <% if(session.getAttribute("email")!= null) {
							out.print("Hello "+session.getAttribute("email")+"\n"); 
						} else {
							session.setAttribute("message", "Please Login");
				       	 	response.sendRedirect("../login_form.jsp");
						} 
					%> 
				</p>
		<!-- Menu -->
			<a href="logout.jsp"> Logout </a> 	
			<p>  <% if(session.getAttribute("message")!= null) {
							out.print(session.getAttribute("message")); 
							session.setAttribute("message", null);
						}
					%> 
			</p>		
		</div>
		<div id="userActions">	
			<p>Select file to upload:</p>		
			<form  id="fileUploadForm" action="../UploadFile" method="post" enctype="multipart/form-data">
				<div class="module">
					<div class="form-row"><span class="form-label"/><input type="file" id="filename" name="filename" /> </div>
					<br/>
					<div class="form-row">
						<input type="submit" value="Upload">
					</div>
				</div>
			</form>			
		</div>
		
		<div id="summary_div" >
			<table id="summaryTable" class="module" style="display:none">
				<tr>
					<th><b>Month</b></th>
					<th><b>Year</b></th>
					<th><b>Total Pre-tax amount</b></th>
					<th><b>Total tax amount</b></th>
					<th><b>Grand Total</b></th>
				</tr>
			</table>					
		</div>
			
	</div>			

</body>
</html>
