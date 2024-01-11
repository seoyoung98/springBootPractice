package com.shinhan.sbproject;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Commit;

import com.shinhan.sbproject.repository.PDSBoardRepository;
import com.shinhan.sbproject.repository.PDSFileRepository;
import com.shinhan.sbproject.vo2.PDSBoard;
import com.shinhan.sbproject.vo2.PDSFile;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Commit
@Slf4j
@SpringBootTest
public class OneToManyTest {
	@Autowired
	PDSBoardRepository brepo;
	
	@Autowired
	PDSFileRepository frepo;
	
	@Test
	void findSelectAll() {
		frepo.findAll(Sort.by(Direction.DESC,"fno")).forEach(f->log.info(f.toString()));
		Pageable paging = PageRequest.of(0, 3);
		frepo.findAll(paging).forEach(i -> {
			log.info(i.toString());
		});
		paging = PageRequest.of(1, 3);
		frepo.findAll(paging).forEach(i -> {
			log.info(i.toString());
		});
		
		Page<PDSFile> result = frepo.findAll(paging);
		int cnt = result.getTotalPages();
		for(int i=0; i<cnt; i++) {
			paging = PageRequest.of(i, 3);
			frepo.findAll(paging).forEach(f->log.info(f.toString()));
			log.info("===========");
		}
				
	}
	
	@Test
	void deleteBoard() {
		brepo.deleteById(1L);
	}
	
	//@Test
	void getFileByBoard() {
		brepo.findById(1L).ifPresent(b->{
			for(PDSFile file : b.getFiles2()) {
				log.info(file.toString());
			}
			
		});
	}
	
	//@Test
	void searchFile() {
		List<PDSBoard> blist = (List<PDSBoard>) brepo.findAll();
		for(PDSBoard board : blist) {
			List<PDSFile> flist = board.getFiles2();
			flist.forEach(f->{
				if(f.getFno() == 3L) {
					f.setPdsfilename("찾기 어려웠음.jpg");
//					brepo.save(board);
					frepo.save(f);
				}
			});
		}
	}
	
	
	// board를 통해서 file에 있는 첨부 파일 이름 수정하기 
	// dml을 쓰기 위해서는 transactional이 필요하다.
	@Transactional // 이 클래스가 test이기 때문에 db에 반영되지는 않는다. 즉, rollback이 된다.
	// 반영을 하기 위해서는 class level 에 @commit을 넣어주어야 한다.
	@Test
	void updateFile2() {
		int result = brepo.updatePDSFile(2L, "이미지성형하기.jpg");
		log.info("결과 : " + result);
	}
	
	// file을 통해서 file에 있는 첨부 파일 수정하기 
	//@Test
	void updateFile() {
		PDSFile file = frepo.findById(1L).orElse(null);
		if(file == null) return;
		file.setPdsfilename("파일이름수정");
		frepo.save(file);
	}
	
	// 3번 
	//@Test
	void getFilesCount() {
		frepo.getFilesCount().forEach(arr -> {
			log.info(Arrays.toString(arr));
		});
	}
	
	// board별 file의 count 얻기 
	// fileCount 의 방법이 더 좋다
	// 2번
	// @Test
	void fileCount2() {
		frepo.getFileCountByBoard().forEach(arr->{
			log.info(Arrays.toString(arr));
		});
	}
	
	// board별 file의 count 얻기 
	// 1번
	//@Test
	void fileCount() {
		brepo.findAll().forEach(b->{
			log.info(b.getPname() + "-----" + b.getFiles2().size());
		});
	}

	//@Test
	void fileUpdate2() {
		Long fno = 102L;
		Long pid = 2L;
		PDSBoard board = brepo.findById(pid).orElse(null);
		if(board == null) return;
		List<PDSFile> flist = board.getFiles2();
		frepo.findByPdsfilenameContaining("eye").forEach(f -> {
			flist.add(f);
		});
		brepo.save(board);
	}
	
	// board를 통해서 file을 저장했다면 pdsfile 테이블의 pdsno가 pid로 들어간다.
	// file만 저장했다면 pdsno가 null이다.
	//@Test
	void fileUpdate() {
		Long fno = 102L;
		Long pid = 2L;
		frepo.findById(fno).ifPresent(f -> {
			brepo.findById(pid).ifPresent(b -> {
				b.getFiles2().add(f);
				brepo.save(b);
			});
		});
	}
	
	//@Test
	void fileSave() {
		IntStream.rangeClosed(1, 10).forEach(i -> {
			PDSFile file = PDSFile.builder()
									.pdsfilename("eye-" + i)
									.build();
			frepo.save(file);
		});
	}
	
	//@Test
	void selectByBoard() {
		Long pid = 2L;
		brepo.findById(pid).ifPresent(board -> {
			log.info("pname:" + board.getPname());
			log.info("pwriter:" + board.getPwriter());
			log.info("files2:" + board.getFiles2());
			log.info("files2건수:" + board.getFiles2().size());
			log.info("**********");
		});
	}
	
	//@Test
	void fileSelect() {
		frepo.findAll().forEach(b->log.info(b.toString()));
	}
	
	//@Test
	void boardSelect() {
		brepo.findAll().forEach(b->log.info(b.toString()));
	}
	
	//@Test
	void insert() {
		// Board(1), file(n)
		List<PDSFile> flist = new ArrayList<>();
		IntStream.rangeClosed(1, 5).forEach(i -> {
			PDSFile file = PDSFile.builder().pdsfilename("face-" + i).build();
			flist.add(file);
		});
		
		PDSBoard board = PDSBoard.builder().pname("수요일").pwriter("문경").files2(flist).build();
		brepo.save(board);
	}
}
