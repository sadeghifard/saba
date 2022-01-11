package com.saba.sci.model;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "registers")
public class Registry {
	@Id
//	@SequenceGenerator(name = "register_sequence", sequenceName = "register_sequence", allocationSize = 1)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "register_sequence")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "station_no", nullable = true)
	
	private Integer stationNo;
	
	@Column(name = "register_code", nullable = true)
	private String registerCode;
	
	@Column(name = "computer_name", nullable = true)
	private String computerName;
	
	@Column(name = "register_user_name", nullable = true)
	private String registerUserName;
	
	@Column(name = "creation_date", nullable = true)
	private LocalDate creationDate;
	
	@ManyToOne(targetEntity = Customer.class)
	@JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = true)
	private Customer customer;
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
	private User user;

}
