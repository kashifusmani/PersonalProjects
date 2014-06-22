define([],	function() {
			'use strict';
			/**
			 * This module is responsible for making service call to fetch expense data and rendering it.
			 */
			var exports = {};

			exports.ExpenseManager = function (service, contentHolder) {	
						
				this.getLastUploadExpenseSummary = function() {
					// display wait image until service call does not returns
					var waitImageDiv = document.createElement('div'); 
					waitImageDiv.setAttribute("id", 'wait_image');
					
					var waitImage = document.createElement("img");
					waitImage.setAttribute("src", "../images/wait.gif");
					
					waitImageDiv.appendChild(waitImage);
					var waitMessage = document.createElement('p');
					waitMessage.innerHTML = '<b>Calculating expense summary of last uploaded file...</b>';
					
					waitImageDiv.appendChild(waitMessage);
					contentHolder.appendChild(waitImageDiv);
					
					var svc = new service.AddressBookService();	
					svc.getLastUploadExpenseSummary(							
							function(data) {
								contentHolder.removeChild(contentHolder.children[2]);
								if (data.length == 0) {
									contentHolder.innerHTML = '<p>No data exists for last upload!</p>';
								} else {
									renderData(data);
								}
							},
							function(data) { 
								console.log("Error occurred while fetching contacts!");
							}
					);
				}
				
				function renderData(data) {					
					for ( var i = 0; i < data.length; i++) {
						var expenseRow = document.createElement('tr');

						var month = document.createElement('td');
						month.innerHTML = data[i].month;
						
						var year = document.createElement('td');
						year.innerHTML = data[i].year;
						
						var totalPreTaxAmount = document.createElement('td');
						totalPreTaxAmount.innerHTML = data[i].totalPreTaxAmount;
						
						var totalTaxAmount = document.createElement('td');
						totalTaxAmount.innerHTML = data[i].totalTaxAmount;
						
						var totalExpense = document.createElement('td');
						totalExpense.innerHTML = data[i].totalExpense;

						expenseRow.appendChild(month);
						expenseRow.appendChild(year);
						expenseRow.appendChild(totalPreTaxAmount);
						expenseRow.appendChild(totalTaxAmount);
						expenseRow.appendChild(totalExpense);
								
						contentHolder.children[1].appendChild(expenseRow);		
						
					}
					contentHolder.children[1].setAttribute('style', 'display:block');
				}
			}
			
			

			return exports;

		});