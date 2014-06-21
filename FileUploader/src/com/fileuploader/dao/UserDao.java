package com.fileuploader.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.fileuploader.businessobjects.User;
import com.fileuploader.util.ValidationHelper;

/**
 * DAO layer to control operations related to User.
 * @author kashifu
 *
 */
public class UserDao {
	private SessionFactory sessionFactory; 
	private final Log logger = LogFactory.getLog(getClass());

	public UserDao(SessionFactory sessionFactory) {
		ValidationHelper.validateForNull(sessionFactory, "sessionFactory");
		this.sessionFactory = sessionFactory;
	}

	public User getUser(String email, String registrationType) {
		ValidationHelper.validateNotBlank(email, "email");
		ValidationHelper.validateNotBlank(registrationType, "registrationType");
		logger.info("extracting user with email " + email + ", regitrationType: " + registrationType + " from database");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("email", email));
		criteria.add(Restrictions.eq("registrationType", registrationType));
		User user = (User) criteria.uniqueResult();	
		logger.info("extracted user " + user + "from database");
		session.getTransaction().commit();
		session.close();
		return user; 
	}
	
	public void insert(User user) {
		ValidationHelper.validateForNull(user, "user");
		logger.info("inserting user " + user + " in database ");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		session.close();
	}
	
	public void update(User user) {
		ValidationHelper.validateForNull(user, "user");
		Session session = sessionFactory.openSession();
		logger.info("updating user " + user + " in database ");
		session.beginTransaction();
		session.update(user);
		session.getTransaction().commit();
		session.close();
	}
	
	
}
