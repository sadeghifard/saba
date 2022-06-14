package com.saba.sci.controller;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.security.auth.Subject;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saba.sci.dto.CustomerDetail;
import com.saba.sci.dto.UserForm;
import com.saba.sci.dto.UserToken;
import com.saba.sci.model.Token;
import com.saba.sci.model.User;
import com.saba.sci.service.TokenService;
import com.saba.sci.service.UserService;
import com.saba.sci.utile.SabaPasswordEncoder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sci/")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final TokenService tokenService;
	private final PasswordEncoder passwordEncoder;
	private final UserDetailsService userDetailsService;
	
	@CrossOrigin(allowedHeaders = "*", originPatterns = "*")
	@PostMapping("/login")
	public ResponseEntity<CustomerDetail> login(@RequestBody UserForm userForm, HttpServletRequest request, HttpServletResponse response, Authentication auth) {
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(10*360);
		
		User user = userService.getUserByUserName(userForm.getUserName().strip());
		
		System.out.println("\n\n====== " + user.getUserName() + " --->> " + user.getPassword() + "\n\n");
		
		String encodedPassword = user.getPassword();
		System.out.println("\n\n" + encodedPassword + "\n\n");
		
		
		
		System.out.println();
		
		if(!passwordEncoder.matches(userForm.getPassword(),encodedPassword)) {
			System.out.println("\n\n======= Password is not match ========\n\n");
			user = null;
		} 
		
		
//		User user = userService.getUserByUserNameAndPassword(userForm.getUserName(), userForm.getPassword());
		CustomerDetail customerDetail = null;
		
		if(user == null) {
			return new ResponseEntity<CustomerDetail>(customerDetail, HttpStatus.NO_CONTENT);
			
		} else {
			tokenService.SetTokenOnSecurityContext(user.getUserName(),user.getPassword(), request, response, auth);
			user = tokenService.refreshTokenByUserName(userForm.getUserName().strip());
			customerDetail = userService.getCustomerInfoByUserId(user.getId());
			return	new ResponseEntity<CustomerDetail>(customerDetail,HttpStatus.OK);
		}
	}
		
	
	@CrossOrigin(allowedHeaders = "*", originPatterns = "*")
	@PostMapping("/check")
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
	
	
//	@CrossOrigin(allowedHeaders = "*", originPatterns = "*", maxAge = 3600, allowCredentials = "true")
//	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/private/users_2538")
	public ResponseEntity<List<CustomerDetail>> getAllCustomersInfo() {
		System.out.println("\n entry to ================= /private/users");
		List<CustomerDetail> customerDetails = userService.getAllCustomersInfo();
		customerDetails = customerDetails.stream().filter(c -> c != null).collect(Collectors.toList());
		
		if(customerDetails == null) {
			System.out.println("\n ==== customerDetails is null in /private/users");
			return new ResponseEntity<List<CustomerDetail>>(customerDetails, HttpStatus.NOT_FOUND);
		} else {
			System.out.println("\n===== CustomerDetail in /private/users: " + customerDetails);
			return	new ResponseEntity<List<CustomerDetail>>(customerDetails,HttpStatus.OK)	;
		}
	}

	
//	@CrossOrigin(allowedHeaders = "*", originPatterns = "*")
	@GetMapping("/users/{id}")
	public ResponseEntity<CustomerDetail> getUserInfoById(@PathVariable("id") Long id) {
		CustomerDetail customerDetail = userService.getCustomerInfoByUserId(id);
		
		if(customerDetail == null) {
			return new ResponseEntity<CustomerDetail>(customerDetail, HttpStatus.NOT_FOUND);
		} else {
			return	new ResponseEntity<CustomerDetail>(customerDetail,HttpStatus.OK);
		}
	}

	@CrossOrigin(allowedHeaders = "*", originPatterns = "*")
	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {

		HttpSession session = request.getSession();
		session.invalidate();
		return ResponseEntity.ok("Acount invalidated");
	}
	


}
