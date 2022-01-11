package com.saba.sci.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saba.sci.model.Customer;
import com.saba.sci.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sci/")
public class CustomerController {
	private final CustomerService customerService;
	
	@CrossOrigin
	@GetMapping("/customer")
	public List<Customer> getAllCustomers() {
		List<Customer> customers = customerService.getAllPersons();
		return customers;
	}

}