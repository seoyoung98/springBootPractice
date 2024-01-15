package com.shinhan.sbproject.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.querydsl.core.types.Predicate;
import com.shinhan.sbproject.repository2.WebBoardRepository;
import com.shinhan.sbproject.repository2.WebReplyRepository;
import com.shinhan.sbproject.vo6.PageMaker;
import com.shinhan.sbproject.vo6.PageVO;
import com.shinhan.sbproject.vo6.WebBoard;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/webboard")
public class WebBoardController {
	final WebBoardRepository boardRepo;
	final WebReplyRepository replyRepo;
	
	@GetMapping("/list.do")
	public void f1(Model model, RedirectAttributes attr,@ModelAttribute("pageVO") PageVO page) {
//		page.setSize(20);
		Predicate predicate = boardRepo.makePredicate(page.getType(), page.getKeyword());
//		if(page==null) 
		Pageable paging = page.makePaging(page.getPage()-1, "bno");
		Page<WebBoard> result = boardRepo.findAll(predicate, paging);
		
		PageMaker<WebBoard> pageMaker = new PageMaker<>(result,5,page.getSize());
		attr.addFlashAttribute("message", null);
		model.addAttribute("blist", pageMaker);
		// paging, predicate, sort 추가함
	}
	
	@GetMapping("/detail.do")
	public void f2(Long bno,Model model,@ModelAttribute("pageVO") PageVO page) {
		model.addAttribute("board", boardRepo.findById(bno).orElse(null));
	}
	
	@PostMapping("/update.do")
	public String f3(WebBoard board, RedirectAttributes attr,@ModelAttribute("pageVO") PageVO page) {
		boardRepo.save(board);
		attr.addFlashAttribute("message","수정 성공");
//		attr.addFlashAttribute("message", page.getPage());
//		attr.addFlashAttribute("message", page.getSize());
//		attr.addFlashAttribute("message", page.getType());
//		attr.addFlashAttribute("message", page.getKeyword());
		
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
