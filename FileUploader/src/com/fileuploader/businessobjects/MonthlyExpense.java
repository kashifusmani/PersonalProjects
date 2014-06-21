package com.fileuploader.businessobjects;

import java.math.BigDecimal;

public class MonthlyExpense {
	private String month;
	private String year;
	private BigDecimal totalPreTaxAmount;
	private BigDecimal totalTaxAmount;
	private BigDecimal totalExpense;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public BigDecimal getTotalPreTaxAmount() {
		return totalPreTaxAmount;
	}

	public void setTotalPreTaxAmount(BigDecimal totalPreTaxAmount) {
		this.totalPreTaxAmount = totalPreTaxAmount;
	}

	public BigDecimal getTotalTaxAmount() {
		return totalTaxAmount;
	}

	public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
		this.totalTaxAmount = totalTaxAmount;
	}

	public BigDecimal getTotalExpense() {
		return totalExpense;
	}

	public void setTotalExpense(BigDecimal totalExpense) {
		this.totalExpense = totalExpense;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MonthlyExpense [month=");
		builder.append(month);
		builder.append(", year=");
		builder.append(year);
		builder.append(", totalPreTaxAmount=");
		builder.append(totalPreTaxAmount);
		builder.append(", totalTaxAmount=");
		builder.append(totalTaxAmount);
		builder.append(", totalExpense=");
		builder.append(totalExpense);
		builder.append("]");
		return builder.toString();
	}

}
