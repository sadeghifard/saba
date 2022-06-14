package com.saba.sci.dto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.type.LocalDateTimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.saba.sci.model.Token;
import com.saba.sci.repository.TokenRepository;
import com.saba.sci.service.TokenService;
import com.saba.sci.utile.Utility;

public class Test {
    
	public static void main(String[] args) throws InterruptedException, NoSuchAlgorithmException {
		String authorization = "reza-reza";
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
		String encodedPassword = passwordEncoder.encode(password);
		System.out.println(encodedPassword);
		
		 encodedPassword = passwordEncoder.encode(password);
			System.out.println(encodedPassword);
		}
	

}
