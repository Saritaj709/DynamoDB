package com.capgemini.dynamodb.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Customer")
public class Customer implements Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String customerId;
	private String firstName;
	private String lastName;
 
	public Customer() {
	}
 
	public Customer(String customerId, String firstName, String lastName) {
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
	}
 
	@DynamoDBHashKey(attributeName = "customerId")
	//@DynamoDBAutoGeneratedKey
	public String getCustomerId() {
		return customerId;
	}
 
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
 
	@DynamoDBAttribute(attributeName = "firstName")
	public String getFirstName() {
		return firstName;
	}
 
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
 
	@DynamoDBAttribute(attributeName = "lastName")
	public String getLastName() {
		return lastName;
	}
 
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
 
	@Override
	public String toString() {
		return String.format("Customer[id=%s, firstName='%s', lastName='%s']", customerId, firstName, lastName);
	}
}