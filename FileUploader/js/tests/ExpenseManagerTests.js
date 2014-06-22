"use strict";
define(['../lib/jquery-latest','../ExpenseManager', '../service'], function(_, expenseManagerModule, serviceModule) {
		
	  var run = function() {
      	test('queries service to get expenses', function() {
      		var contentHolderMock = document.createElement('div');
      		contentHolderMock.id = 'someid';
      		
      		var testString = null;
      		$.ajax = function(param) {
      			testString = "Service got called";
      		}
      		
      		var expenseManager = new expenseManagerModule.ExpenseManager(serviceModule, contentHolderMock);      		
      		expenseManager.getLastUploadExpenseSummary();
      		      		     		
      		//assert service.getLastUploadExpenseSummary was called; so testString should be set
      		equal(testString, "Service got called");
      	});
      	
      	test('response data from service call is rendered correctly', function() {
      		var contentHolderMock = document.createElement('div');
      		contentHolderMock.id = 'someid';
      		contentHolderMock.appendChild(document.createElement('p'));
      		
      		var mockTable = document.createElement('table');
      		contentHolderMock.appendChild(mockTable);
      		
      		var options = null;
      		$.ajax = function(param) {
      			options = param;
      		}
      		
      		var expenseManager = new expenseManagerModule.ExpenseManager(serviceModule, contentHolderMock);      		
      		expenseManager.getLastUploadExpenseSummary();
      		
      		var month = "October";
      		var year = "2012";
      		var totalPreTaxAmount = 400;
      		var totalTaxAmount = 30.00;
      		var totalExpense = 430;
      		var serviceResponseData = [{"month":month,"year": year,"totalPreTaxAmount":totalPreTaxAmount,"totalTaxAmount":totalTaxAmount,"totalExpense":totalExpense}];
      		//make the success call
      		options.success(serviceResponseData);
      		
      		//assert a new row is added to table
      		equal(mockTable.children.length, 1); 
      		
      		var rowElement = mockTable.children[0];
      		//assert 5 columns are in this row
      		equal(rowElement.children.length, 5);
      		
      		//assert all data is rendered as it should
      		var monthElement = rowElement.children[0];
      		equal(monthElement.innerHTML, month);
      		
      		var yearElement = rowElement.children[1];
      		equal(yearElement.innerHTML, year);
      		
      		var totalPreTaxAmountElement = rowElement.children[2];
      		equal(totalPreTaxAmountElement.innerHTML, totalPreTaxAmount);
      		
      		var totalTaxAmountElement = rowElement.children[3];
      		equal(totalTaxAmountElement.innerHTML, totalTaxAmount);
      		
      		var totalExpenseElement = rowElement.children[4];
      		equal(totalExpenseElement.innerHTML, totalExpense);
      	});
      };
        return {run: run}
    }
);
