package com.shinhan.sbproject.repository;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.shinhan.sbproject.vo2.EmpDTO;
import com.shinhan.sbproject.vo2.PDSFile;

public interface EmpRepository2 extends CrudRepository<EmpDTO, Long>, QuerydslPredicateExecutor<EmpDTO> {
	
}
