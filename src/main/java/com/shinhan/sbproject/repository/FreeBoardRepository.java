package com.shinhan.sbproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.shinhan.sbproject.vo3.FreeBoard;

public interface FreeBoardRepository extends CrudRepository<FreeBoard, Long>, PagingAndSortingRepository<FreeBoard, Long>, QuerydslPredicateExecutor<FreeBoard>{
	List<FreeBoard> findByBnoGreaterThan(Long bno, Pageable page);
	List<FreeBoard> findByBnoBetween(Long bno, Long bno2, Pageable page);
	Page<FreeBoard> findByWriter(String writer, Pageable page);
	
	// Title이 특정 문자를 포함하는 board를 얻기, sort, 특정 칼럼만 select 하기
	@Query("select board.bno, board.title, board.writer from FreeBoard board where board.title like %?1% order by board.bno DESC")
	List<Object[]> selectByTitle(String title);
	
	@Query("select board.bno, board.title, board.writer from #{#entityName} board where board.title like %:tt% order by board.bno DESC")
	List<Object[]> selectByTitle2(@Param("tt") String title);

	@Query(value = "select board.bno, board.title, board.writer from tbl_freeboards_young board where board.title like concat('%',:tt,'%') order by board.bno DESC", nativeQuery = true)
	List<Object[]> selectByTitle3(@Param("tt") String title);
}
