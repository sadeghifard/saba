package com.saba.sci.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.saba.sci.model.Role;
import com.saba.sci.repository.RoleRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class RoleService {
	private RoleRepository roleRepository;
	
	public Role getRoleByName(String name) {
		return roleRepository.findByName(name).orElse(null);
	}
}
