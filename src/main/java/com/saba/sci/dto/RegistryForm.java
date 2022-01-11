package com.saba.sci.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistryForm {
	private String registerCode;
	private Integer stationNo;
	private String registerUserNanme;
	private Date creationDate;
}
