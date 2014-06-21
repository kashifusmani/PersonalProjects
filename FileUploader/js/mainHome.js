"use strict";
require(["lib/jquery-latest", "lib/jquery.validate", "lib/form-validation", "ExpenseManager", "service"], 
		function(_1,             _2,                      _3,            expenseManagerModule, service) {
	/**
	 * Module to wire up dependencies that are need by home.jsp
	 */
    var summaryDiv = document.getElementById('summary_div');
    var expenseManager = new expenseManagerModule.ExpenseManager(service, summaryDiv);
	$(document).ready(expenseManager.getLastUploadExpenseSummary()); 
});