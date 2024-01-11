package com.shinhan.sbproject.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.shinhan.sbproject.vo2.PDSBoard;

public interface PDSBoardRepository extends CrudRepository<PDSBoard, Long>, QuerydslPredicateExecutor<PDSBoard> {

	// @query : select만 가능
	// DML 수행은 @Modifying 추가한다. test시 @transactional 필요하다.
	// board에서 file 수정 
	@Modifying 
	@Query("UPDATE PDSFile f set f. pdsfilename = ?2 WHERE f.fno = ?1 ") 
	int updatePDSFile(Long fno, String newFileName); 

	
}
