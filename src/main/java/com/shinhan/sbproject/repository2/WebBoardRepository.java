package com.shinhan.sbproject.repository2;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.shinhan.sbproject.vo6.QWebBoard;
import com.shinhan.sbproject.vo6.WebBoard;

public interface WebBoardRepository extends CrudRepository<WebBoard, Long>, PagingAndSortingRepository<WebBoard, Long>,
		QuerydslPredicateExecutor<WebBoard> {
	// default function 추가
	// type : title, content, writer
	// keyword : word
	public default Predicate makePredicate(String type, String keyword) {
		System.out.println("typemake" + type);
		System.out.println("keywordmake" + keyword);
		BooleanBuilder builder = new BooleanBuilder();
		QWebBoard board = QWebBoard.webBoard;
//		builder.and(board.bno.gt(0)); // and bn0 > 0
		System.out.println("gg" + type);
		if (type == null) {
			return builder;
		}	
			
		if(type.equals("전체")) {
			builder.or(board.title.like("%" + keyword + "%"));
			builder.or(board.content.like("%" + keyword + "%"));
			builder.or(board.writer.like("%" + keyword + "%"));
		}

		switch (type) {
		case "title":
			builder.and(board.title.like("%" + keyword + "%"));
			break;
		case "content":
			builder.and(board.content.like("%" + keyword + "%"));
			break;	
		case "writer":
			builder.and(board.writer.like("%" + keyword + "%"));
			break;
		}
		return builder;

	}
}
