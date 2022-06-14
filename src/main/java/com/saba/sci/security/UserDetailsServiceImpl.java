package com.saba.sci.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.saba.sci.model.User;
import com.saba.sci.repository.UserRepository;

@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		  User user = userRepository.getUserByUserName(username).orElse(null);
	         
	        if (user == null) {
	            throw new UsernameNotFoundException("Could not find user");
	        }
	     System.out.println("\n=========== UserDetailsServiceImpl: " + user);    
	        return new SabaUserDetails(user);
	}

}
