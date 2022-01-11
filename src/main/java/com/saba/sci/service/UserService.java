package com.saba.sci.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saba.sci.config.SabaUserDetails;
import com.saba.sci.dto.CustomerDetail;
import com.saba.sci.model.ContactInfo;
import com.saba.sci.model.Customer;
import com.saba.sci.model.User;
import com.saba.sci.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}
	
	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public User getUserByUserName(String userName) {
		return userRepository.getUserByUserName(userName).orElse(null);
	}
	

	
	public User getUserByUserNameAndPassword(String userName, String password) {
		return userRepository.getUserByUserNameAndPassword(userName, password).orElse(null);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
	
	public User update(Long id, User user) {
		User existUser = getUserById(id);
		existUser.setPassword(user.getPassword());
		existUser.setUserName(user.getUserName());
		existUser.setCustomer(user.getCustomer());
		return userRepository.save(existUser);
	}
	
	public void delete(Long id) {
		User user = getUserById(id);
		userRepository.delete(user);
	}
	
	public CustomerDetail getCustomerInfoByUserName(String UserName) {
		CustomerDetail customerDetails =  userRepository.getCustomerInfoByUserName(UserName).orElse(null);
		
		return customerDetails;
	}
	
	public CustomerDetail getCustomerInfoByUserId(Long id) {
		CustomerDetail customerDetail =  userRepository.getCustomerInfoByUserId(id).orElse(null);
							
		return customerDetail;
	}
	
	public List<CustomerDetail> getAllCustomersInfo() {
		List<CustomerDetail> customerDetails = userRepository.getAllCustomersInfo().orElse(null);
							
		return customerDetails;
	}
	

	
}
