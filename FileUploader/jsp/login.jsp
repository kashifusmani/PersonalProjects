<jsp:useBean id="loginbean" scope="session" class="com.addressbook.servlets.beans.LoginBean"/>
<!-- pick up all the properties from the registration_form submit form -->
<jsp:setProperty name="loginbean" property="*"/>
<!-- invoke controller Servlet to process the request -->
<jsp:forward page="/Login"/>