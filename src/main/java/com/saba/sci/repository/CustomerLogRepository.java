package com.saba.sci.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saba.sci.dto.CutomerSerailDetail;
import com.saba.sci.model.CustomerLog;

@Repository
public interface CustomerLogRepository extends JpaRepository<CustomerLog, Long>{

	
	@Query(value = "select distinct(c.serial_num) as serialNum,\r\n"
			+ "	   c.station_num as stationNum, \r\n"
			+ "	   c.id as customerId, \r\n"
			+ "	   u.user_name as userName \r\n"
			+ "from users u \r\n"
			+ "left outer join customer_log cl \r\n"
			+ "	on u.customer_id = cl.customer_id \r\n"
			+ "join customer c \r\n"
			+ "	on u.customer_id = c.id \r\n"
			+ "where u.id = :id", nativeQuery = true)
	Optional<CutomerSerailDetail> getCustomerSerialByUserId(@Param("id") Long id);
	
	@Query(value = "select cl.*\r\n"
			+ "from users u\r\n"
			+ "left outer join customer_log cl\r\n"
			+ "	on u.customer_id = cl.customer_id\r\n"
			+ "where u.id = :id", nativeQuery = true)
	Optional<List<CustomerLog>> getCustomerLogByUserId(@Param("id") Long id);

	
	@Query(value = "select cl.*\r\n"
			+ "from customer c \r\n"
			+ "join customer_log cl\r\n"
			+ "	on c.id = cl.customer_id\r\n"
			+ "where cl.id = :id", nativeQuery = true)
	Optional<CustomerLog> getCustomerLogById(@Param("id") Long id);
}
