package com.shinhan.sbproject;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sbproject.repository.CarRepository;
import com.shinhan.sbproject.vo.BoardVO;
import com.shinhan.sbproject.vo.CarVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class SbprojectApplicationTests {
	
	@Autowired
	CarRepository repository;
	
	//@Test
	void f5() {
		IntStream.rangeClosed(1, 10).forEach(i->{
			BoardVO c1 = BoardVO.builder()
					.title("책 제목 " + i)
					.content("책 내용" + i)
					.writer("작가 " + i)
					.build();
		});
	}
	
	//@Test
	void f4() {
		repository.findAll().forEach(i -> {
			log.info(i.toString());
		});
	}
	
	//@Test
	void f3() {
		IntStream.rangeClosed(1, 60).forEach(i->{
			CarVO c1 = CarVO.builder()
					.model("abc" + i)
					.price(i * 1000)
					.build();
			// 내부적으로 insert가 실행이 된다.
			repository.save(c1);
		});
	}
	
	//@Test
	void f2() {
		// car를 10번 반복하기
		IntStream.rangeClosed(1, 10).forEach(i->{
			CarVO c1 = CarVO.builder()
					.carNum(i * 100L)
					.model("abc" + i)
					.price(i * 1000)
					.build();
			log.info(i + "번째" + c1);
		});
	}

	//@Test
	void f1() {
		CarVO c1 = CarVO.builder()
						.carNum(100L)
						.model("abc")
						.price(1000)
						.build();
		
		log.info(c1.toString());
	}
	
	//@Test
	void contextLoads() {
	}

}
