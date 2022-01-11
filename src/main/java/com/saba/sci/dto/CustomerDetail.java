package com.saba.sci.dto;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
public interface CustomerDetail {
	Long getUserId();
	String getUserName();
	String getRoleName();
	String getCompanyName();
	String getOwnerName();
	String getAddress();
	String getSerialNum();
	Integer getStationNum();
	Long getContactId();
	String getTelephone();
	String getDescription();
	String getRegisterCode();
	Integer getStationNo();
	String getTokenValue();
	
//	Long getCustomerInfoId();
	LocalDate getSetupDate();
	LocalDate getGarantyDate();
	LocalDate getSubStartDate();
	LocalDate getSubEndDate();
	Integer getSubAnnual();
	Integer getSubPayment();

}
