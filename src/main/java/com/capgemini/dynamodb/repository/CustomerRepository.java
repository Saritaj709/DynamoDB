package com.capgemini.dynamodb.repository;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.DeleteTableRequest;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.capgemini.dynamodb.model.Customer;

@Repository
public class CustomerRepository {

	@Autowired
	private DynamoDBMapper mapper;
	
	private HashOperations<String, String, Customer> hashOperations;
	private static final String KEY = "customer";

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerRepository.class);

	public DynamoDBMapper insert(Customer customer) {
		mapper.save(customer);
		return mapper;
	}

	public void update(Customer customer) {
		try {
			mapper.save(customer, buildDynamoDBSaveExpression(customer));
		} catch (ConditionalCheckFailedException exception) {
			LOGGER.error("invalid data : ", exception.getMessage());
		}
	}

	public Customer findOne(String customerId) {
		Customer customer=mapper.load(Customer.class, customerId);
		return customer;
	}

	public void deleteById(String customerId) {
		Customer customer=mapper.load(Customer.class, customerId);
		mapper.delete(customer);
	}

	private DynamoDBSaveExpression buildDynamoDBSaveExpression(Customer customer) {
		DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
		Map<String, ExpectedAttributeValue> expected = new HashMap<>();
		expected.put("customerId", new ExpectedAttributeValue(new AttributeValue(customer.getCustomerId()))
				.withComparisonOperator(ComparisonOperator.EQ));
		saveExpression.setExpected(expected);
		return saveExpression;
	}

	public Customer findAll() {
		return mapper.load(Customer.class, "Customer");
	}

	/*public Map<String, Customer> findAll() {
		return hashOperations.entries("Customer");
	}*/
	
	public void deleteAll() {
	//return mapper.generateDeleteTableRequest(Customer.class);
	 mapper.generateDeleteTableRequest(Customer.class);
	}
}
