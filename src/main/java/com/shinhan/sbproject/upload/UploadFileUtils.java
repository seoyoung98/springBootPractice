package com.shinhan.sbproject.upload;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;
import org.springframework.util.FileCopyUtils;
import net.coobird.thumbnailator.Thumbnails;
public class UploadFileUtils {
	// 썸네일 이미지 크기
	static final int THUMB_WIDTH = 300;
	static final int THUMB_HEIGHT = 300;

	// 파일 업로드
	public static String fileUpload(String uploadPath, 
			String fileName, byte[] fileData, String ymdPath)
			throws Exception {
		UUID uid = UUID.randomUUID();
		String newFileName = uid + "_" + fileName;
		String imgPath = uploadPath + ymdPath;
		File target = new File(imgPath, newFileName);
		FileCopyUtils.copy(fileData, target); // 실제 업로드 코드는 딱 한줄! 
		String thumbFileName = "s_" + newFileName;
		File image = new File(imgPath + File.separator + newFileName);
		File thumbnail = new File(imgPath + File.separator + "s" + File.separator + thumbFileName); // 썸네일 만들기 
		if (image.exists()) {
			thumbnail.getParentFile().mkdirs();
			Thumbnails.of(image).size(THUMB_WIDTH, THUMB_HEIGHT).toFile(thumbnail);
		}
		return newFileName;
	}

	// 폴더이름 및 폴더 생성
	public static String calcPath(String uploadPath) {
		// File.separator => /
		Calendar cal = Calendar.getInstance();
		String yearPath = File.separator + cal.get(Calendar.YEAR);
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		
		int pos = uploadPath.lastIndexOf(File.separator); // cc/bb/aa.png -> 마지막 슬래시를 찾음
		String folder = uploadPath.substring(0, pos); // 마지막 슬래시 전까지 내용으로
		makeDir(folder, uploadPath.substring(pos)); // 폴더를 만든다.
		System.out.println(folder + ":" + uploadPath.substring(pos)); 
		makeDir(uploadPath, yearPath, monthPath, datePath);
		makeDir(uploadPath, yearPath, monthPath, datePath + "\\s");
		return datePath;
	}

	// 폴더 생성
	private static void makeDir(String uploadPath, String... paths) {
		if (new File(paths[paths.length - 1]).exists())
			return;
		for (String path : paths) {
			File dirPath = new File(uploadPath + path);
			if (!dirPath.exists())
				dirPath.mkdir(); // 디렉토리 만들기
		}
	}
}
