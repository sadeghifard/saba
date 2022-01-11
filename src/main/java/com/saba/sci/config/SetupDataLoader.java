package com.saba.sci.config;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.saba.sci.model.Role;
import com.saba.sci.model.User;
import com.saba.sci.repository.RoleRepository;
import com.saba.sci.repository.UserRepository;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent>{

		@Autowired
	    private UserRepository userRepository;
	 
	    @Autowired
	    private RoleRepository roleRepository;
	 
	    @Autowired
	    private PasswordEncoder passwordEncoder;
	
	    boolean alreadySetup = false;
	    
	@Transactional
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (alreadySetup) {
            return;
		}
		
//		Role adminRole = roleRepository.findByRoleName("ADMIN").orElse(null);
//        User user = new User();
//        user.setUserName("test");
//        user.setPassword(passwordEncoder.encode("test"));
//        Set<Role> roles = new HashSet<>(Arrays.asList(adminRole));
//        
//        user.setRoles(roles);
//   
//        userRepository.save(user);
//        
        alreadySetup = true;
	}

}
