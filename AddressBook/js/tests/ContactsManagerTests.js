"use strict";
define(['../lib/jquery-latest','../ContactsManager'], function(_, contactsManagerModule) {
		
		var contactsContentHolderMock = document.createElement('div');
		contactsContentHolderMock.id = 'someid';
		
		var svcMock = new Object();
		svcMock.AddressBookService = function () {
			
			this.getContacts = function () {
				return [{"contactId":4,"firstname":"Kashif","lastname":"Omani","phone":1100190029,"userId":4}];
			}		
		};
		
		var contactsManager = new contactsManagerModule.ContactsManager(svcMock, contactsContentHolderMock);
		
        var run = function() {
        	test('queries service to getContacts', function() {
        		contactsManager.getExistingContacts();
        		
        		//assert service.getContacts was called
        		equal(contactsContentHolderMock.children.length, 1);
        		console.log(contactsContentHolderMock.children);
        	});
        };
        return {run: run}
    }
);
