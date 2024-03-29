package com.shinhan.sbproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sbproject.repository.composite.MultiKeyAUsingRepository;
import com.shinhan.sbproject.repository.composite.MultiKeyBRepository;
import com.shinhan.sbproject.vo4.MultiKeyAUsing;
import com.shinhan.sbproject.vo4.MultiKeyB;
import com.shinhan.sbproject.vo4.MultiKeyBDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MultiKeyTest {
	@Autowired
	MultiKeyAUsingRepository repo1;
	
	@Autowired
	MultiKeyBRepository repo2;
	
	@Test
	void f2() {
		MultiKeyBDTO multi = MultiKeyBDTO.builder()
											.id(MultiKeyB.builder()
                                                    .id1(300)
                                                    .id2(500)
                                                    .build())
											.userName("영")
											.address("파주")
											.build();
							repo2.save(multi);
	}
	
	//@Test
	void f1() {
		MultiKeyAUsing multi = MultiKeyAUsing.builder()
												.id1(100)
												.id2(200)
												.userName("홍길동")
												.address("홍대입구")
												.build();
		repo1.save(multi);
	}
}
