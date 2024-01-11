package com.shinhan.sbproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shinhan.sbproject.vo2.PDSFile;

public interface PDSFileRepository extends CrudRepository<PDSFile, Long>, QuerydslPredicateExecutor<PDSFile>, PagingAndSortingRepository<PDSFile, Long> {
	
	List<PDSFile> findByPdsfilenameContaining(String fname);
	
	@Query(value = "select b.pname, count(f.fno) from tbl_pdsboard_young2 b left outer join tbl_pdsfiles_young2 f on(f.pdsno = b.pid) group by b.pname", nativeQuery = true)
	List<Object[]> getFileCountByBoard();
	
	@Query("select b.pname, count(f) from PDSBoard b left outer join b.files2 f where b.pid>0 group by b.pname order by b.pname ")
			public List<Object[]> getFilesCount();

}
