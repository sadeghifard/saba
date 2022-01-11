package com.saba.sci.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.saba.sci.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	@Query(value = "SELECT * FROM customer c where c.id =(select Max(c.id) from customer c)", nativeQuery = true)
	Optional<Customer> getLastCustomer();
}
