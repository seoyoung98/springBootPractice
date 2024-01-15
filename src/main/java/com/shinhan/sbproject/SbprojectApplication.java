package com.shinhan.sbproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.shinhan.sbproject","com.shinhan.firstzone", "com.shinhan.firstzone2"})
@SpringBootApplication
// EnableJpaRepositories 스프링 부트에서는 자동으로 설정되어 따로 써줄 필요가 없다.
//@EnableJpaRepositories(basePackages = ("com.shinhan.firstzone2"))
public class SbprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbprojectApplication.class, args);
	}

}
