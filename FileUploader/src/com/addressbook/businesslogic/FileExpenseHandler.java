package com.addressbook.businesslogic;

import java.util.List;

import com.addressbook.businessobjects.MonthlyExpense;
import com.addressbook.businessobjects.User;
import com.addressbook.businessobjects.UserFileMap;
import com.addressbook.dao.FileExpenseDao;
import com.addressbook.util.ValidationHelper;

public class FileExpenseHandler {
	private FileExpenseDao fileExpenseDao;

	public FileExpenseHandler(FileExpenseDao fileExpenseDao) {
		ValidationHelper.validateForNull(fileExpenseDao, "fileExpenseDao");
		this.fileExpenseDao = fileExpenseDao;
	}

	public List<MonthlyExpense> getLastUploadEntries(User user) {
		ValidationHelper.validateForNull(user, "user");
		UserFileMap latestMap = fileExpenseDao.getLatestFileMap(user);
		List<MonthlyExpense> monthlyExpenseEntries = fileExpenseDao.getExpenseEntries(latestMap);
		return monthlyExpenseEntries;
	}
}
