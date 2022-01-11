package com.saba.sci.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.saba.sci.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	@Query(value = "select * from roles r where r.role_name = :roleName", nativeQuery = true)
	Optional<Role> findByRoleName(@Param("roleName")  String roleName);

}
