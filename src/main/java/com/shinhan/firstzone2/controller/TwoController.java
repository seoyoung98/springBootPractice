package com.shinhan.firstzone2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TwoController {
	
	@GetMapping("/shinhan")
	public String hello() {
		return "hello~~";
	}

}
