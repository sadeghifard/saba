package com.saba.sci.model;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "customer_info")
public class CustomerInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "setup_date", nullable = true)
	private LocalDate setupDate;
	
	@Column(name = "garanty_date", nullable = true)
	private LocalDate garantyDate;
	
	@Column(name = "sub_start_date", nullable = true)
	private LocalDate subStartDate;
	
	@Column(name = "sub_end_date", nullable = true)
	private LocalDate subEndDate;
	
	@Column(name = "sub_annual", nullable = true)
	private Integer subAnnual;
	
	@Column(name = "sub_payment", nullable = true)
	private Integer subPayment;
	
	@OneToOne(cascade = CascadeType.ALL,targetEntity = Customer.class)
	@JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = true)
	private Customer customer;

}
