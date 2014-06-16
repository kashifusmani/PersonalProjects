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
      		
      		var serviceResponseData = [{"contactId":4,"firstname":"Kashif","lastname":"Omani","phone":1100190029,"userId":4}];
      		options.success(serviceResponseData);
      		
      		equal(contactsContentHolderMock.children.length, 1); //assert the correct div is created
      		//div has a <p> element in it, get it
      		
      		var elemP = contactsContentHolderMock.children[0].children[0];
      		//get text inside elemP
      		var text = elemP.innerHTML;
      		console.log(text);
      	});
      };
        return {run: run}
    }
);
