package com.shinhan.sbproject;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sbproject.repository.DeptRepository;
import com.shinhan.sbproject.vo.DepartmentVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class DeptTest {
	@Autowired
	DeptRepository deptRepo;
	
	@Test
	void f6(){
		deptRepo.findByManagerId(7).forEach(i -> {
			String dname = i.getDepartmentName();
			i.setDepartmentName(dname);
			deptRepo.save(i);
		});
	}
	
	// 삭제
	//@Test
	void f5() {
		deptRepo.deleteById(10);
	}
	
	// 수정
	//@Test
	void f4() {
		int searchId = 110;
		deptRepo.findById(searchId).ifPresent(i -> {
			i.setDepartmentName("인사부");
			i.setManagerId(100);
			deptRepo.save(i);
		});
	}
	
	// 모두 조회
	//@Test
	void f3() {
		deptRepo.findAll().forEach(i -> {
			log.info(i.toString());
		});
	}
	
	
	// 1건 조회
	//@Test
	void f2() {
//		int searchId = 110;
//		deptRepo.findById(searchId).ifPresentOrElse(i->{
//			log.info("있음");
//		}, ()->{log.info("없음");});
		
		DepartmentVO dept = deptRepo.findById(100).orElse(null);
		if (dept != null) {
		    log.info("here" + dept.toString());
		} else {
		    log.info("Department with ID 100 not found");
		}
		
		DepartmentVO dept2 = deptRepo.findById(9).get();
		log.info(dept2.toString());
	}
	
	// 입력
	//@Test
	void f1() {
		IntStream.rangeClosed(1, 20).forEach(i -> {
			deptRepo.save(DepartmentVO.builder()
											.departmentName("부서 이름" + i)
											.managerId(i)
											.locationId(i)
											.build());
		});
		for(int i = 100; i <= 200; i += 10){
			DepartmentVO dept = DepartmentVO.builder()
											.departmentName("개발부" + i)
											.managerId(i % 20)
											.locationId(i)
											.build();
			deptRepo.save(dept);
		}
	}
	
	//@Test
	void contextLoads() {
	}

}
