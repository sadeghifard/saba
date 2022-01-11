package com.saba.sci;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.saba.sci.model.User;
import com.saba.sci.repository.CustomerRepository;
import com.saba.sci.repository.UserRepository;

import lombok.RequiredArgsConstructor;

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
	
	@Override
	public void run(String... args) throws Exception {
//		User u1 = new User();
//		u1.setUserName("test1");
//		u1.setPassword("test1");
//		u1.setPerson(customerRepository.findById(1L).get());
//		userRepository.save(u1);
//		
//		User u2 = new User();
//		u2.setUserName("test2");
//		u2.setPassword("test2");
//		u2.setPerson(customerRepository.findById(3L).get());
//		userRepository.save(u2);
//		
//		User u3 = new User();
//		u3.setUserName("test3");
//		u3.setPassword("test3");
//		u3.setPerson(customerRepository.findById(4L).get());
//		userRepository.save(u3);
//		
//		User u4 = new User();
//		u4.setUserName("test4");
//		u4.setPassword("test4");
//		u4.setPerson(customerRepository.findById(5L).get());
//		userRepository.save(u4);
//		
	}

}
