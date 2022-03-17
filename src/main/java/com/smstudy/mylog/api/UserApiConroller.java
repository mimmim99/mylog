package com.smstudy.mylog.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smstudy.mylog.common.dto.ResultDto;
import com.smstudy.mylog.user.model.UserInput;
import com.smstudy.mylog.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiConroller {

	private final UserService userService;
	
	@PostMapping("/api/user/join")
	public ResponseEntity<?> join(@RequestBody UserInput parameter) {
		
		ResultDto result = userService.join(parameter);
		
		return ResponseEntity.ok().body(result);
	}
}
