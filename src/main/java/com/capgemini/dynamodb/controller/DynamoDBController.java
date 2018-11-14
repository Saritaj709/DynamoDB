package com.capgemini.dynamodb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.dynamodb.model.Customer;
import com.capgemini.dynamodb.repository.CustomerRepository;

@RestController
@RequestMapping("/dynamodb/api")
public class DynamoDBController {

	@Autowired
	CustomerRepository repository;
 
	@GetMapping("/delete")
	public ResponseEntity<String> delete() {
		repository.deleteAll();
		return new ResponseEntity<>("Done",HttpStatus.OK);
	}
 
	@PostMapping("/save")
	public ResponseEntity<String> save(@RequestBody Customer customer) {
		// save a single Customer
		repository.insert(customer);
		return new ResponseEntity<>("saved",HttpStatus.OK);
	}
 
	/*@RequestMapping("/save")
	public String save(Customer customer) {
		customer.setCustomerId("1122");
		customer.setFirstName("Sarita");
		customer.setLastName("Jaiswal");
		repository.insert(customer);
		return customer.toString();
	}*/
	
	@GetMapping("/findall")
	public ResponseEntity<String> findAll() {
		String result = "";
		Iterable<Customer> customers =  (Iterable<Customer>) repository.findAll();
 
		for (Customer cust : customers) {
			result += cust.toString() + "<br>";
		}
 
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
 
	@PostMapping("/findbyid")
	public ResponseEntity<String> findById(@RequestParam("customerId") String customerId) {
		String customer=repository.findOne(customerId);
		return new ResponseEntity<>(customer,HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody Customer customer) {
		repository.update(customer);
		return new ResponseEntity<>("updated",HttpStatus.OK);
	}
 
/*	@RequestMapping("/findbylastname")
	public String fetchDataByLastName(@RequestParam("lastname") String lastName) {
		String result = "";
 
		for (Customer cust : repository.findByLastName(lastName)) {
			result += cust.toString() + "<br>";
		}
 
		return result;
	}*/
}
