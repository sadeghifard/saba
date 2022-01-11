package com.saba.sci.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contact_info")
public class ContactInfo {
	@Id
//	@SequenceGenerator(name = "contact_info_sequence", sequenceName = "contact_info_sequence", allocationSize = 1)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_info_sequence")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "tel")
	private String telephone;

	@Column(name = "des", nullable = true)
	@Lob
	private String description;
	
	@ManyToOne(targetEntity = Customer.class)
	@JoinColumn(name = "customer_id",referencedColumnName = "id")
	private Customer customer;

}
