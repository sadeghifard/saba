package com.saba.sci.security;

import java.security.Principal;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import reactor.core.publisher.Mono;

@ControllerAdvice
public class SecurityControllerAdvice {
	
//	@ModelAttribute("user")
//	public Mono<Principal> user(Mono<Principal> user){
//		return user;
//	}
}
