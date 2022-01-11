package com.saba.sci.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
	@Id
//	@SequenceGenerator(name = "person_sequence", sequenceName = "person_sequence", allocationSize = 1)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_sequence")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "owner_name")
	private String ownerName;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "serial_num")
	private Integer serialNum;
	
	@Column(name = "station_num")
	private Integer stationNum;
	
}
