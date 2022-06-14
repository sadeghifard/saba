package com.saba.sci;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.saba.sci.model.Role;
import com.saba.sci.model.User;
import com.saba.sci.repository.CustomerRepository;
import com.saba.sci.repository.RoleRepository;
import com.saba.sci.repository.UserRepository;

import lombok.RequiredArgsConstructor;

//@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@SpringBootApplication
@RequiredArgsConstructor
public class SabaCustomersInfomationApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(SabaCustomersInfomationApplication.class, args);
	}
 
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public void run(String... args) throws Exception {
//		Role r1 = new Role();
//		r1.setName("ADMIN");
//		roleRepository.save(r1);
//		
//		Role r2 = new Role();
//		r2.setName("USER");
//		roleRepository.save(r2);
	}

}
