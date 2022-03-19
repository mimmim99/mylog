package com.smstudy.mylog.api;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.smstudy.mylog.common.dto.ResponseDto;
import com.smstudy.mylog.common.dto.ServiceResultDto;
import com.smstudy.mylog.member.model.MemberInput;
import com.smstudy.mylog.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MemberApiConroller {

	private final MemberService memberService;
	private final AuthenticationManager authenticationManager;
	
	@PostMapping("/api/member/join")
	public ResponseDto<?> join(@RequestBody MemberInput parameter) {
		
		ServiceResultDto result = memberService.join(parameter);
		
		if(!result.isResult()) {
			return new ResponseDto<String>(HttpStatus.FORBIDDEN.value(), result.getMessage());
		}
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PostMapping("/api/member/dupl")
	public ResponseDto<?> checkDupl(@RequestBody MemberInput parameter) {
		
		ServiceResultDto result = memberService.checkDuplMember(parameter);
		// true : 중복
		if(result.isResult()) {
			return new ResponseDto<String>(HttpStatus.CONFLICT.value(), result.getMessage()); 
		}
		//false : 중복 아님
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/api/member/update")
	public ResponseDto<?> update(@RequestBody MemberInput parameter) {
		//비밀번호 입력 안할 경우 현재 비밀번호로 세팅
		if(parameter.getPassword() == null || parameter.getPassword().isEmpty()) {
			parameter.setPassword(parameter.getCurrPassword());
		}
		
		ServiceResultDto result = memberService.updateMember(parameter);
		
		if(!result.isResult()) {
			return new ResponseDto<String>(HttpStatus.FORBIDDEN.value(), result.getMessage());
		}
		//수정된 정보로 토큰 재발급
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(parameter.getUsername(),  parameter.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
