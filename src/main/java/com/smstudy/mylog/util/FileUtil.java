package com.smstudy.mylog.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

	private static final String baseFilePath = "C:/dev/eclipse-workspace-private/mylog/files";
	private static final String baseUrlPath = "/files";
	
	/**
	 * 디렉토리 체크(없으면 생성)
	 */
	public static String checkDirectory() {
		LocalDateTime today = LocalDateTime.now();
		String dir = String.format("%s/%d/%02d/%02d/", baseFilePath, today.getYear(), today.getMonthValue(),today.getDayOfMonth());
		File fileDir = new File(dir);
		if(!fileDir.isDirectory()) {
			boolean result = fileDir.mkdirs();
			if(!result) {
				System.out.println("FileUtil 디렉토리 실패");
			}
		}
		
		return dir;
	}
	
	/**
	 * 파일명 생성 
	 */
	public static String makeFilename(String origin) {
		return UUID.randomUUID() + origin.substring(origin.lastIndexOf("."));
	}
	
	/**
	 * 파일 업로드 후 url 경로 반환
	 */
	public static String save(MultipartFile file) {
		if(file == null || file.isEmpty()) {
			System.out.println("FileUtil 파일 저장 실패 - 파일 데이터가 없습니다.");
		}
		
		//디렉토리 생성
		String filepath = checkDirectory();
		//파일명 생성
		String filename = makeFilename(file.getOriginalFilename());
		//파일 업로드
		File targetFile = new File(filepath + filename);
		try {
			FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(targetFile));
		} catch (IOException e) {
			//log.debug("file exception");
			//log.debug(e.getMessage());
		}
		
		return filepath.replace(baseFilePath, baseUrlPath) + filename;
	}
	
	/**
	 * 파일 삭제
	 */
	public static void delete(String fileurlpath) {
		File file = new File(baseFilePath.replace("/files", "") + fileurlpath);
		if(file.isFile()) {
			try {			
				FileUtils.forceDelete(file);
				System.out.println("파일삭제");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("게시글 등록 이미지 파일 삭제 실패");
			}
		}
	}
}
