package com.rabobank.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @author Anitha C
 *
 */
public class Record {

	private int transactionRef;
	
	@JsonIgnore
	private String accountNumber;
	
	@JsonIgnore
	private double startBalance;
	@JsonIgnore
	private double mutation;
	private String description;
	@JsonIgnore
	private double endBalance;
	
	/*
	 * temporary variable to store reason, not to be persisted
	 */
	private String failureReason;

	public String getFailureReason() {
		return failureReason;
	}

	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}

	public Record() {
	}

	public Record(int transactionRef, String accountNumber, double startBalance, double mutation, String description,
			double endBalance) {
		super();
		this.transactionRef = transactionRef;
		this.accountNumber = accountNumber;
		this.startBalance = startBalance;
		this.mutation = mutation;
		this.description = description;
		this.endBalance = endBalance;
	}

	@XmlAttribute(name="reference")
	public int getTransactionRef() {
		return transactionRef;
	}

	public void setTransactionRef(int transactionRef) {
		this.transactionRef = transactionRef;
	}

	@XmlElement(name="accountNumber")
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@XmlElement(name="startBalance")
	public double getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(double startBalance) {
		this.startBalance = startBalance;
	}

	@XmlElement(name="mutation")
	public double getMutation() {
		return mutation;
	}

	public void setMutation(double mutation) {
		this.mutation = mutation;
	}

	@XmlElement(name="description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@XmlElement(name="endBalance")
	public double getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(double endBalance) {
		this.endBalance = endBalance;
	}

}
