package com.saba.sci.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saba.sci.dto.CutomerSerailDetail;
import com.saba.sci.model.Customer;
import com.saba.sci.model.CustomerLog;
import com.saba.sci.model.Registry;
import com.saba.sci.repository.CustomerLogRepository;
import com.saba.sci.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerLogService {
	
	private final CustomerLogRepository customerLogRepository;
	private final CustomerService customerService;
	private final RegistryService registryService;
	
	public List<CustomerLog> getAllCustomerLogs() {
		return customerLogRepository.findAll();
	}
	
	public CustomerLog getCustomerLogById(Long id) {
		return customerLogRepository.findById(id).orElse(null);
	}
	
	public CustomerLog save(Long id, CustomerLog customerLog) {
		CutomerSerailDetail serailDetail = customerLogRepository.getCustomerSerialByUserId(id)
				.orElseThrow(() -> new RuntimeException("Customer Information not found: " + id));
		Customer customer = customerService.getCustomerById(serailDetail.getCustomerId());
		
		customerLog.setSerialNum(serailDetail.getSerialNum());
		customerLog.setStationNum(serailDetail.getStationNum());
		customerLog.setCustomer(customer);
		
	
		customerLog =  customerLogRepository.save(customerLog);
	    return customerLog;
	}
	
	public CustomerLog update(Long id, CustomerLog customerLog) {
		CustomerLog existCustomerLog = getCustomerLogById(id);
		
		if(existCustomerLog != null && customerLog != null) {
			existCustomerLog.setSerialNum(customerLog.getSerialNum());
			existCustomerLog.setStationNum(customerLog.getStationNum());
			existCustomerLog.setDescription(customerLog.getDescription());
			existCustomerLog.setStartDate(customerLog.getStartDate());
			existCustomerLog.setStartTime(customerLog.getStartTime());
			existCustomerLog.setEndDate(customerLog.getEndDate());
			existCustomerLog.setEndTime(customerLog.getEndTime());
			existCustomerLog.setRegisterCodeLog(customerLog.getRegisterCodeLog());
			existCustomerLog.setComputerNameLog(customerLog.getComputerNameLog());
			
		} else {
			return null;
		}
		existCustomerLog =  customerLogRepository.save(existCustomerLog);
		System.out.println("service:" + existCustomerLog);
		return existCustomerLog;
	}
	
	public void delete(Long id) {
		CustomerLog customerLog = getCustomerLogById(id);
		if(customerLog != null) {
			customerLogRepository.delete(customerLog);
		}
	}
	
	public List<CustomerLog> getCustomerLogByUserId(Long id) {
		List<CustomerLog> customerLogs = customerLogRepository.getCustomerLogByUserId(id).orElse(null);
		
		return customerLogs;
	}
	
}
