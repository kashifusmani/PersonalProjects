<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<!-- Home page of the application -->
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head> 
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title> AddressBook </title>		
		<script data-main="../js/mainHome" src="../js/require.js"></script>
		<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"></link>
		<link rel="stylesheet" href="../css/adBook.css" type="text/css"></link>
	
	</head>
<body>
	<div id="main">
		<div id="topmain"><h2>Welcome to AddressBook. Cloud based contacts manager.</h2>
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
		</div>
		<div id="left">	
			<p>Add new contact</p>		
			<form  id="addContact_form" action="add_contact.jsp" method="post">
				<div class="module">
					<div class="form-row"><span class="form-label">First Name*  </span><input type="text" id="firstname" name="firstname" ></input> </div>
					<div class="form-row"><span class="form-label">Last Name*  </span><input type="text" id="lastname" name="lastname" ></input> </div>
					<div class="form-row"><span class="form-label">Phone* </span><input type="phone" id="phone" name="phone" ></input></div>
					<input type="hidden" id ="userId" name="userId" value="<%=session.getAttribute("userId")%>" />
					<div class="form-row">
						<input type="submit" value="Submit">&nbsp;
						<input type="reset">
					</div>
				</div>
			</form>
			<p>Existing Contacts</p>
			<div  id="existingContacts"> 
				
			</div>
		</div>
		<div id="editcontact_div" style="display:none">
			<p>Edit contact</p>	
			<form  id="editcontact_form" action="edit_contact.jsp" method="post">
				<div class="module">
					<div class="form-row"><span class="form-label">First Name*  </span><input type="text" id="editFirstname" name="firstname" ></input> </div>
					<div class="form-row"><span class="form-label">Last Name*  </span><input type="text" id="editLastname" name="lastname" ></input> </div>
					<div class="form-row"><span class="form-label">Phone* </span><input type="phone" id="editPhone" name="phone" ></input></div>
					<input type="hidden" id ="editUserId" name="userId" />
					<input type="hidden" id ="editContactId" name="contactId" />
					<div class="form-row">
						<input type="submit" value="Submit">&nbsp;
						<input type="reset">
					</div>
				</div>
			</form>
		</div>			
		
	</div>
	</body>
</html>
