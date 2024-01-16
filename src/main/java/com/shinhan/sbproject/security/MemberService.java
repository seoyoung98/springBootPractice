package com.shinhan.sbproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shinhan.sbproject.repository.MemberRepository;
import com.shinhan.sbproject.vo.MemberDTO;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class MemberService implements UserDetailsService {
	@Autowired
	private HttpSession httpSession;

	@Autowired
	private MemberRepository mrepo;

	@Autowired
	BCryptPasswordEncoder passwordEncoder; // security config에서 Bean생성
	// 회원가입

	// transactional은 member가 ~~면 안써도 된다. 들어와있으면?
	@Transactional
	public MemberDTO joinUser(MemberDTO member) {
		// 비밀번호 암호화...암호화되지않으면 로그인되지않는다.
		String pwd = passwordEncoder.encode(member.getMpassword());
		member.setMpassword(pwd);
		System.out.println("암호화된 pass:" + pwd);
		return mrepo.save(member);
	}

	//!!!!반드시 구현해야한다. 
	// 무조건 override 해야한다.
	@Override
	//@Transactional
	// loadUserByUsername에는 parameter로 username이 들어간다. 무조건. 변수명이랑은 관계가 없다. 
	public UserDetails loadUserByUsername(String mid) throws UsernameNotFoundException {
		System.out.println("loadUserByUsername mid:" + mid);
		//filter는 조건에 맞는것만 선택
		//map: 변형한다. 
		
//		Optional<MemberDTO> optionMember = mrepo.findById(mid);
		MemberDTO loginMember = mrepo.findById(mid).orElse(null);
		
		UserDetails member = mrepo.findById(mid).filter(m -> m != null).map(m -> new SecurityUser(m)).get();
		System.out.println("UserDetails member:" + member);
		httpSession.setAttribute("user", loginMember);
		return member;
	}

}