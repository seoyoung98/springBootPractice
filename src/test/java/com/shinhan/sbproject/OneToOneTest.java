package com.shinhan.sbproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sbproject.repository.composite.UserCellPhone2Repository;
import com.shinhan.sbproject.repository.composite.UserCellPhoneRepository;
import com.shinhan.sbproject.repository.composite.UserVORepository;
import com.shinhan.sbproject.vo4.UserCellPhoneVO;
import com.shinhan.sbproject.vo4.UserCellPhoneVO2;
import com.shinhan.sbproject.vo4.UserVO;
import com.shinhan.sbproject.vo4.UserVO2;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class OneToOneTest {
	@Autowired
	UserCellPhoneRepository pRepo;
	
	@Autowired
	UserCellPhone2Repository p2Repo;
	
	@Autowired
	UserVORepository uRepo;
	
	//@Test
	void f4() {
		p2Repo.findAll().forEach(p -> {
			log.info(p.toString());
		});
	}
	
	// user를 먼저 저장하고 user를 phone에 넣기 
	//@Test
	void f3() {
		UserVO2 user = UserVO2.builder()
							.userid("ssy")
							.username("ssy")
							.build();
		UserCellPhoneVO2 phone = UserCellPhoneVO2.builder()
				.phoneNumber("010-1234-1234")
				.model("apple")
				.user(user)
				.build();
		p2Repo.save(phone);
	}
	
	//@Test
	void f2() {
		uRepo.findById("ssy").ifPresent(u->log.info(u.toString()));
	}
	
	// phone 정보를 먼저 저장하고 user에 phone 넣기
	@Test
	void f1() {
		UserCellPhoneVO phone = UserCellPhoneVO.builder()
												.phoneNumber("010-1231-2312")
												.model("samsung")
												.build();
//		UserCellPhoneVO new_phone = pRepo.save(phone);
		UserVO user = UserVO.builder()
							.userid("yys")
							.username("yys")
//							.phone(new_phone)
							.phone(phone)
							.build();
		uRepo.save(user);
	}
	
	
}
