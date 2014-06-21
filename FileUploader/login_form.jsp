
<!-- Document to Display New User Registration form -->
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head> 
		<script data-main="js/mainLoginRegister" src="js/require.js"></script>
		<link rel="stylesheet" href="css/uploader.css" type="text/css"></link>
	<title> FileUploader </title>		
	</head>
<body>
	<div id="main">
		<div id="mainHeader"><h2>Login</h2></div>
		<!-- Menu -->
		<div class="headermodule"> <a href="jsp/registration_form.jsp"> Register for a new account </a>
			<p>	<% 
					if(session.getAttribute("message")!= null) {
						out.println(session.getAttribute("message"));
						session.setAttribute("message", null);
					}
				%>
				
			</p>
		 </div>
			
		<div id="form-left">
			<form  id="login_form" method="post" action="jsp/login.jsp">			
				<div class="module">
					<!-- Registration Fields -->
					<div class="form-row"><span class="form-label">Email* </span><input type="text" id="email" name="email" ></input></div>
					<div class="form-row"><span class="form-label">Password* </span><input type="password" id="password" name="password" ></input></div>
					<input type="hidden" id="registrationType" name="registrationType" value="internal" ></input>
					<div class="form-row">
						<input type="submit" value="Submit">&nbsp;
						<input type="reset">
					</div>
				</div>
			</form>
			<div class="module">
				<p> Sign in using <a href="OpenIdLoginInitializer?op=Google">Google Account</a></p>
			</div> 
		</div>
		
    </div>
	</body>
</html>
