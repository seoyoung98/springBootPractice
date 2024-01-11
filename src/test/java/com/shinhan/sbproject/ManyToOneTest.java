package com.shinhan.sbproject;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.querydsl.core.BooleanBuilder;
import com.shinhan.sbproject.repository.MemberRepository;
import com.shinhan.sbproject.repository.ProfileRepository;
import com.shinhan.sbproject.vo.MemberDTO;
import com.shinhan.sbproject.vo.MemberRole;
import com.shinhan.sbproject.vo.ProfileDTO;
import com.shinhan.sbproject.vo.QProfileDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ManyToOneTest {
	@Autowired
	MemberRepository memRepo;
	
	@Autowired
	ProfileRepository profileRepo;
	
	@Test
	void jpqlTest() {
		String mid = "user1";
		int count = memRepo.getProfileCountByMember(mid);
		log.info(mid + "의 프로필 건수 : " + count);
		
		List<Object[]> result = memRepo.getProfileCountByMember();
		result.forEach(data -> log.info("멤버의 프로필 건수 : " + data[0] + "-----" + data[1]));
		
		result = memRepo.getProfileCountByMember2();
		result.forEach(data -> log.info("!!!멤버의 프로필 건수 : " + data[0] + "-----" + data[1]));
	}
	
	//@Test
	void selectByMember(){
	    MemberDTO member = MemberDTO.builder().mid("user1").build();
	    List<ProfileDTO> plist = profileRepo.findByMember(member);
	    plist.forEach(p->log.info(p.toString()));
	    plist = profileRepo.findByCurrentYnIsTrue();
	    plist.forEach(p->log.info(p.toString()));
	}
	//@Test
	void f1(){
	    profileRepo.findAll().forEach(p -> log.info(p.toString()));
	}
	
	// 1. cuurentYn == true인 프로필 조회
	// 2. member와 profile 조인 문장(nativeQuery)작성
	//@Test
	void 동적() {
		BooleanBuilder builder = new BooleanBuilder();
		QProfileDTO profile = QProfileDTO.profileDTO;
		builder.and(profile.pno.gt(52L));
		builder.and(profile.fname.like("%필%"));
		builder.and(profile.currentYn.eq(false));
		profileRepo.findAll(builder).forEach(i -> {
			log.info(i.toString());
		});
	}
	
	//@Test
	void join() {
		profileRepo.selectByJoin().forEach(i -> {
			log.info(Arrays.toString(i));
		});
	}
	
	// 단방향 처리 : profile을 통해서 member 가져오기, profile이 member를 참조하므로 가능 
	// memberId를 알고 Profile 가져오기 가능? ManyToOne은 불가능하다. 
	// pno를 가지고 Member 가져오기 가능? 가능
 	//@Test
	void memberProfile() {
		Long pno = 5L;
		profileRepo.findById(pno).ifPresent(pro -> {
			log.info("Fname" + pro.getFname());
			log.info("Mname" + pro.getMember().getMname());
		});
		log.info("------------------------");
		MemberDTO member = new MemberDTO();
		member.setMid("admin10");
		profileRepo.findByMember(member).forEach(pro -> {
			log.info("Fname" + pro.getFname());
			log.info("Mname" + pro.getMember().getMname());
		});
	}
	
	//@Test
	void profileSelect() {
		profileRepo.findAll().forEach(pro -> log.info(pro.toString()));
		log.info("건수 " + profileRepo.count());
	}
	
	//@Test
	void profileInsert() {
//		MemberDTO memberDTO = memRepo.findById("user1").orElse(null);
//		MemberDTO memberDTO = memRepo.findById("manager7").orElse(null);
		MemberDTO memberDTO = memRepo.findById("admin10").orElse(null);
		IntStream.rangeClosed(1, 5).forEach(i -> {
			ProfileDTO profile = ProfileDTO.builder()
											.fname("프로필" + i)
											.currentYn(i < 5 ? false:true)
											.member(memberDTO)
											.build();
			profileRepo.save(profile);
		});
	}
	
	//@Test
	void memberSelect() {
		memRepo.findAll().forEach(m -> {
			log.info(m.toString());
		});
	}
	
	//@Test
	void memberInsert() {
		// 10명의 member 입력하기 1~5 : user 권한 6~8 : manager 권한 9~10 : admin 권한
		IntStream.rangeClosed(1, 10).forEach(i -> {
			MemberDTO member = new MemberDTO();
			UUID uuid = UUID.randomUUID();
			member.setMpassword(uuid.toString().split("-")[0]);
			if(i <= 5) {
				member.setMid("user"+i);
				member.setMname("유저" + i);
				member.setMrole(MemberRole.USER);
			} else if(i >= 6 && i <= 8) {
				member.setMid("manager"+i);
				member.setMname("유저" + i);
				member.setMrole(MemberRole.MANAGER);
			} else {
				member.setMid("admin"+i);
				member.setMname("유저" + i);
				member.setMrole(MemberRole.ADMIN);
			}
			memRepo.save(member);
		});
		
	}
}
