package com.capgemini.dynamodb.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@Configuration
public class DynamoDBConfiguration {

	@Value("${amazon.dynamodb.endpoint}")
	private String awsDynamoDbEndpoint;

	@Value("${amazon.aws.accesskey}")
	private String awsAccessKey;

	@Value("${amazon.aws.secretkey}")
	private String awsSecretKey;

	@Value("${amazon.dynamodb.region}")
	private String awsRegion;
	/*
	 * @Bean public AmazonDynamoDB amazonDynamoDB() { AmazonDynamoDB amazonDynamoDB
	 * = new AmazonDynamoDBClient(amazonAWSCredentials());
	 * 
	 * if (!StringUtils.isEmpty(amazonDynamoDBEndpoint)) {
	 * amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint); }
	 * 
	 * return amazonDynamoDB; }
	 * 
	 * @Bean public AWSCredentials amazonAWSCredentials() { return new
	 * BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey); }
	 */

	@Autowired
	private AmazonDynamoDB dynamoDB;

	@Bean
	public DynamoDBMapper mapper() {
		return new DynamoDBMapper(dynamoDB);
	}

	@Bean
	public AmazonDynamoDB amazonDyanamoDBConfig() {
		return AmazonDynamoDBAsyncClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsDynamoDbEndpoint, awsRegion))
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
				.build();

	}

}
