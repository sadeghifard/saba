package com.saba.sci.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer_log")
public class CustomerLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "serial_num")
	private Integer serialNum;
	
	@Column(name = "station_no")
	private Integer stationNum;
	
	@Column(name = "description")
	@Lob
	private String description;
	
	@Column(name = "start_date", 	nullable = true)
	private LocalDate startDate;
	
	@Column(name = "start_time")
	private String startTime;
	
	@Column(name = "end_date", nullable = true)
	private LocalDate endDate;
	
	@Column(name = "end_time")
	private String endTime;
	
	@Column(name="reg_cod_log", nullable = true)
	private String registerCodeLog;
	
	@Column(name = "computer_name_log")
	private String computerNameLog;
	
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Customer.class)
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	private Customer customer;
	
}
