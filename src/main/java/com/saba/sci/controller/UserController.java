package com.saba.sci.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saba.sci.dto.CustomerDetail;
import com.saba.sci.dto.UserForm;
import com.saba.sci.dto.UserToken;
import com.saba.sci.model.User;
import com.saba.sci.service.TokenService;
import com.saba.sci.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sci/")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final TokenService tokenService;
	
	@CrossOrigin
	@PostMapping("/login")
	public ResponseEntity<CustomerDetail> login(@RequestBody UserForm userForm, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(10*360);
		
		User user = userService.getUserByUserNameAndPassword(userForm.getUserName(), userForm.getPassword());
		CustomerDetail customerDetail = null;
		
		if(user == null) {
			return new ResponseEntity<CustomerDetail>(customerDetail, HttpStatus.NO_CONTENT);
			
		} else {
			
			customerDetail = userService.getCustomerInfoByUserId(user.getId());
			return	new ResponseEntity<CustomerDetail>(customerDetail,HttpStatus.OK);
		}
	}
		
	
	@CrossOrigin
	@PostMapping("/private/check")
	public ResponseEntity<CustomerDetail> checkToken(@RequestBody UserToken userToken){
		Predicate<User> p1 = u -> u.getToken().getTokenValue() != null; 
		Predicate<User> p2 = u -> !(u.getToken().getTokenValue().isEmpty()); 
		
		
		List<User> users = userService.getAllUsers();
				Optional<User> user = users.stream()
						.filter(p1.and(p2))
						.filter(u -> u.getToken().getTokenValue().equals(userToken.getTokenValue()))
						.findAny();
				User existUser = user.isPresent()? user.get() :  null;
		CustomerDetail customerDetail = null;
		if(existUser == null) {
			return new ResponseEntity<CustomerDetail>(customerDetail, HttpStatus.NOT_FOUND);
			
		} else {
			String currentToken = userToken.getTokenValue();
			System.out.println(currentToken);
			
			String newToken = tokenService.updateTokenValue(userToken.getTokenValue());
			System.out.println(newToken);
			existUser.getToken().setTokenValue(newToken);
			existUser.getToken().setCreationDate(LocalDateTime.now());
			existUser = userService.save(existUser);

			customerDetail = userService.getCustomerInfoByUserId(existUser.getId());;
			return	new ResponseEntity<CustomerDetail>(customerDetail,HttpStatus.OK);		
		}
					
	}
	
	
	@CrossOrigin
	@GetMapping("/private/users")
	public ResponseEntity<List<CustomerDetail>> getAllCustomersInfo() {
		List<CustomerDetail> customerDetails = userService.getAllCustomersInfo();
		customerDetails = customerDetails.stream().filter(c -> c != null).collect(Collectors.toList());
		
		if(customerDetails == null) {
			return new ResponseEntity<List<CustomerDetail>>(customerDetails, HttpStatus.NOT_FOUND);
		} else {
			return	new ResponseEntity<List<CustomerDetail>>(customerDetails,HttpStatus.OK)	;
		}
	}

	
	@CrossOrigin
	@GetMapping("/users/{id}")
	public ResponseEntity<CustomerDetail> getUserInfoById(@PathVariable("id") Long id) {
		CustomerDetail customerDetail = userService.getCustomerInfoByUserId(id);
		
		if(customerDetail == null) {
			return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
		} else {
			return	new ResponseEntity<CustomerDetail>(customerDetail,HttpStatus.OK);
		}
	}

	@CrossOrigin
	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {

		HttpSession session = request.getSession();
		session.invalidate();
		return ResponseEntity.ok("Acount invalidated");
	}
	


}
