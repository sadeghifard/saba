package com.saba.sci.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RESTLogoutSuccessHandler   extends SimpleUrlLogoutSuccessHandler {  

	
	@Transactional
    @Override  
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,  
            Authentication authentication) throws IOException, ServletException {  

    	
       
        super.onLogoutSuccess(request, response, authentication);         
    }  
} 
