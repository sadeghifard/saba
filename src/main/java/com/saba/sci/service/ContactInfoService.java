package com.saba.sci.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saba.sci.model.ContactInfo;
import com.saba.sci.repository.ContactInfoRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ContactInfoService {
	private final ContactInfoRepository contactInfoRepository;
	
	public List<ContactInfo> getAllContactInfo(){
		return contactInfoRepository.findAll();
	}
	
	public ContactInfo getContactInfoById(Long id) {
		return contactInfoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("ContactInfo not found: " + id));
	}
	
	public ContactInfo save(ContactInfo contactInfo) {
		return contactInfoRepository.save(contactInfo);
	}
	
	public ContactInfo update(Long id, ContactInfo contactInfo) {
		ContactInfo existContactInfo = getContactInfoById(id);
		existContactInfo.setDescription(contactInfo.getDescription());
		existContactInfo.setTelephone(contactInfo.getTelephone());
		existContactInfo.setCustomer(contactInfo.getCustomer());
		return contactInfoRepository.save(existContactInfo);
	}
	
	public void delete(Long id) {
		ContactInfo contactInfo = getContactInfoById(id);
		contactInfoRepository.delete(contactInfo);
	}
}
