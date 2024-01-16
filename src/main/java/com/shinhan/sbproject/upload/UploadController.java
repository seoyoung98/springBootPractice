package com.shinhan.sbproject.upload;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.shinhan.sbproject.repository.MemberRepository;
import com.shinhan.sbproject.repository.ProfileRepository;
import com.shinhan.sbproject.vo.MemberDTO;
import com.shinhan.sbproject.vo.ProfileDTO;

import jakarta.servlet.http.HttpServletRequest;


@Controller
public class UploadController {
    //application.properties에 설정된 경로 가져옴 
	@Value("${spring.servlet.multipart.location}")
	String uploadPath ;
	
	@Autowired
	MemberRepository memRepo;
	
	@Autowired
	ProfileRepository profileRepo;

	@GetMapping("/shop/uploadTest")
	public void upload() {
		
	}
	
	@PostMapping(value = "/shop/register")
	public String register(@RequestParam("mid") String mid, @RequestParam(value="fileList", required=false) MultipartFile[] fileList,
			HttpServletRequest req) throws IOException, Exception {
		String imgUploadPath = uploadPath + File.separator + "upload";		
		String ymdPath = UploadFileUtils.calcPath(imgUploadPath);//폴더생성, temp는 있는데 그 안의 upload 폴더는 없으므로 만들어준다.
		String fileName= null;
		for(MultipartFile f:fileList) {
			String originFileName = f.getOriginalFilename();
			System.out.println("originFileName:" + originFileName);
			if(originFileName!=null && !originFileName.equals("")) {
				fileName = UploadFileUtils.fileUpload(imgUploadPath, originFileName,
						f.getBytes(), ymdPath);			
			}else {
				fileName = File.separator + "images" + File.separator + "hide.png";
			}
			System.out.println(fileName); //DB저장
			MemberDTO member = memRepo.findById(mid).orElse(null);
			ProfileDTO profile = ProfileDTO.builder()
											.fname(fileName)
											.currentYn(true)
											.member(member)
											.build();
			profileRepo.save(profile);
		}
		return "redirect:uploadTest";
	}
}


