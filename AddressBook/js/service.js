define([], function() {
	'use strict';
	
	var exports = {};
	
	exports.AddressBookService = function () {
		
		this.getContacts = function (onSuccess, onError) {
			$.ajax({
				url: '../GetContacts',
				type: 'GET',
				success: onSuccess,
				error: onError
			});
		}		
	};
	
	return exports;
});