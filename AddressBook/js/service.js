define([], function() {
	'use strict';
	/**
	 * Warpper for backend service
	 * 
	 * @module js/service
	 */
	var exports = {};
	
	exports.AddressBookService = function () {
		
		/**
		 * Get Contacts from the backend service
		 * 
		 * @param onSuccess on Success, a list of contacts as json will be passed to this callback.
		 * @param onError Error callback
		 */
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