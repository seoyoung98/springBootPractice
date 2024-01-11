package com.shinhan.sbproject.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.sbproject.vo.DepartmentVO;

// 1. 기본 crud => CrudRepository 상속 : findAll, findById, save, count, delete
public interface DeptRepository extends CrudRepository<DepartmentVO, Integer>{

	// 2. 규칙에 맞는 함수 정의 
	// 특정 managerId가 관리하는 부서들의 부서 이름 뒤에 "ok"라는 문자를 추가(수정) 
	List<DepartmentVO> findByManagerId(int managerId);
}
