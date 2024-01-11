package com.shinhan.sbproject.repository.composite;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.sbproject.vo4.MultiKeyA;
import com.shinhan.sbproject.vo4.MultiKeyAUsing;

// 복합키의 pk 타입은 묶여져 있는 class 로 써주기
public interface MultiKeyAUsingRepository extends CrudRepository<MultiKeyAUsing, MultiKeyA>{
	
}
