package com.shinhan.sbproject;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sbproject.repository.composite.EnumTypeRepository;
import com.shinhan.sbproject.repository.composite.MultiKeyAUsingRepository;
import com.shinhan.sbproject.repository.composite.MultiKeyBRepository;
import com.shinhan.sbproject.vo.MemberRole;
import com.shinhan.sbproject.vo4.EnumTypeVO;
import com.shinhan.sbproject.vo4.MultiKeyAUsing;
import com.shinhan.sbproject.vo4.MultiKeyB;
import com.shinhan.sbproject.vo4.MultiKeyBDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class CollectionTest {
	@Autowired
	EnumTypeRepository repo;
	
	@Test
	void f1() {
		Set<MemberRole> role = new HashSet<>();
		role.add(MemberRole.USER);
//		role.add(MemberRole.MANAGER);
		role.add(MemberRole.ADMIN);
		
		EnumTypeVO vo = EnumTypeVO.builder()
									.mid("환")
									.mname("민성환")
									.mpassword("1234")
									.mrole(role)
									.build();
		repo.save(vo);
	}
}
