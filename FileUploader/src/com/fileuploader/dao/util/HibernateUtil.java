package com.fileuploader.dao.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import com.fileuploader.businessobjects.Auth;
import com.fileuploader.businessobjects.ExpenseEntry;
import com.fileuploader.businessobjects.User;
import com.fileuploader.businessobjects.UserFileMap;

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
