package com.saba.sci.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saba.sci.model.Token;
import com.saba.sci.repository.TokenRepository;
import com.saba.sci.utile.Utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data

@RequiredArgsConstructor
@Service
@Transactional
public class TokenService {
	private  final TokenRepository tokenRepository;
	
	
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
				System.out.println("========================="+ creationTime + "========" + currentTime +"===========" + (currentTime- creationTime));
				String newToken = Utility.tokenGenerator();
				return newToken;
			}
		}
		return tokenValue;
	}
}
