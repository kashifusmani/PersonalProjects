package com.addressbook.servlets.beans;

public class ExpenseCsvEntry {

	private String expenseDateString;

	private String category;

	private String employeeName;

	private String employeeAddress;

	private String expenseDescription;

	private String preTaxAmountString;

	private String taxName;

	private String taxAmountString;

	public String getExpenseDateString() {
		return expenseDateString;
	}

	public void setExpenseDateString(String expenseDateString) {
		this.expenseDateString = expenseDateString;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeAddress() {
		return employeeAddress;
	}

	public void setEmployeeAddress(String employeeAddress) {
		this.employeeAddress = employeeAddress;
	}

	public String getExpenseDescription() {
		return expenseDescription;
	}

	public void setExpenseDescription(String expenseDescription) {
		this.expenseDescription = expenseDescription;
	}

	public String getPreTaxAmountString() {
		return preTaxAmountString;
	}

	public void setPreTaxAmountString(String preTaxAmountString) {
		this.preTaxAmountString = preTaxAmountString;
	}

	public String getTaxName() {
		return taxName;
	}

	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

	public String getTaxAmountString() {
		return taxAmountString;
	}

	public void setTaxAmountString(String taxAmountString) {
		this.taxAmountString = taxAmountString;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExpenseCsvEntry [expenseDateString=");
		builder.append(expenseDateString);
		builder.append(", category=");
		builder.append(category);
		builder.append(", employeeName=");
		builder.append(employeeName);
		builder.append(", employeeAddress=");
		builder.append(employeeAddress);
		builder.append(", expenseDescription=");
		builder.append(expenseDescription);
		builder.append(", preTaxAmountString=");
		builder.append(preTaxAmountString);
		builder.append(", taxName=");
		builder.append(taxName);
		builder.append(", taxAmountString=");
		builder.append(taxAmountString);
		builder.append("]");
		return builder.toString();
	}
	
	

}
