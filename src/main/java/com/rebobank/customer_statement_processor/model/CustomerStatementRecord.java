package com.rebobank.customer_statement_processor.model;

import com.opencsv.bean.CsvBindByPosition;

public class CustomerStatementRecord {
	@CsvBindByPosition(position = 0)
	private Long transactionReference;
	@CsvBindByPosition(position = 1)
	private String accountNumber;
	@CsvBindByPosition(position = 3)
	private Double startBalance;
	@CsvBindByPosition(position = 4)
	private Double mutation;
	@CsvBindByPosition(position = 2)
	private String description;
	@CsvBindByPosition(position = 5)
	private Double endBalance;
	
	public Long getTransactionReference() {
		return transactionReference;
	}
	public void setTransactionReference(Long transactionReference) {
		this.transactionReference = transactionReference;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Double getStartBalance() {
		return startBalance;
	}
	public void setStartBalance(Double startBalance) {
		this.startBalance = startBalance;
	}
	public Double getMutation() {
		return mutation;
	}
	public void setMutation(Double mutation) {
		this.mutation = mutation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getEndBalance() {
		return endBalance;
	}
	public void setEndBalance(Double endBalance) {
		this.endBalance = endBalance;
	}

}
