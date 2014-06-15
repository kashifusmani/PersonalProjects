<jsp:useBean id="userbean" scope="session" class="com.addressbook.servlets.beans.RegistrationBean"/>
<!-- pick up all the properties from the registration_form submit form -->
<jsp:setProperty name="userbean" property="*"/>
<!-- invoke controller Servlet to process the request -->
<jsp:forward page="/AddAccount"/>