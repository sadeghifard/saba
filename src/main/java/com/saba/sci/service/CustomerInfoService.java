package com.saba.sci.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saba.sci.model.CustomerInfo;
import com.saba.sci.repository.CustomerInfoRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerInfoService {
	private final CustomerInfoRepository customerInfoRepository;
	
	public CustomerInfo save(CustomerInfo customerInfo) {
		return customerInfoRepository.save(customerInfo);
	}
	
	public CustomerInfo getCustomerInfoById(Long id) {
		return customerInfoRepository.findById(id).orElse(null);
	}
	
	public CustomerInfo getCustomerInfoByCustomerId(Long id) {
		return customerInfoRepository.getCustomerInfoByCustomerId(id).orElse(null);
	}
}
