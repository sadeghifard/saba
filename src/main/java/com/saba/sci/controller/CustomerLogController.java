package com.saba.sci.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.saba.sci.model.CustomerLog;
import com.saba.sci.service.CustomerLogService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sci/")
public class CustomerLogController {
	
	private final CustomerLogService customerLogService;
	
//	@CrossOrigin
//	@GetMapping("/private/logs")
//	public ResponseEntity<List<CustomerLog>> getAllCustomerLogs(){
//		List<CustomerLog> customerLogs = customerLogService.getAllCustomerLogs();
//		if(customerLogs == null) {
//			return new ResponseEntity("Logs not found", HttpStatus.NOT_FOUND);
//		} else {
//			return new ResponseEntity<List<CustomerLog>>(customerLogs, HttpStatus.OK);
//		}
//	}
	
//	@CrossOrigin
//	@GetMapping("/private/logs/{id}")
//	public ResponseEntity<CustomerLog> getLogByCustomerLogId(@PathVariable("id") Long id){
//		CustomerLog customerLog =  customerLogService.getCustomerLogById(id);
//		
//		if(customerLog != null) {
//			return new ResponseEntity<CustomerLog>(customerLog,HttpStatus.OK);
//		} else {
//			return new ResponseEntity<CustomerLog>(customerLog, HttpStatus.NOT_FOUND);
//		}
//	}
	
	@CrossOrigin
	@GetMapping("/private/logs_2538/{id}")
	public ResponseEntity<List<CustomerLog>> getCustomerLogByUserId(@PathVariable("id") Long id){
		List<CustomerLog> customerLogs = customerLogService.getCustomerLogByUserId(id);
		customerLogs = customerLogs.stream().filter(c -> c != null).collect(Collectors.toList());
		
		if (customerLogs != null && customerLogs.size()>0){
			System.out.println("it is not null? " + customerLogs);
			return new ResponseEntity<List<CustomerLog>>(customerLogs,HttpStatus.OK);
		} else {
			System.out.println("it is null? " + customerLogs);
			return new ResponseEntity<List<CustomerLog>>(new ArrayList<>(),HttpStatus.NO_CONTENT);
		}
	}
	
	@CrossOrigin
	@PutMapping("/private/logs_2538/{id}")
	public ResponseEntity<CustomerLog> updateLogs(@PathVariable("id") Long id, @RequestBody CustomerLog customerLog){
		
		CustomerLog existCustomerLog = customerLogService.update(id, customerLog);
		if(existCustomerLog == null) {
			return new ResponseEntity<CustomerLog>(existCustomerLog, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<CustomerLog>(existCustomerLog, HttpStatus.OK);
		}
	}
	
	@CrossOrigin
	@PostMapping("/private/logs_2538/{id}")
	public ResponseEntity<CustomerLog> saveLog(@PathVariable("id") Long id, @RequestBody CustomerLog customerLog){
	    CustomerLog log =  customerLogService.save(id, customerLog);
		return new ResponseEntity<CustomerLog>(log,HttpStatus.CREATED);
	}
	
	
	
	@CrossOrigin
	@DeleteMapping("/private/logs_2538/{id}")
	public ResponseEntity<String> DeleteLog(@PathVariable("id") Long id) {
		customerLogService.delete(id);
		return new ResponseEntity("Log deleted", HttpStatus.OK);
	}
	
	
}
