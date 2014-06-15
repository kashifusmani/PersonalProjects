package com.addressbook.businesslogic;

import java.util.List;

import com.addressbook.businessobjects.Contact;
import com.addressbook.businessobjects.User;
import com.addressbook.dao.ContactsDao;
import com.addressbook.util.ValidationHelper;

public class ContactsHandler {
	private ContactsDao contactsDao;

	public ContactsHandler(ContactsDao contactsDao) {
		ValidationHelper.validateForNull(contactsDao, "contactsDao");
		this.contactsDao = contactsDao;
	} 

	public void createContact(Contact contact) {
		ValidationHelper.validateForNull(contact, "contact");
		contactsDao.insert(contact);
	}
	
	public void updateContact(Contact contact) {
		ValidationHelper.validateForNull(contact, "contact");
		contactsDao.update(contact);
	}
	
	public List<Contact> getContacts(User user) {
		ValidationHelper.validateForNull(user, "user");
		List<Contact> contacts = contactsDao.getContacts(user);
		return contacts;
	}
}
