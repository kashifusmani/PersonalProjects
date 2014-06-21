"use strict";
define(['../lib/jquery-latest','../ContactsManager', '../service'], function(_, contactsManagerModule, serviceModule) {
		
	  var run = function() {
      	test('queries service to getContacts', function() {
      		var contactsContentHolderMock = document.createElement('div');
      		contactsContentHolderMock.id = 'someid';
      		
      		var testString = null;
      		$.ajax = function(param) {
      			testString = "Service got called";
      		}
      		
      		var contactsManager = new contactsManagerModule.ContactsManager(serviceModule, contactsContentHolderMock);      		
      		contactsManager.getExistingContacts();
      		      		     		
      		//assert service.getContacts was called; so testString should be set
      		equal(testString, "Service got called");
      	});
      	
      	test('response data from service call is rendered correctly', function() {
      		var contactsContentHolderMock = document.createElement('div');
      		contactsContentHolderMock.id = 'someid';
      		
      		var options = null;
      		$.ajax = function(param) {
      			options = param;
      		}
      		
      		var contactsManager = new contactsManagerModule.ContactsManager(serviceModule, contactsContentHolderMock);
      		
      		contactsManager.getExistingContacts();
      		
      		var contactId = 4;
      		var firstname = "Kashif";
      		var lastname = "Omani";
      		var phone = 4161112211;
      		var userId = 3;
      		var serviceResponseData = [{"contactId":contactId,"firstname": firstname,"lastname":lastname,"phone":phone,"userId":userId}];
      		options.success(serviceResponseData);
      		
      		//assert the correct div is created
      		equal(contactsContentHolderMock.children.length, 1); 
      		
      		//div should have a <p> element and a Edit Contact Button children
      		equal(contactsContentHolderMock.children[0].children.length, 2);
      		
      		//div has a <p> element in it, get it      		
      		var elemP = contactsContentHolderMock.children[0].children[0];
      		console.log(elemP);
      		//get text inside elemP
      		var text = elemP.innerHTML;
      		ok(text.indexOf(firstname) >= 0);
      		ok(text.indexOf(lastname) >= 0);
      		ok(text.indexOf(phone) >= 0);
      		
      		
      		//div should also have a "Edit Contact" button
      		equal(contactsContentHolderMock.children[0].children[1].type, "button");
      		equal(contactsContentHolderMock.children[0].children[1].value, "Edit Contact");
      	});
      };
        return {run: run}
    }
);
