package com.saba.sci.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saba.sci.model.Role;
import com.saba.sci.model.User;
import com.saba.sci.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	  User user = userRepository.getUserByUserName(userName)
			  .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));

	   return new SabaUserDetails(user);       
	}

}
