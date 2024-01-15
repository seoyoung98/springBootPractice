package com.shinhan.sbproject.repository2;

import java.util.List;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shinhan.sbproject.vo6.WebBoard;
import com.shinhan.sbproject.vo6.WebReply;

public interface WebReplyRepository extends CrudRepository<WebReply, Long>, PagingAndSortingRepository<WebReply, Long>, QuerydslPredicateExecutor<WebReply>{
	
	// 기본 crud 제공
	// 추가 메서드 (규칙에 맞게 정의)
	List<WebReply> findByBoard(WebBoard board);
}
