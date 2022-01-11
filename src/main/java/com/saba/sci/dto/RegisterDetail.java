package com.saba.sci.dto;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDetail {

	private String companyName;
	private String ownerName;
	private String address;
	private Integer stationNum;
	private String telephone;
	private String description;
	private String userName;
	private String password;

}
