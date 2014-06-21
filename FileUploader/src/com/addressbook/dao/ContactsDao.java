package com.addressbook.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.addressbook.businessobjects.Contact;
import com.addressbook.businessobjects.User;
import com.addressbook.util.ValidationHelper;
/**
 * DAO layer to control operations related to Contacts.
 * @author kashifu
 *
 */
public class ContactsDao {
	private SessionFactory sessionFactory;
	private final Log logger = LogFactory.getLog(getClass());
	
	public ContactsDao(SessionFactory sessionFactory) {
		ValidationHelper.validateForNull(sessionFactory, "sessionFactory");
		this.sessionFactory = sessionFactory;
	}

	public void insert(Contact contact) {
		ValidationHelper.validateForNull(contact, "contact");
		logger.info("inserting contact " + contact + " in database ");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(contact);
		session.getTransaction().commit();
		session.close();
	}

	public void update(Contact contact) {
		ValidationHelper.validateForNull(contact, "contact");
		logger.info("updating contact " + contact + " in database ");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.update(contact);
		session.getTransaction().commit();
		session.close();
	}

	@SuppressWarnings("unchecked")
	public List<Contact> getContacts(User user) {
		ValidationHelper.validateForNull(user, "user");
		logger.info("retrieving contacts for user: " + user);
		List<Contact> contacts = new ArrayList<Contact>();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Contact.class);
		criteria.add(Restrictions.eq("userId", user.getUserId()));		
		contacts = criteria.list();
		logger.info("retrieved contacts: " + contacts );
		session.getTransaction().commit();
		session.close();
		return contacts;
	}
}
