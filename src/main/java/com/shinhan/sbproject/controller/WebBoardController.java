package com.shinhan.sbproject.controller;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.shinhan.sbproject.security.MemberService;
import com.shinhan.sbproject.vo.MemberDTO;
import com.shinhan.sbproject.vo6.PageMaker;
import com.shinhan.sbproject.vo6.PageVO;
import com.shinhan.sbproject.vo6.WebBoard;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/webboard")
@Tag(name="웹보드",description="여기에서는 webboard crud 가능")
public class WebBoardController {
	final WebBoardRepository boardRepo;
	final WebReplyRepository replyRepo;
	final MemberService memService;
	
	@GetMapping("/list.do")
	public void f1(Model model, RedirectAttributes attr,@ModelAttribute("pageVO") PageVO page, Principal principal, Authentication auth, HttpSession session) {
		// 로그인한 멤버의 정보를 알아내고 싶다.
		// principal, authentication 사용
		// 1. Principal
		log.info("방법 1 : ", principal.toString());
		// 2. Authentication 
		log.info("방법 2 : ", auth.getPrincipal());
		// 3. SecurityContextHolder 이용
		log.info("방법 3 : " , SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		
		String mid = principal.getName();
		UserDetails user = memService.loadUserByUsername(mid);
		System.out.println("로그인한 사람(security) : " + user);
//		user = (UserDetails)session.getAttribute("user");
		
		MemberDTO member = (MemberDTO)session.getAttribute("user");
		System.out.println("로그인한 사람 (우리 db) : " + user);
		model.addAttribute("user", member);
		
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
	public void f2(Long bno,Model model,@ModelAttribute("pageVO") PageVO page, HttpSession session) {
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
		
		return "redirect:detail.do?bno="+board.getBno()+"&page="+page.getPage()+"&type="+page.getType()+"&keyword="+page.getKeyword();
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
