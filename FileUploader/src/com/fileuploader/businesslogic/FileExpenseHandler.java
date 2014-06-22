package com.fileuploader.businesslogic;

import java.util.ArrayList;
import java.util.List;

import com.fileuploader.businessobjects.MonthlyExpense;
import com.fileuploader.businessobjects.User;
import com.fileuploader.businessobjects.UserFileMap;
import com.fileuploader.dao.FileExpenseDao;
import com.fileuploader.util.ValidationHelper;

/**
 * This class is responsible to perform any business logic of fetching expense data via DAO
 * @author kashifu
 *
 */
public class FileExpenseHandler {
	private FileExpenseDao fileExpenseDao;

	public FileExpenseHandler(FileExpenseDao fileExpenseDao) {
		ValidationHelper.validateForNull(fileExpenseDao, "fileExpenseDao");
		this.fileExpenseDao = fileExpenseDao;
	}

	public List<MonthlyExpense> getLastUploadEntries(User user) {
		ValidationHelper.validateForNull(user, "user");
		UserFileMap latestMap = fileExpenseDao.getLatestFileMap(user);
		//if no uploads exists for this user, return empty response
		List<MonthlyExpense> monthlyExpenseEntries = new ArrayList<MonthlyExpense>();
		if (latestMap != null) {
			monthlyExpenseEntries = fileExpenseDao.getExpenseEntries(latestMap);
		}
		return monthlyExpenseEntries;
	}
}
