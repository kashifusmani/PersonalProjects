package com.addressbook.util;

import com.addressbook.businessobjects.Contact;
import com.addressbook.businessobjects.User;
import com.addressbook.servlets.beans.AddContactBean;
import com.addressbook.servlets.beans.EditContactBean;
import com.addressbook.servlets.beans.RegistrationBean;

public class Converter {

	public static User toUser(RegistrationBean registerBean) {
		User user = new User();
		user.setEmail(registerBean.getEmail());
		user.setFirstname(registerBean.getFirstname());
		user.setLastname(registerBean.getLastname());
		user.setRegistrationType(registerBean.getRegistrationType());
		return user;
	}
	
	public static Contact toContact(AddContactBean bean) {
		Contact contact = new Contact();
		contact.setFirstname(bean.getFirstname());
		contact.setLastname(bean.getLastname());
		contact.setPhone(bean.getPhone());
		contact.setUserId(bean.getUserId());
		return contact;
	}
	
	public static Contact toContact(EditContactBean bean) {
		Contact contact = new Contact();
		contact.setFirstname(bean.getFirstname());
		contact.setLastname(bean.getLastname());
		contact.setPhone(bean.getPhone());
		contact.setUserId(bean.getUserId());
		contact.setContactId(bean.getContactId());
		return contact;
	}
}
