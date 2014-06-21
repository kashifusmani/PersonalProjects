package com.fileuploader.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.fileuploader.businessobjects.ExpenseEntry;
import com.fileuploader.businessobjects.MonthlyExpense;
import com.fileuploader.businessobjects.User;
import com.fileuploader.businessobjects.UserFileMap;
import com.fileuploader.util.ValidationHelper;

public class FileExpenseDao {
	private SessionFactory sessionFactory;
	private final Log logger = LogFactory.getLog(getClass());
	
	public FileExpenseDao(SessionFactory sessionFactory) {
		ValidationHelper.validateForNull(sessionFactory, "sessionFactory");
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * insertNewMapping inserts new user:file mapping in USER_FILE_MAPPINGS table
	 * @param user The user for which we want to insert a new mapping
	 * @return The newly inserted mapping.
	 */
	public synchronized UserFileMap insertNewMapping(User user) {
		ValidationHelper.validateForNull(user, "user");
		UserFileMap map = new UserFileMap();
		map.setUserId(user.getUserId());
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(map);
		session.getTransaction().commit();
		session.close();
		return map;
	}
	
	public void insertExpenseEntries(List<ExpenseEntry> expenseEntries) {
		ValidationHelper.validateForNull(expenseEntries, "expenseEntries");
		logger.info("Inserting new expense entries: " + expenseEntries);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		for (ExpenseEntry entry: expenseEntries) {
			session.save(entry);
		}
		session.getTransaction().commit();
		session.close();		
	}
	
	public UserFileMap getLatestFileMap(User user) {
		ValidationHelper.validateForNull(user, "user");
		//select max fileId where userId = userId
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		String queryStr = "select max(id) from USER_FILE_MAPPINGS WHERE userId = :user_id";
		SQLQuery query = session.createSQLQuery(queryStr);

		UserFileMap map = null;
		query.setParameter("user_id", user.getUserId());
		Integer fileId = (Integer) query.uniqueResult();
		if (fileId != null) { 
			map = new UserFileMap();
			map.setId(fileId);
			map.setUserId(user.getUserId());
		} else {
			logger.info("No file mappings found for user: " + user);
		}
		session.getTransaction().commit();
		session.close();
		return map;
	}
	
	public List<MonthlyExpense> getExpenseEntries(UserFileMap map) {
		ValidationHelper.validateForNull(map, "map");
		logger.info("Retrieving ExpenseEntries for " + map);
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		String queryStr = "select monthname(expenseDate), year(expenseDate), sum(preTaxAmount), sum(taxAmount) , sum(preTaxAmount) + sum(taxAmount) as totalExpense "
				+ "from file_expense_entries where fileId = :file_id group by year(expenseDate), month(expenseDate) ";
		SQLQuery query = session.createSQLQuery(queryStr);
		query.setParameter("file_id", map.getId());
		
		//logger.info(query.list());
		List<Object[]> rows = query.list();
		List<MonthlyExpense> result = new ArrayList<MonthlyExpense>();
		for (Object[] row: rows) {
			MonthlyExpense expense = new MonthlyExpense();
			expense.setMonth((String) row[0]);
			expense.setYear(String.valueOf(row[1]));
			expense.setTotalPreTaxAmount((BigDecimal) row[2]);
			expense.setTotalTaxAmount((BigDecimal) row[3]);
			expense.setTotalExpense((BigDecimal) row[4]);
			result.add(expense);
		}
		
		session.getTransaction().commit();
		session.close();
		logger.info(result);
		return result;
	}
}
