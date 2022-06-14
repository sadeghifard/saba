package com.saba.sci.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saba.sci.dto.CustomerDetail;
import com.saba.sci.dto.CustomerDetailImple;
import com.saba.sci.dto.RegisterDetail;
import com.saba.sci.service.RegisterDetailsService;
import com.saba.sci.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sci")
public class RegisterDetailsController {
	private final RegisterDetailsService registerDetailsService;
	private final UserService userService;
	
	@CrossOrigin
	@GetMapping("/registration")
	ResponseEntity<RegisterDetail> showRegistrationForm(){
		RegisterDetail registerDetail = new RegisterDetail();
		return ResponseEntity.ok(registerDetail);
	}
	
	@CrossOrigin
	@PostMapping("/registration")
	public ResponseEntity<CustomerDetail> registerNewCustomer(@RequestBody RegisterDetail registerDetail) {
		System.out.println(registerDetail);
		RegisterDetail existRegisterDetail = registerDetailsService.save(registerDetail);
		CustomerDetail customerDetail = null;
		if(existRegisterDetail != null) {
			customerDetail = userService.getCustomerInfoByUserName(registerDetail.getUserName());
			return new ResponseEntity<CustomerDetail>(customerDetail, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<CustomerDetail>(customerDetail, HttpStatus.ALREADY_REPORTED);
		}
		
	}
	
	
	@CrossOrigin
	@PutMapping("/private/users/update_2538")
	public ResponseEntity<CustomerDetail> updateCustomerDetail(@RequestBody CustomerDetailImple customerDetailImple){
		System.out.println(customerDetailImple);
		CustomerDetail detail =  registerDetailsService.updateCustomerDetail(customerDetailImple);
		return new ResponseEntity<CustomerDetail>(detail,HttpStatus.OK);
	}
	


}
