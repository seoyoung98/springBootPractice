package com.shinhan.sbproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.shinhan.sbproject.repository2.DeptRepository5;
import com.shinhan.sbproject.repository2.EmpRepository5;

@Controller
public class DeptEmpController {
	@Autowired
	EmpRepository5 empRepo;
	
	@Autowired
	DeptRepository5 deptRepo;
	
	@GetMapping("/deptemp")
	public void f1(Model model){
		model.addAttribute("dlist",deptRepo.findAll());
		model.addAttribute("elist",empRepo.findAll());
	}
	
//	@GetMapping("/emp")
//	public List<EmpVO> f2(){
//		return (List<EmpVO>)empRepo.findAll();
//	}
}
