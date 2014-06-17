"use strict";
require(["lib/jquery-latest", "lib/jquery.validate", "lib/form-validation", "ContactsManager", "service"], 
		function(_1,             _2,                      _3,            contactsManagerModule, service) {
	/**
	 * Module to wire up dependencies that are need by home.jsp
	 */
    var contactsDiv = document.getElementById('existingContacts');
    var contactsManager = new contactsManagerModule.ContactsManager(service, contactsDiv);
	$(document).ready(contactsManager.getExistingContacts()); 
});