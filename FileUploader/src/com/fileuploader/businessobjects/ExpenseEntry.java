package com.fileuploader.businessobjects;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This bean is used to represent each row of user input file as it should be stored in database
 * 
 * @author kashifu
 * 
 */
@Entity
@Table(name = "FILE_EXPENSE_ENTRIES")
public class ExpenseEntry {
	@Id
	@GeneratedValue
	@Column(name = "expenseEntryId")
	private int expenseEntryId;

	@Column(name = "fileId")
	private int fileId;

	@Column(name = "expenseDate")
	private Date expenseDate;

	@Column(name = "category")
	private String category;

	@Column(name = "employeeName")
	private String employeeName;

	@Column(name = "employeeAddress")
	private String employeeAddress;

	@Column(name = "expenseDescription")
	private String expenseDescription;

	@Column(name = "preTaxAmount")
	private BigDecimal preTaxAmount;

	@Column(name = "taxName")
	private String taxName;

	@Column(name = "taxAmount")
	private BigDecimal taxAmount;

	public int getExpenseEntryId() {
		return expenseEntryId;
	}

	public void setExpenseEntryId(int expenseEntryId) {
		this.expenseEntryId = expenseEntryId;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public Date getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
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

	public BigDecimal getPreTaxAmount() {
		return preTaxAmount;
	}

	public void setPreTaxAmout(BigDecimal preTaxAmount) {
		this.preTaxAmount = preTaxAmount;
	}

	public String getTaxName() {
		return taxName;
	}

	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExpenseEntry [expenseEntryId=");
		builder.append(expenseEntryId);
		builder.append(", fileId=");
		builder.append(fileId);
		builder.append(", expenseDate=");
		builder.append(expenseDate);
		builder.append(", category=");
		builder.append(category);
		builder.append(", employeeName=");
		builder.append(employeeName);
		builder.append(", employeeAddress=");
		builder.append(employeeAddress);
		builder.append(", expenseDescription=");
		builder.append(expenseDescription);
		builder.append(", preTaxAmount=");
		builder.append(preTaxAmount);
		builder.append(", taxName=");
		builder.append(taxName);
		builder.append(", taxAmount=");
		builder.append(taxAmount);
		builder.append("]");
		return builder.toString();
	}

}
