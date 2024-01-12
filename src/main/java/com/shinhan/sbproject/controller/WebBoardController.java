package com.shinhan.sbproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shinhan.sbproject.repository2.WebBoardRepository;
import com.shinhan.sbproject.repository2.WebReplyRepository;
import com.shinhan.sbproject.vo6.WebBoard;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/webboard")
public class WebBoardController {
	final WebBoardRepository boardRepo;
	final WebReplyRepository replyRepo;
	
	@GetMapping("/list.do")
	public void f1(Model model) {
		model.addAttribute("blist", boardRepo.findAll());
	}
	
	@GetMapping("/detail.do")
	public void f2(Long bno,Model model) {
		model.addAttribute("blist", boardRepo.findById(bno).orElse(null));
	}
	
	@GetMapping("/update.do")
	public String f3(WebBoard board, RedirectAttributes attr) {
		boardRepo.save(board);
		return "redirect:list.do";
	}
	
	@GetMapping("/insert.do")
	public void f4() {
	}
	
	@PostMapping("/insert.do")
	public String f5(WebBoard board, RedirectAttributes attr) {
		boardRepo.save(board);
		return "redirect:list.do";
	}
	
	@GetMapping("/delete.do")
	public String f6(Long bno, RedirectAttributes attr) {
		boardRepo.deleteById(bno);
		attr.addFlashAttribute("message","삭제 성공");
		return "redirect:list.do";
	}
	
}
