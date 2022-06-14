package com.saba.sci.service;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saba.sci.model.Token;
import com.saba.sci.model.User;
import com.saba.sci.repository.TokenRepository;
import com.saba.sci.utile.Utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data

@AllArgsConstructor
@Service
@Transactional
public class TokenService {
	private  final TokenRepository tokenRepository;
	private final UserService userService;
	
	
	@Qualifier(value = "myAuthenticationManager")
	private final AuthenticationManager authenticationManager;
	private final SecurityContextRepository contextRepository;
	
	
	public Token getTokenById(Long id) {
		return tokenRepository.findById(id).orElse(null);
	}
	
	public Token Save(Token token) {
		return tokenRepository.save(token);
	}
	
	public Token update(Long id, Token token) {
		Token existToken = getTokenById(id);
		if(existToken != null && token != null) {
			existToken.setTokenValue(token.getTokenValue());
			existToken.setCreationDate(LocalDateTime.now());
			return existToken;
		}			
		return null;
	}
	
	public Token getTokenByTokenValue(String tokenValue) {
		return tokenRepository.getTokenByTokenValue(tokenValue).orElse(null);
	}
	
	public String updateTokenValue(String tokenValue) {
		
		Token token = getTokenByTokenValue(tokenValue);
		
		if(token != null) {
			
			LocalDateTime creationDate = token.getCreationDate();
			Long creationTime = Utility.convertLocalDateTimeToDate(creationDate).getTime()/1000/60/60;
			Long currentTime = Utility.convertLocalDateTimeToDate(LocalDateTime.now()).getTime()/1000/60/60;
			
			if((currentTime - creationTime) >= 2) {
				System.out.println("==========="+ creationTime + "========" + currentTime +"===========" + (currentTime- creationTime));
				String newToken = Utility.tokenGenerator();
				return newToken;
			}
		}
		return tokenValue;
	}
	
	public User refreshTokenByUserName(String userName) {
		Token token = new Token();
		String tokenValue = Utility.tokenGenerator();
		token.setTokenValue(tokenValue);
		token.setCreationDate(LocalDateTime.now());
		token = tokenRepository.save(token);
		User user = userService.getUserByUserName(userName);
		user.setToken(token);
		
		return user;
	}
	
	public void SetTokenOnSecurityContext(String userName, String password, HttpServletRequest  request, HttpServletResponse response, Authentication auth) {
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password);
				
		try {
			auth = (auth != null) ? auth : authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
			contextRepository.saveContext(SecurityContextHolder.getContext(), request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
