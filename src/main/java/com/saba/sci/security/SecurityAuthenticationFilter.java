package com.saba.sci.security;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SqlFragmentAlias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.saba.sci.model.Role;
import com.saba.sci.model.Token;
import com.saba.sci.model.User;
import com.saba.sci.repository.TokenRepository;
import com.saba.sci.service.TokenService;
import com.saba.sci.service.UserService;
import com.saba.sci.utile.Base64Coder;

import reactor.netty.http.server.HttpServerResponse;

@Component
@Order(299)
@Filter(name = "securityAuthenticationFilter")
public class SecurityAuthenticationFilter extends OncePerRequestFilter {

	private static final long serialVersionUID = 1L;
	private Set<String> skipUrls = new HashSet<>(Arrays.asList("/sci/registration","/sci/login", "/sci/check", "/sci/logout"));
	private AntPathMatcher pathMatcher = new AntPathMatcher();
	
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	private UserService userService;
	
//	@Autowired
//	@Qualifier(value = "myAuthenticationManager")
//	private AuthenticationManager authenticationManager;
	
//	@Autowired
//	private  SecurityContextRepository contextRepository;
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws IOException, ServletException, AuthenticationException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		
		User user = getUserByToken(request, response);
		if(user == null) {
			 response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			 return;
		}
		String roleName = user.getRoles().stream().findFirst().get().getName();
		
		if(user != null && roleName.equalsIgnoreCase("ADMIN")) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleName);
			
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword(), List.of(grantedAuthority));
//			Authentication auth = authenticationManager.authenticate(authToken);
			SecurityContextHolder.getContext().setAuthentication(authToken);
//			contextRepository.saveContext(SecurityContextHolder.getContext(), request, response);
		}
		filterChain.doFilter(request, response);
	}

	
	private User getUserByUsernameAndPassword(HttpServletRequest request, HttpServletResponse response) {
		String authorization =  request.getHeader("Authorization");
		System.out.println("\n authorization in filter: " + authorization +"\n");
		if(authorization == null || authorization.isBlank()) {
			((HttpServletResponse) response).setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		
		authorization = Base64Coder.decode(authorization);
		
		int indx = authorization.indexOf("-");
		
		StringBuffer username = new StringBuffer();
		StringBuffer password = new StringBuffer();
		
		for(int i=0 ; i < authorization.substring(0, indx).length(); i++) {
			username.append(authorization.charAt(i));
		}
		System.out.println(username);
		
		String subPass = authorization.substring(indx+1);
		for(int i = 0; i < subPass.length(); i++) {
			password.append(subPass.charAt(i));
		}
		
		System.out.println(password);
		
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(password.toString().strip());
		User user = userService.getUserByUserName(username.toString().strip());
		
		user = passwordEncoder.matches(password, user.getPassword()) ? user : null;
		
		return user;
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return skipUrls.stream().anyMatch(p -> pathMatcher.match(p, request.getRequestURI()));
//		return request.getServletPath().equals("/sci/registration");
	}
	
	private User getUserByToken(HttpServletRequest request, HttpServletResponse response) {
		String authorization =  request.getHeader("Authorization");
		System.out.println("\ntoken in filter: " + authorization +"\n");
		if(authorization == null || authorization.isBlank() || authorization.isEmpty()) {
			 response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
//		User user = userService.getUserByTokenValue("D08-E585-47B4-8DB3-EAD51E7-883326429613079464035f1d08-e585-47b4-8db3-ead51e7c99bd");
		User user = userService.getUserByTokenValue(authorization);

		return user;	
	}
}
