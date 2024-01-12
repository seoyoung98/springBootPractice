package com.shinhan.sbproject.repository2;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shinhan.sbproject.vo6.WebBoard;

public interface WebBoardRepository extends CrudRepository<WebBoard, Long>, PagingAndSortingRepository<WebBoard, Long>, QuerydslPredicateExecutor<WebBoard>{
	
}
