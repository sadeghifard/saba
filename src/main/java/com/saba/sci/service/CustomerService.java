package com.saba.sci.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saba.sci.model.Customer;
import com.saba.sci.repository.CustomerRepository;

import  java.util.List;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class CustomerService {
	private final CustomerRepository customerRepository;
	
	public List<Customer> getAllPersons(){
		return customerRepository.findAll();
	}
	
	public Customer getCustomerById(Long id) {
		return customerRepository.findById(id)
				.orElseThrow(() ->  new RuntimeException("Customer not found"));
	}
	
	public Customer getLastCustomer() {
		return customerRepository.getLastCustomer().orElse(null);
	}
	
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}
	
	public Customer update(Long id, Customer customer) {
		Customer existPerson = getCustomerById(id);
		existPerson.setAddress(customer.getAddress());
		existPerson.setCompanyName(customer.getCompanyName());
		existPerson.setOwnerName(customer.getOwnerName());
		existPerson.setSerialNum(customer.getSerialNum());
		existPerson.setStationNum(customer.getStationNum());
		return customerRepository.save(existPerson);
	}
	
	public void delete(Long id) {
		Customer customer = getCustomerById(id);
		customerRepository.delete(customer);
	}
}
