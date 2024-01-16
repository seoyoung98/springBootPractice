package com.shinhan.sbproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sbproject.repository2.WebBoardRepository;
import com.shinhan.sbproject.repository2.WebReplyRepository;
import com.shinhan.sbproject.vo6.WebBoard;
import com.shinhan.sbproject.vo6.WebReply;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/reply")
@Tag(name="댓글",description="여기에서는 webboard reply crud 가능")
public class WebBoardReplyController {
	
	@Autowired
	WebReplyRepository replyRepo;
	
	@Autowired
	WebBoardRepository boardRepo;
	
	// 조회
	@GetMapping("/list/{bno}")
	public List<WebReply> getReplies(@PathVariable("bno") Long bno) {
		WebBoard board = WebBoard.builder().bno(bno).title("aa").build();
		return replyRepo.findByBoard(board);
	}
	
	// 추가
	@PostMapping(value = "/add/{bno}", consumes = "application/json")
	public ResponseEntity<List<WebReply>> insertReplies(@PathVariable("bno") Long bno, @RequestBody WebReply reply) {
		WebBoard board = boardRepo.findById(bno).orElse(null);
		reply.setBoard(board);
		replyRepo.save(reply);
		
		// 상태값과 data를 같이 넘기기 
		return new ResponseEntity<>(replyRepo.findByBoard(board),HttpStatus.CREATED);
	}
	
	// 수정
	@PutMapping("/update/{bno}")
	public ResponseEntity<List<WebReply>> updateReplies(@PathVariable("bno") Long bno, @RequestBody WebReply reply) {
		replyRepo.findById(reply.getRno()).ifPresent(originalReply->{
			originalReply.setReplyText(reply.getReplyText());
			replyRepo.save(originalReply);
		});
		WebBoard board = WebBoard.builder().bno(bno).title("aa").build();
		return new ResponseEntity<>(replyRepo.findByBoard(board),HttpStatus.OK);
		
//		WebReply dbReply = replyRepo.findById(reply.getRno()).orElse(null);
//        if (dbReply != null) {
//            dbReply.setReply(reply);
//            replyRepo.save(dbReply);
//            return ResponseEntity.status(HttpStatus.OK)
//                                .body(replyRepo.findByBoard(WebBoard.builder()
//                                                                            .bno(bno)
//                                                                            .title("aa")
//                                                                            .build()));
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("notFound");
	}
	
	@DeleteMapping("/delete/{bno}/{rno}")
	public ResponseEntity<List<WebReply>> deleteReply(@PathVariable("bno") Long bno,@PathVariable("rno") Long rno) {
		WebReply reply = replyRepo.findById(rno).orElse(null);
//		replyRepo.deleteById(rno);
		replyRepo.delete(reply);
		WebBoard board = WebBoard.builder().bno(bno).title("aa").build();
		return new ResponseEntity<>(replyRepo.findByBoard(board),HttpStatus.OK);
	}
}
