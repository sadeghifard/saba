package com.saba.sci.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saba.sci.dto.CustomerDetail;
import com.saba.sci.dto.CustomerDetailImple;
import com.saba.sci.dto.RegisterDetail;
import com.saba.sci.model.ContactInfo;
import com.saba.sci.model.Customer;
import com.saba.sci.model.CustomerInfo;
import com.saba.sci.model.Registry;
import com.saba.sci.model.Role;
import com.saba.sci.model.Token;
import com.saba.sci.model.User;
import com.saba.sci.repository.RoleRepository;
import com.saba.sci.utile.SabaPasswordEncoder;
import com.saba.sci.utile.Utility;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterDetailsService {
	
	private static Integer serialNum = 10000;
	
	private final CustomerService customerService;
	private final ContactInfoService contactInfoService;
	private final UserService userService;
	private final TokenService tokenService;
	private final CustomerInfoService customerInfoService;
	private final RegistryService registryService;
	private final RoleService roleService;
	
	private final PasswordEncoder passwordEncoder;
	
	public RegisterDetail save(RegisterDetail registerDetail) {
		User existeuser = userService.getUserByUserName(registerDetail.getUserName());
			
		if (existeuser == null) {
			Customer customer = new Customer();
			customer.setAddress(registerDetail.getAddress());
			customer.setCompanyName(registerDetail.getCompanyName());
			customer.setOwnerName(registerDetail.getOwnerName());
			final Integer stationNum = registerDetail.getStationNum();
			customer.setStationNum(stationNum);
			
			Customer lastCustomer = customerService.getLastCustomer();
			if(lastCustomer != null) {
				serialNum = lastCustomer.getSerialNum() + 1;
				
			} else {
				serialNum = serialNum+1;
			}
			
			customer.setSerialNum(serialNum);
			customer = customerService.save(customer);
			
			ContactInfo contactInfo = new ContactInfo();
			contactInfo.setCustomer(customer);
			contactInfo.setDescription(registerDetail.getDescription());
			contactInfo.setTelephone(registerDetail.getTelephone());
			contactInfoService.save(contactInfo);
			
			User user = new User();
			user.setCustomer(customer);
			user.setUserName(registerDetail.getUserName().strip());
			String passwordEncoded = passwordEncoder.encode(registerDetail.getPassword().strip());
			user.setPassword(passwordEncoded);
			user.setRoleName("ADMIN");
			
			Role role = roleService.getRoleByName("ADMIN");
			user.setRoles(Arrays.asList(role));
			
			
			CustomerInfo customerInfo = new CustomerInfo();
			customerInfo.setCustomer(customer);
			
			Token token = new Token();
			String tokenValue = Utility.tokenGenerator();
			
			token.setTokenValue(tokenValue);
			token.setCreationDate(LocalDateTime.now());
			token = tokenService.Save(token);
			user.setToken(token);
			user = userService.save(user);

			for(int i = 1; i<= stationNum; i++){
				Registry registry = new Registry();
				registry.setCustomer(customer);
				registry.setStationNo(i);
				registry.setUser(user);
				registryService.save(registry);
			}
			
			return registerDetail;
		} else {
			return null;
		}

	}
	

	public CustomerDetail updateCustomerDetail(CustomerDetailImple customerDetailImple) {
		

		User existUser =  userService.getUserById(customerDetailImple.getUserId());
		Customer existCustomer = existUser.getCustomer();
				
		existCustomer.setAddress(customerDetailImple.getAddress());
		final Integer stationNum = customerDetailImple.getStationNum();
		existCustomer.setStationNum(stationNum);
		existCustomer.setCompanyName(customerDetailImple.getCompanyName());
		existCustomer.setOwnerName(customerDetailImple.getOwnerName());
		existCustomer = customerService.save(existCustomer);
		
		existUser.setCustomer(existCustomer);
		
		ContactInfo contactInfo =  contactInfoService.getContactInfoById(customerDetailImple.getContactId());

		contactInfo.setCustomer(existCustomer);
		contactInfo.setDescription(customerDetailImple.getDescription());
		contactInfo.setTelephone(customerDetailImple.getTelephone());
		contactInfo = contactInfoService.save(contactInfo);
		
		CustomerInfo customerInfo =  customerInfoService.getCustomerInfoByCustomerId(existCustomer.getId());
		customerInfo = customerInfo == null ? new CustomerInfo() : customerInfo;
		
		customerInfo.setCustomer(existCustomer);
		customerInfo.setGarantyDate(customerDetailImple.getGarantyDate());
		customerInfo.setSetupDate(customerDetailImple.getSetupDate());
		customerInfo.setSubAnnual(customerDetailImple.getSubAnnual());
		customerInfo.setSubEndDate(customerDetailImple.getSubEndDate());
		customerInfo.setSubPayment(customerDetailImple.getSubPayment());
		customerInfo.setSubStartDate(customerDetailImple.getSubStartDate());
		customerInfo = customerInfoService.save(customerInfo);
		
		List<Registry> registries = registryService.getRegistryByUserId(existUser.getId());
		
		if(registries != null) {
			int size = registries.size();
			if(stationNum > size) {
				for(int i= size+1 ; i<=stationNum; i++) {
					Registry registry = new Registry();
					registry.setCustomer(existCustomer);
					registry.setStationNo(i);
					registry.setUser(existUser);
					registryService.save(registry);
				}
			}
		}
		
		CustomerDetail customerDetail = userService.getCustomerInfoByUserId(existUser.getId());
		
		return customerDetail;
	}
	
}
