package com.shinhan.firstzone.repository;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.sbproject.vo.BoardVO;

public interface BoardRepository extends CrudRepository<BoardVO, Long>{
	
}
