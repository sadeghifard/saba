package com.saba.sci.dto;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor	
public class CustomerDetailImple {
	private Long userId;
	private String userName;
	private String roleName;
	private String companyName;
	private String ownerName;
	private String address;
	private String serialNum;
	private Integer stationNum;
	private Long contactId;
	private String telephone;
	private String description;
	private String registerCode;
	private Integer stationNo;
	private String tokenValue;
	
//	private Long customerInfoId;
	private LocalDate setupDate;
	private LocalDate garantyDate;
	private LocalDate subStartDate;
	private LocalDate subEndDate;
	private Integer subAnnual;
	private Integer subPayment;

}
