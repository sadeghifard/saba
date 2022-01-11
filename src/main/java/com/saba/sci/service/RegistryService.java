package com.saba.sci.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saba.sci.dto.RegistryForm;
import com.saba.sci.model.Registry;
import com.saba.sci.repository.RegistryRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RegistryService {
	
	@Autowired
	private final RegistryRepository registryRepository;
	
	public List<Registry> getAllRegisters(){
		return registryRepository.findAll();
	}
	
	public Registry getRegisteryById(Long id) {
		return registryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Registry data table not found: " + id));
	}
	
	public Registry save(Registry registry) {
		return registryRepository.save(registry);
	}
	
	public Registry update(Long id, Registry registry) {
		Registry existRegister = getRegisteryById(id);
		existRegister.setRegisterCode(registry.getRegisterCode());
		existRegister.setStationNo(registry.getStationNo());
		existRegister.setCustomer(registry.getCustomer());
		existRegister.setCreationDate(registry.getCreationDate());
		existRegister.setUser(registry.getUser());
		return registryRepository.save(existRegister);
	}
	
	public void delete(Long id) {
		Registry registry = getRegisteryById(id);
		registryRepository.delete(registry);
	}

	public List<Registry> getRegistryByUserId(Long id) {
		return registryRepository.getRegistryByUserId(id)
				.orElse(null);
	}
	
	
	public List<Registry> saveAll(List<Registry> registries) {
		List<Registry> registryList =  registryRepository.saveAll(registries);
		return registryList;
	}
}
