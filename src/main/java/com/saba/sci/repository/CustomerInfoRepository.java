package com.saba.sci.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saba.sci.model.CustomerInfo;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Long> {

	@Query(value = "select cf.*\r\n"
			+ "from customer_info cf\r\n"
			+ "join customer c\r\n"
			+ "on(cf.customer_id = c.id)\r\n"
			+ "where c.id = :id", nativeQuery = true)
	Optional<CustomerInfo> getCustomerInfoByCustomerId(@Param("id") Long id);
}
