package com.saba.sci.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider
  implements AuthenticationProvider {
 

	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
    @Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
  
    	String username = authentication.getName();
        String password = (String) authentication.getCredentials();
   
          UserDetails user = userDetailsService.loadUserByUsername(username);
   
          if (user == null || !user.getUsername().equalsIgnoreCase(username)) {
              throw new BadCredentialsException("Username not found.");
          }
   
          if (!password.equals(user.getPassword())) {
              throw new BadCredentialsException("Wrong password.");
          }
   
          Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
   
          return new UsernamePasswordAuthenticationToken(username, password, authorities);
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
          UsernamePasswordAuthenticationToken.class);
    }
}
