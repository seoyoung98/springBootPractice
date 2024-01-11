package com.shinhan.sbproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.shinhan.sbproject.vo.BoardVO;

// 1. 기본 crud 작업 => crudRepository 상속 : findAll, findById, save, count, delete
// crudRepository를 상속 받은 PagingAndSortingRepository
public interface BoardRepository extends CrudRepository<BoardVO, Long>, PagingAndSortingRepository<BoardVO, Long>, QuerydslPredicateExecutor<BoardVO>{
	// 2. 규칙에 맞는 메서드 정의
	List<BoardVO> findByWriter(String writer);
	List<BoardVO> findByContent(String content);
	List<BoardVO> findByBnoGreaterThan(Long bno);
	List<BoardVO> findByContentLike(String content);
	List<BoardVO> findByContentContaining(String content);
	List<BoardVO> findByBnoGreaterThanAndBnoLessThanEqual(Long bno, Long bno2);
	List<BoardVO> findByBnoBetweenAndContentStartingWithOrderByWriterDesc(Long bno,Long bno2,String content);
	int countByWriter(String writer);
	// paging, sort 추가
	List<BoardVO> findByBnoGreaterThan(Long bno, Pageable paging);
	Page<BoardVO> findByBnoBetween(Long bno, Long bno2, Pageable paging);
	
	
	//3. JPQL(JPA Query Language) : 규칙에 맞는 함수 정의가 한계가 있다. 이를 해결하는 방법이다. 
	@Query("select b from BoardVO b where b.title like %?2% and b.writer like %?3% and b.bno > ?1 order by bno desc")
	List<BoardVO> selectByTitleAndWriter(Long bno,String title, String writer);
	
//	@Query("select * from tbl_boards_young b where b.title like %?2% and b.writer like %?3% and b.bno > ?1 order by bno desc")
//	List<BoardVO> selectByTitleAndWriter3(Long bno,String title, String writer);
	
	@Query("select b from BoardVO b where b.title like %:tt% and b.writer like %:ww% and b.bno > :bb order by bno desc")
	List<BoardVO> selectByTitleAndWriter4(@Param("bb") Long bno,@Param("tt") String title,@Param("ww") String writer);
	
	@Query("select b from #{#entityName} b where b.title like %:tt% and b.writer like %:ww% and b.bno > :bb order by bno desc")
	List<BoardVO> selectByTitleAndWriter5(@Param("bb") Long bno,@Param("tt") String title,@Param("ww") String writer);
	
	@Query("select b from #{#entityName} b where b.title like %:tt% and b.writer like %:ww% and b.bno > :bb order by bno desc")
	List<BoardVO> selectByTitleAndWriter6(@Param("bb") Long bno,@Param("tt") String title,@Param("ww") String writer);
	
	@Query("select board.title, board.content from #{#entityName} board where board.writer = :wr")
	List<Object[]> selectByWriter(@Param("wr") String writer);
}
