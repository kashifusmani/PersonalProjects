package com.addressbook.dao.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.addressbook.businessobjects.Auth;
import com.addressbook.businessobjects.Contact;
import com.addressbook.businessobjects.ExpenseEntry;
import com.addressbook.businessobjects.User;
import com.addressbook.businessobjects.UserFileMap;

/**
 * Utility class to configure a SessionFactory.
 * @author kashifu
 *
 */
public class HibernateUtil {
	private static final Log logger = LogFactory.getLog(HibernateUtil.class);
	private static SessionFactory sessionFactory;

	static { 
		try {
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			configuration.addAnnotatedClass(User.class);
			configuration.addAnnotatedClass(Auth.class);
			configuration.addAnnotatedClass(Contact.class);
			configuration.addAnnotatedClass(UserFileMap.class);
			configuration.addAnnotatedClass(ExpenseEntry.class);
			ServiceRegistryBuilder registry = new ServiceRegistryBuilder();
			registry.applySettings(configuration.getProperties());
			ServiceRegistry serviceRegistry = registry.buildServiceRegistry();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Exception e) {
			logger.error("SessionFactory creation failed.");
			logger.error(e.getMessage());			
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
