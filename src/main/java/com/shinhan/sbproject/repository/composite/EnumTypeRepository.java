package com.shinhan.sbproject.repository.composite;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.sbproject.vo4.EnumTypeVO;

// 복합키의 pk 타입은 묶여져 있는 class 로 써주기
public interface EnumTypeRepository extends CrudRepository<EnumTypeVO, String>{
	
}
