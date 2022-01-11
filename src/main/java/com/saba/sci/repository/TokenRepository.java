package com.saba.sci.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saba.sci.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

	@Query(value = "select t from Token t where t.tokenValue = :tokenValue")
	Optional<Token> getTokenByTokenValue(@Param("tokenValue") String tokenValue);

}
