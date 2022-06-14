package com.saba.sci.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saba.sci.dto.RegistryForm;
import com.saba.sci.model.Registry;
import com.saba.sci.service.RegistryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sci/")
public class RegisteryController {
	private final RegistryService registryService;
	
	@CrossOrigin
	@GetMapping("/private/reg_2538/{id}")
	public ResponseEntity<List<Registry>> getRegisteryByUserId(@PathVariable("id") Long id){
		List<Registry> registries =  registryService.getRegistryByUserId(id);
		
		if(registries != null && registries.size() > 0) {
			return new ResponseEntity<List<Registry>>(registries, HttpStatus.OK);
		} else {
			return new ResponseEntity<List<Registry>>(registries, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@CrossOrigin
	@PostMapping("/private/reg_2538")
	public ResponseEntity<List<Registry>> saveRegistry(@RequestBody List<Registry> registries ){
		List<Registry> existRegisterList =  registryService.saveAll(registries);

		if(existRegisterList != null && existRegisterList.size() > 0) {
			return new ResponseEntity<List<Registry>>(existRegisterList, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<List<Registry>>(existRegisterList, HttpStatus.NO_CONTENT);
		}
	}
	
	@CrossOrigin
	@PutMapping("/private/reg_2538/{id}")
	public ResponseEntity<Registry> updateRegistry(@PathVariable("id") Long id, @RequestBody Registry registry) {
		Registry existRegistery =  registryService.update(id, registry);
		
		if(existRegistery != null) {
			return new ResponseEntity<Registry>(existRegistery, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<Registry>(existRegistery, HttpStatus.OK);
		}
	}
	
	
}
