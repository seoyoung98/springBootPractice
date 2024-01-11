package com.shinhan.sbproject;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.shinhan.sbproject.repository.FreeBoardReplyRepository;
import com.shinhan.sbproject.repository.FreeBoardRepository;
import com.shinhan.sbproject.vo3.FreeBoard;
import com.shinhan.sbproject.vo3.FreeBoardReply;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class TwoWayTest {
	@Autowired
	FreeBoardRepository boardRepo;
	
	@Autowired
	FreeBoardReplyRepository replyRepo;
	
	// writer 가 쓴 글 찾아오기
	@Test
	void selectBoard3() {
		Pageable paging = PageRequest.of(1, 3, Sort.by(Direction.ASC, "bno"));
		Page<FreeBoard> result = boardRepo.findByWriter("user1", paging);

		log.info("페이지 수 : " + result.getTotalPages());
		log.info("건수 : " + result.getTotalElements());
		
		List<FreeBoard> blist = result.getContent();
		for(FreeBoard bb : blist) {
			log.info("bno : " + bb.getBno());
		}
	}
	
	// 게시글 번호 범위에 해당하는 글 가져오기
	//@Test
	void selectBoard2() {
		Pageable paging = PageRequest.of(1, 5, Sort.Direction.ASC);
		List<FreeBoard> board = boardRepo.findByBnoBetween(1L, 20L, paging);

		for(FreeBoard bb : board) {
			log.info(bb.getContent());
		}
	}
	
	// 게시글 번호보다 큰 번호를 가진 게시글 가져오기 
	//@Test
	void selectBoard1() {
		Pageable paging = PageRequest.of(0, 3, Sort.by(Direction.ASC, "bno"));
		boardRepo.findByBnoGreaterThan(10L, paging).forEach(i -> {
			log.info("내용 : " + i.getContent());
		});;
	}
	
	//@Test
	void replySelectByBoard() {
		FreeBoard board = FreeBoard.builder()
									.bno(20L)
									.title("a")
									.build();
		List<FreeBoardReply> replyList = replyRepo.findByBoard2(board);
		replyList.forEach(reply -> {
			log.info("댓글 번호:" + reply.getRno());
			log.info("댓글:" + reply.getReply());
			log.info("댓글 작성자:" + reply.getReplyer());
			log.info("댓글 작성일:" + reply.getRegdate());
			log.info("댓글 수정일:" + reply.getUpdatedate());
			log.info("--------------------------");
		});
	}
	
	// 댓글 가져오기
	//@Test
	void replySelect() {
		replyRepo.findAll(Sort.by(Direction.DESC,"rno")).forEach(reply -> {
			log.info("댓글 내용 => " + reply.getReply());
			log.info("작성자 => " + reply.getReplyer());
			log.info("board => " + reply.getBoard2().getContent());
			log.info("---------------------");
		});
	}
	
	// 모든 board의 댓글의 개수 출력
	//@Test
	void getReplyCount() {
		boardRepo.findAll().forEach(i -> {
			log.info(i.getBno() + "-----" + i.getReplies().size());
		});
	}
	
	//@Test
	void replyInsert2() {
//		List<FreeBoard> it = (List<FreeBoard>) boardRepo.findAllById(Lists.newArrayList(5L,10L,15L));
//		log.info(it.toString());
		
		List<Long> blist = Arrays.asList(5L, 10L, 15L);
		boardRepo.findAllById(blist).forEach(board -> {
			IntStream.range(1, 6).forEach(i -> {
				FreeBoardReply reply = FreeBoardReply.builder()
														.reply("퍼스트존 ... " + board.getBno())
														.replyer("작성자" + i)
														.board2(board)
														.build();
				replyRepo.save(reply);
			});
		});
	}
	
	//@Test
	void replyInsert() {
		FreeBoard board = boardRepo.findById(20L).orElse(null);
		IntStream.range(1, 6).forEach(i -> {
			FreeBoardReply reply = FreeBoardReply.builder()
													.reply(i+""+i+i+i+i)
													.replyer("작성자" + i)
													.board2(board)
													.build();
			replyRepo.save(reply);
		});
	}
	
	//@Test
	void boardSelect() {
		boardRepo.findAll(Sort.by(Direction.DESC,"bno")).forEach(b->log.info(b.toString()));
	}
	
	//@Test
	void boardInsert() {
		// 20건의 board 작성
		IntStream.range(1, 21).forEach(i -> {
			FreeBoard board = FreeBoard.builder()
										.title("양방향 연습" + i)
										.writer("user" + i%5)
										.content("넘 어렵다.")
										.build();
			boardRepo.save(board);
		});
	}

}
