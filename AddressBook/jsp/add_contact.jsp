<jsp:useBean id="addcontactbean" scope="session" class="com.addressbook.servlets.beans.AddContactBean"/>
<jsp:setProperty name="addcontactbean" property="*"/>
<!-- invoke controller Servlet to process the request -->
<jsp:forward page="/AddContact"/>