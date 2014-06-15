define([],	function() {
			'use strict';
			var exports = {};

			exports.ContactsManager = function (service, contactsContentHolder) {	
				
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
								contactsContentHolder.removeChild(document.getElementById("wait_image"));
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
					console.log('going to return');
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

				function copyContactValues(data) {
					return function() {
						copyContactValuesToForm(data);
					}
				}

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