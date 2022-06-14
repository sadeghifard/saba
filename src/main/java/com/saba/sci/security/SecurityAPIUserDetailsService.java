package com.saba.sci.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

//@Component("userDetailsService")
public class SecurityAPIUserDetailsService implements UserDetailsService {

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		if(username.equalsIgnoreCase("reza")){
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		
		return new org.springframework.security.core.userdetails.User(
				username, "reza", authorities);
	}

}
