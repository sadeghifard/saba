package com.saba.sci.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saba.sci.model.ContactInfo;

@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long>{

}
