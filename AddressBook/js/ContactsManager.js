define([],	function() {
			'use strict';
			/**
			 * This module is responsible for making service call to fetch contacts and rendering them.
			 */
			var exports = {};

			exports.ContactsManager = function (service, contactsContentHolder) {	
				/**
				 * This module is responsible to make a service call and render the content.
				 */
				this.getExistingContacts = function() {
					// display wait image until service call does not returns
					var waitImageDiv = document.createElement('div'); 
					waitImageDiv.setAttribute("id", 'wait_image');
					
					var waitImage = document.createElement("img");
					waitImage.setAttribute("src", "../images/wait.gif");
					
					waitImageDiv.appendChild(waitImage);
					var waitMessage = document.createElement('p');
					waitMessage.innerHTML = '<b>Loading your contacts</b>';
					
					waitImageDiv.appendChild(waitMessage);
					contactsContentHolder.appendChild(waitImageDiv);
					
					var svc = new service.AddressBookService();					
					svc.getContacts(							
							function(data) {
								contactsContentHolder.removeChild(contactsContentHolder.children[0]);
								if (data.length == 0) {
									contactsContentHolder.innerHTML = '<p>You do not have any contacts!</p>';
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
						var contactsDiv = document.createElement('div');
						contactsDiv.setAttribute("class", "module");

						var contactDisplay = 'FirstName: ' + data[i].firstname
								+ "<br/>" + 'LastName:  ' + data[i].lastname
								+ "<br/>" + 'Phone:     ' + data[i].phone
								+ "<br/>";

						var editButton = document.createElement("input");
						editButton.type = "button";
						editButton.value = "Edit Contact";
						editButton.onclick = copyContactValues(data[i]);

						var p = document.createElement('p');
						p.innerHTML = contactDisplay;
						contactsDiv.appendChild(p);
						contactsDiv.appendChild(editButton);

						contactsContentHolder.appendChild(contactsDiv);
					}
				}
				/**
				 * This function is responsible to provide a callback handler when the 'Edit Contact' button
				 * would be clicked.
				 * @param data The input data callback function
				 * @returns {Function}
				 */
				function copyContactValues(data) {
					return function() {
						copyContactValuesToForm(data);
					}
				}
				
				/**
				 * This is the actual callback function that is called back when 'Edit Contact' button is clicked.
				 * @param contactData Contact data that is copied to Edit Contact form.
				 */
				function copyContactValuesToForm(contactData) {
					$('#editFirstname').val(contactData.firstname);
					$('#editLastname').val(contactData.lastname);
					$('#editPhone').val(contactData.phone);
					$('#editUserId').val(contactData.userId);
					$('#editContactId').val(contactData.contactId);

					// make the form visible now
					$('#editcontact_div').attr('style', 'display:block');
				}
			}

			return exports;

		});