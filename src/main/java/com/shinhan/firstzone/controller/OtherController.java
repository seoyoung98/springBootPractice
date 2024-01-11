package com.shinhan.firstzone.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
////import com.shinhan.firstzone.repository.BoardRepository;
//import com.shinhan.sbproject.vo.BoardVO;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtherController {
//	@Autowired
//	BoardRepository brepo;
//
//	@GetMapping("/sample4")
//	public List<BoardVO> f4() {
//		return (List<BoardVO>) brepo.findAll();
//	}
	
	@GetMapping("/other")
	public String other() {
		return "other";
	}
}
