package com.saba.sci.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saba.sci.dto.CustomerDetail;
import com.saba.sci.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	@Query(value = "select u from User u where u.userName = :userName")
	Optional<User> getUserByUserName(@Param("userName") String userName);
	
	@Query(value = "select u from User u where u.userName = :userName and u.password = :password")
	Optional<User> getUserByUserNameAndPassword(@Param("userName") String userNam,
												@Param("password") String password);
	
	@Query(value="    select distinct \r\n"
			+ "        u.id as userId, \r\n"
			+ "        u.user_name as userName,\r\n"
			+ "        u.role_name as roleName,\r\n"
			+ "        c.company_name as companyName,\r\n"
			+ "        c.owner_name as ownerName,\r\n"
			+ "        c.address as address,\r\n"
			+ "        c.serial_num as serialNum,\r\n"
			+ "        c.station_num as stationNum,\r\n"
			+ "        ci.id as contactId,\r\n"
			+ "        ci.tel as telephone,\r\n"
			+ "        ci.des as description,\r\n"
//			+ "        r.register_code as registerCode,\r\n"
//			+ "        r.station_no as stationNo,\r\n"
			+ "        cf.setup_date as setupDate,\r\n"
			+ "        cf.garanty_date as garantyDate,\r\n"
			+ "        cf.sub_start_date as subStartDate,\r\n"
			+ "        cf.sub_end_date as subEndDate,\r\n"
			+ "        cf.sub_annual as subAnnual,\r\n"
			+ "        cf.sub_payment as subPayment,  \r\n"
			+ "		   a.token_Value as tokenValue\r\n"
			+ "    from\r\n"
			+ "        users u        \r\n"
			+ "    join\r\n"
			+ "        customer c     \r\n"
			+ "            on(\r\n"
			+ "                u.customer_id = c.id\r\n"
			+ "            )      \r\n"
			+ "    left outer join\r\n"
			+ "        registers r         \r\n"
			+ "            on(\r\n"
			+ "                r.customer_id = c.id\r\n"
			+ "            )      \r\n"
			+ "    join\r\n"
			+ "        contact_info ci    \r\n"
			+ "            on(\r\n"
			+ "                ci.customer_id = c.id\r\n"
			+ "            )      \r\n"
			+ "    left outer join\r\n"
			+ "        customer_info cf        \r\n"
			+ "            on(\r\n"
			+ "                cf.customer_id = c.id\r\n"
			+ "            )\r\n"
			+ "	join\r\n"
			+ "		auth_token a \r\n"
			+ "			on(\r\n"
			+ "				u.token_id= a.id\r\n"
			+ "			)\r\n"
			+ "    where\r\n"
			+ "        u.user_name= :userName" , nativeQuery = true)	
	Optional<CustomerDetail> getCustomerInfoByUserName(@Param("userName") String userName);
	
	@Query(value="    select distinct \r\n"
			+ "        u.user_name as userName,\r\n"
			+ "        u.id as userId, \r\n"
			+ "        u.role_name as roleName,\r\n"
			+ "        c.company_name as companyName,\r\n"
			+ "        c.owner_name as ownerName,\r\n"
			+ "        c.address as address,\r\n"
			+ "        c.serial_num as serialNum,\r\n"
			+ "        c.station_num as stationNum,\r\n"
			+ "        ci.id as contactId,\r\n"
			+ "        ci.tel as telephone,\r\n"
			+ "        ci.des as description,\r\n"
//			+ "        r.register_code as registerCode,\r\n"
//			+ "        r.station_no as stationNo,\r\n"
			+ "        cf.setup_date as setupDate,\r\n"
			+ "        cf.garanty_date as garantyDate,\r\n"
			+ "        cf.sub_start_date as subStartDate,\r\n"
			+ "        cf.sub_end_date as subEndDate,\r\n"
			+ "        cf.sub_annual as subAnnual,\r\n"
			+ "        cf.sub_payment as subPayment,  \r\n"
			+ "		   a.token_Value as tokenValue\r\n"
			+ "    from\r\n"
			+ "        users u        \r\n"
			+ "    join\r\n"
			+ "        customer c     \r\n"
			+ "            on(\r\n"
			+ "                u.customer_id = c.id\r\n"
			+ "            )      \r\n"
			+ "    left outer join\r\n"
			+ "        registers r         \r\n"
			+ "            on(\r\n"
			+ "                r.customer_id = c.id\r\n"
			+ "            )      \r\n"
			+ "    join\r\n"
			+ "        contact_info ci      \r\n"
			+ "            on(\r\n"
			+ "                ci.customer_id = c.id\r\n"
			+ "            )      \r\n"
			+ "    left outer join\r\n"
			+ "        customer_info cf        \r\n"
			+ "            on(\r\n"
			+ "                cf.customer_id = c.id\r\n"
			+ "            )\r\n"
			+ "	join\r\n"
			+ "		auth_token a \r\n"
			+ "			on(\r\n"
			+ "				u.token_id= a.id\r\n"
			+ "			)\r\n"
			+ "    where\r\n"
			+ "        u.id= :id" , nativeQuery = true)	
	Optional<CustomerDetail> getCustomerInfoByUserId(@Param("id") Long id);

	
	@Query(value="    select distinct \r\n"
			+ "        u.user_name as userName,\r\n"
			+ "        u.id as userId, \r\n"
			+ "        u.role_name as roleName,\r\n"
			+ "        c.company_name as companyName,\r\n"
			+ "        c.owner_name as ownerName,\r\n"
			+ "        c.address as address,\r\n"
			+ "        c.serial_num as serialNum,\r\n"
			+ "        c.station_num as stationNum,\r\n"
			+ "        ci.id as contactId,\r\n"
			+ "        ci.tel as telephone,\r\n"
			+ "        ci.des as description,\r\n"
//			+ "        r.register_code as registerCode,\r\n"
//			+ "        r.station_no as stationNo,\r\n"
			+ "        cf.setup_date as setupDate,\r\n"
			+ "        cf.garanty_date as garantyDate,\r\n"
			+ "        cf.sub_start_date as subStartDate,\r\n"
			+ "        cf.sub_end_date as subEndDate,\r\n"
			+ "        cf.sub_annual as subAnnual,\r\n"
			+ "        cf.sub_payment as subPayment,  \r\n"
			+ "		   a.token_Value as tokenValue\r\n"
			+ "    from\r\n"
			+ "        users u        \r\n"
			+ "    join\r\n"
			+ "        customer c     \r\n"
			+ "            on(\r\n"
			+ "                u.customer_id = c.id\r\n"
			+ "            )      \r\n"
			+ "    left outer join\r\n"
			+ "        registers r         \r\n"
			+ "            on(\r\n"
			+ "                r.customer_id = c.id\r\n"
			+ "            )      \r\n"
			+ "    join\r\n"
			+ "        contact_info ci      \r\n"
			+ "            on(\r\n"
			+ "                ci.customer_id = c.id\r\n"
			+ "            )      \r\n"
			+ "    left outer join\r\n"
			+ "        customer_info cf        \r\n"
			+ "            on(\r\n"
			+ "                cf.customer_id = c.id\r\n"
			+ "            )\r\n"
			+ "	join\r\n"
			+ "		auth_token a \r\n"
			+ "			on(\r\n"
			+ "				u.token_id= a.id\r\n"
			+ "			)\r\n" , nativeQuery = true)	
	Optional<List<CustomerDetail>> getAllCustomersInfo();
}


