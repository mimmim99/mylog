package com.smstudy.mylog.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smstudy.mylog.common.dto.ResponseDto;
import com.smstudy.mylog.util.FileUtil;

@RestController
public class FileApiController {

	@PostMapping("/api/board/upload")
	public ResponseDto<?> fileUpload(@RequestParam(value="file", required=false) MultipartFile file) {
		
		if(file == null || file.isEmpty()) {
			return new ResponseDto<String>(HttpStatus.BAD_REQUEST.value(), "파일이 정상적으로 전달되지 않았습니다.");
		}
		
		String fileUrl = FileUtil.save(file);
		
		return new ResponseDto<String>(HttpStatus.OK.value(), fileUrl);
	}
}
