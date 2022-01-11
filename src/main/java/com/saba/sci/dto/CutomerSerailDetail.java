package com.saba.sci.dto;

import org.springframework.stereotype.Component;

@Component
public interface CutomerSerailDetail {
	Integer getSerialNum();
	Integer getStationNum();
	Long getCustomerId();
	String getUserName();
}
