<jsp:useBean id="editcontactbean" scope="session" class="com.addressbook.servlets.beans.EditContactBean"/>
<jsp:setProperty name="editcontactbean" property="*"/>
<!-- invoke controller Servlet to process the request -->
<jsp:forward page="/EditContact"/>