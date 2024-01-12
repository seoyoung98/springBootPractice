package com.shinhan.sbproject.repository2;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shinhan.sbproject.vo6.WebReply;

public interface WebReplyRepository extends CrudRepository<WebReply, Long>, PagingAndSortingRepository<WebReply, Long>, QuerydslPredicateExecutor<WebReply>{
	
}
