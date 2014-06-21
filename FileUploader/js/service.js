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
		 * Get Last upload expense summary from the backend service
		 * 
		 * @param onSuccess on Success, a list of contacts as json will be passed to this callback.
		 * @param onError Error callback
		 */		
		this.getLastUploadExpenseSummary = function (onSuccess, onError) {
			$.ajax({
				url: '../GetLastUploadExpenseSummary',
				type: 'GET',
				success: onSuccess,
				error: onError
			});
		}
	};
	
	return exports;
});