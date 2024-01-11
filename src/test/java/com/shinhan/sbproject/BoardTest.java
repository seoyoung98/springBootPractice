package com.shinhan.sbproject;

import static org.mockito.ArgumentMatchers.nullable;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.BooleanBuilder;
import com.shinhan.sbproject.repository.BoardRepository;
import com.shinhan.sbproject.vo.BoardVO;
import com.shinhan.sbproject.vo.QBoardVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class BoardTest {

	@Autowired
	BoardRepository brepo;
	
	@Test
	void f16() {
		
		// BooleanBuilder -> 조건에 맞으면 build 한다.
		BooleanBuilder builder = new BooleanBuilder();
		QBoardVO board = QBoardVO.boardVO;
		
		Long bno = 5L;
		String writer = "user 0";
		String content = "%재미%";
		
		// 동적으로 집어넣기 
		if(bno != null)
			builder.and(board.bno.gt(bno));
		if(writer != null)
			builder.and(board.writer.eq(writer));
		if(content != null)
			builder.and(board.content.like(content));
		
		log.info(builder.toString());
		// boardVO.bno > 5 && boardVO.writer = user 0 && boardVO.content like %재미%
		
		// 동적 sql 만들기
		brepo.findAll(builder).forEach(i -> {
			log.info("boolean" + i);
		});
	}
	
	//@Test
	void f15() {
//		List<Object[]> bList = brepo.selectByWriter("user");
		brepo.selectByWriter("user 3").forEach(s -> {
			log.info("title : " + s[0]);
			log.info("content : " + s[1]);
			log.info("------------");
		});
	}
	
	//@Test
	void f14() {
		brepo.selectByTitleAndWriter(5L, "java", "user").forEach(i -> {
			log.info("왁왁왁" + i.toString());
		});
	}
	
	//@Test
	void f13() {
//		Pageable paging = PageRequest.of(1, 6);
//		Pageable paging = PageRequest.of(1, 5, Sort.by(Sort.Direction.DESC,"writer","title"));
		Pageable paging = PageRequest.of(1, 5, Sort.by("writer").ascending());
//		Page<BoardVO> result = brepo.findAll(paging);
		Page<BoardVO> result = brepo.findByBnoBetween(1L, 10L, paging);
		
		log.info("getsize" + result.getSize());
		log.info("getNumber" + result.getNumber());
		log.info("getNumberOfElements" + result.getNumberOfElements());
		log.info("getTotalPages" + result.getTotalPages());
		log.info("getPageable" + result.getPageable());
		log.info("getSort" + result.getSort());
		
		result.getContent().forEach(i -> {
			log.info("content" + i);
		});
		
	}
	
	//@Test
	void f12() {
		Pageable paging = PageRequest.of(1, 6);
		brepo.findByBnoGreaterThan(5L, paging).forEach(i -> {
			log.info("페이징 : " + i);
		});;
	}
	
	//@Test
	void f11() {
		String writer = "정진 0";
		int count = brepo.countByWriter(writer);
		log.info("user가 작성한 board 건수 : " + count);
		
		brepo.findByWriter(writer).forEach(b -> {
			log.info(b.toString());
		});
	}
	
	//@Test
	void f10() {
		List<BoardVO> blist = brepo.findByContentLike("%재미있다%");
		blist.forEach(i -> {
			log.info("같다 " + i);
		});
		
		List<BoardVO> blist2 = brepo.findByContentContaining("재미있다");
		blist2.forEach(i -> {
			log.info("자동 같다 " + i);
		});
		
		List<BoardVO> blist3 = brepo.findByBnoGreaterThanAndBnoLessThanEqual(10L, 30L);
		blist3.forEach(i -> {
			log.info("크고 작거나 같다 " + i);
		});
		
		List<BoardVO> blist4 = brepo.findByBnoBetweenAndContentStartingWithOrderByWriterDesc(10L, 110L, "재미");
		blist4.forEach(i -> {
			log.info("해보자 " + i);
		});
	}
	
	//@Test
	void f9() {
		List<BoardVO> blist = brepo.findByWriter("정진 0");
		List<BoardVO> blist2 = brepo.findByContent("재미있다.. 27");
		List<BoardVO> blist3 = brepo.findByBnoGreaterThan(50L);
		blist.forEach(i -> {
			log.info("뿅 " +i.toString());
		});
		blist2.forEach(i -> {
			log.info("샤라랑 " +i.toString());
		});
		blist3.forEach(i -> {
			log.info("크다 " +i.toString());
		});
	}
	
	//@Test
	void f8() {
		IntStream.rangeClosed(21, 40).forEach(i->{
			BoardVO board = BoardVO.builder()
					.title("java " + i)
					.content("재미있다.. " + i)
					.writer("user " + i%5)
					.build();
			BoardVO new_board = brepo.save(board);
			log.info("생성된 board : " + board);
			log.info("입력된 board : " + new_board);
			log.info(board.equals(new_board)?"내용같음":"내용다름");
		});
	}
	
	//@Test
	void f7() {
		Long serachId = 20L;
		brepo.findById(serachId).ifPresent(b -> {
			b.setTitle("화요일 ");
			b.setContent("오늘 점심 멘는");
			b.setWriter("정진");
			BoardVO update_board = brepo.save(b);
			log.info("원본 : " + b);
			log.info("수정 : " + update_board);
		});
	}
	
	//@Test
	void f6() {
		log.info("건수 + " + brepo.count());
	}
	
	//@Test
	void f5() {
		Long searchId = 19L;
		// 객체를 지우기 
		brepo.findById(searchId).ifPresent(b -> {
			brepo.delete(b);
		});
		// id로 지우기 
		brepo.deleteById(18L);
	}
	
	//@Test
	void f4() {
		Long serachId = 20L;
		brepo.findById(serachId).ifPresent(b -> {
			b.setTitle("월요일");
			b.setContent("오늘 점심 메뉴는?");
			brepo.save(b);
		});
	}
	
	//@Test
	void f3() {
		Long serachId = 20L;
		brepo.findById(serachId).ifPresentOrElse(b -> {
			log.info("조회한 정보 : " + b);
		}, () -> {log.info("존재하지 않음");});
	}
	
	//@Test
	void f2() {
		brepo.findAll().forEach(i -> {
			log.info(i.toString());
		});
	}
	
	//@Test
	void f1() {
		IntStream.rangeClosed(1, 20).forEach(i->{
			brepo.save(BoardVO.builder()
					.title("스프링 부트 " + i)
					.content("신기하다 " + i)
					.writer("정진 " + i%5)
					.build());
		});
	}
	
	//@Test
	void contextLoads() {
	}

}
