package com.saba.sci.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.saba.sci.model.User;
import com.saba.sci.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Component("userDetailsService")
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	  User user = userRepository.getUserByUserName(userName)
			  .orElseThrow(() -> new UsernameNotFoundException("Could not find user"));

	   return new SabaUserDetails(user);       
	}

}
