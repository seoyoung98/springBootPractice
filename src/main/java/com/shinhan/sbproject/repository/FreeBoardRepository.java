package com.shinhan.sbproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shinhan.sbproject.vo3.FreeBoard;

public interface FreeBoardRepository extends CrudRepository<FreeBoard, Long>, PagingAndSortingRepository<FreeBoard, Long>, QuerydslPredicateExecutor<FreeBoard>{
	List<FreeBoard> findByBnoGreaterThan(Long bno, Pageable page);
	List<FreeBoard> findByBnoBetween(Long bno, Long bno2, Pageable page);
	Page<FreeBoard> findByWriter(String writer, Pageable page);
	
}
