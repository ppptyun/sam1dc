package com.samil.stdadt.util;
import java.io.File;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

public class FileUploader {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(FileUploader.class);

	public static File uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception {

		//겹쳐지지 않는 파일명을 위한 유니크한 값 생성
		UUID uid = UUID.randomUUID();
		
		//원본파일 이름과 UUID 결합
		String savedName = uid.toString() + "_" + originalName;
		
		// directory가 없을 경우 생성
		makeDir(uploadPath);
		
		//저장할 파일준비
		File target = new File(uploadPath, savedName);
		
		//파일을 저장
		FileCopyUtils.copy(fileData, target);
		
		return target;
	}//
	

	
	//폴더 생성 함수
	private static void makeDir(String uploadPath) throws Exception  {
		
		if(new File(uploadPath).exists()) {
			return;
		}//if
		
		File dirPath = new File(uploadPath);
		dirPath.mkdir();
		
	}//makeDir
	
}
