
<!-- Document to Display New User Registration form -->
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head> 
		<script data-main="../js/mainLoginRegister" src="../js/require.js"></script>
		<link rel="stylesheet" href="../css/adBook.css" type="text/css"></link>
	<title> AddressBook </title>		
	</head>
<body>
	<div id="main">
		<div id="topmain"><h2>Register for a New Account</h2></div>
		<!-- Menu -->
		<div class="headermodule"> <a href="../login_form.jsp"> Login </a> </div>
			<p>	<% 
					if(session.getAttribute("message")!= null) {
						out.println(session.getAttribute("message"));
					}
				%>
				
			</p>
		<div id="form-left">
			<form  id="registration_form" method="post" action="add_acct.jsp">
				<div class="module">
					<!-- Registration Fields -->
					<div class="form-row"><span class="form-label">Email* </span><input type="text" id="email" name="email" ></input></div>
					<div class="form-row"><span class="form-label">First Name*  </span><input type="text" id="firstname" name="firstname" ></input> </div>
					<div class="form-row"><span class="form-label">Last Name*  </span><input type="text" id="lastname" name="lastname" ></input> </div>
					<div class="form-row"><span class="form-label">Password* </span><input type="password" id="password" name="password" ></input></div>
					<input type="hidden" id="registrationType" name="registrationType" value="internal" ></input>
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
