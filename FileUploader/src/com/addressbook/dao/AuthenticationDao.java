package com.addressbook.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.criterion.Restrictions;

import com.addressbook.businessobjects.Auth;
import com.addressbook.businessobjects.User;
import com.addressbook.util.ValidationHelper;

/**
 * DAO layer to control operations related to user authentication.
 * @author kashifu
 *
 */
public class AuthenticationDao {
	private SessionFactory sessionFactory;
	private final Log logger = LogFactory.getLog(getClass());
	public AuthenticationDao(SessionFactory sessionFactory) {
		ValidationHelper.validateForNull(sessionFactory, "sessionFactory");
		this.sessionFactory = sessionFactory;
	}

	public Auth getAuth(User user) {
		ValidationHelper.validateForNull(user, "user");
		logger.info("extracting auth information for user: " + user );
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Auth.class);
		criteria.add(Restrictions.eq("userId", user.getUserId()));
		Auth auth = (Auth) criteria.uniqueResult();
		session.getTransaction().commit();
		session.close();
		return auth;  
	}

	public void save(User user, String pwdHash) {
		ValidationHelper.validateForNull(user, "user");
		ValidationHelper.validateNotBlank(pwdHash, "pwdHash");
		logger.info("saving auth information for user: " + user );
		Auth auth = new Auth();
		auth.setUserId(user.getUserId());
		auth.setPwdhash(pwdHash);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(auth);
		session.getTransaction().commit();
		session.close();
	}
}
