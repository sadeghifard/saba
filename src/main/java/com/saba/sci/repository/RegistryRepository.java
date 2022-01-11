package com.saba.sci.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saba.sci.model.Registry;

@Repository
public interface RegistryRepository extends JpaRepository<Registry, Long>{

	@Query(value = "select distinct r.*\r\n"
			+ "from users u \r\n"
			+ "join registers r\r\n"
			+ "on(u.customer_id = r.customer_id)\r\n"
			+ "where u.id = :id", nativeQuery = true)
	Optional<List<Registry>> getRegistryByUserId(@Param("id") Long id);
}
