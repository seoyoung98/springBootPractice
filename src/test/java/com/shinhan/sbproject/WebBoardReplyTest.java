package com.shinhan.sbproject;

import java.util.*;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sbproject.repository2.WebBoardRepository;
import com.shinhan.sbproject.repository2.WebReplyRepository;
import com.shinhan.sbproject.vo6.WebBoard;
import com.shinhan.sbproject.vo6.WebReply;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class WebBoardReplyTest {
	@Autowired
	WebBoardRepository boardRepo;
	
	@Autowired
	WebReplyRepository replyRepo;
	
	@Test
	void f4() {
		List<Long> alist = Arrays.asList(11L,12L,13L);
		boardRepo.findAllById(alist).forEach(board -> {
			List<WebReply> replies = new ArrayList<>();
			IntStream.rangeClosed(1,3).forEach(j -> {
				WebReply reply = WebReply.builder()
											.replyText("호호" + j)
											.replyer("하하하하" + board.getBno())
											.board(board)
											.build();
				replyRepo.save(reply);
			});
		});
	}
	
	// 기존 board에 댓글 insert
	@Test
	void f3() {
	 	List<Long> alist = Arrays.asList(5L,6L,7L);
		boardRepo.findAllById(alist).forEach(board -> {
			List<WebReply> replies = new ArrayList<>();
			IntStream.rangeClosed(1,3).forEach(j -> {
				WebReply reply = WebReply.builder()
											.replyText("하하" + j)
											.replyer("친구" + board.getBno())
											.board(board)
											.build();
				replies.add(reply);
			});
			board.setReplies(replies);
			boardRepo.save(board);
		});
	}
	
	@Test
	void f1() {
		// board 100개 insert
		IntStream.range(1, 101).forEach(i -> {
			WebBoard board = WebBoard.builder()
										.title("제목" + i)
										.writer("작성자" + i%10)
										.content("내용" + i)
										.build();
			if(i==1||i==10||i==20) {
				List<WebReply> replies = new ArrayList<>();
				IntStream.rangeClosed(1,5).forEach(j -> {
					WebReply reply = WebReply.builder()
												.replyText("댓글" + i + "---" + j)
												.replyer("댓글 작성자" + j)
												.board(board)
												.build();
					replies.add(reply);
				});
				
				board.setReplies(replies);
			}
			boardRepo.save(board);
		});
		
		// reply 1, 10, 20 board 댓글 5개 insert
		
	}
}
