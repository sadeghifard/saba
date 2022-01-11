package com.saba.sci.dto;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

import org.hibernate.type.LocalDateTimeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.saba.sci.model.Token;
import com.saba.sci.repository.TokenRepository;
import com.saba.sci.service.TokenService;
import com.saba.sci.utile.Utility;

public class Test {
    
	public static void main(String[] args) throws InterruptedException {

		LocalDateTime dateTime1 = LocalDateTime.now();
		Date date1 = Utility.convertLocalDateTimeToDate(dateTime1);
		Long time1 = date1.getTime()/1000/60;
		System.out.println(time1);
		Thread.sleep(60000);
	
		LocalDateTime dateTime2 = LocalDateTime.now();
		Date date2 = Utility.convertLocalDateTimeToDate(dateTime2);
		Long time2 = date2.getTime()/1000/60;
		System.out.println(time2);
		 Long time = time2-time1;
		
		System.out.println(time);
	}
}
