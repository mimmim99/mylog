package com.smstudy.mylog.member.service;

import org.springframework.transaction.annotation.Transactional;

import com.smstudy.mylog.common.dto.ServiceResultDto;
import com.smstudy.mylog.member.dto.MemberDto;
import com.smstudy.mylog.member.model.MemberInput;

public interface MemberService {

	/**
	 * 회원가입
	 */
	@Transactional
	public ServiceResultDto join(MemberInput parameter);

	/**
	 * 중복회원 검사
	 */
	@Transactional
	public ServiceResultDto checkDuplMember(MemberInput parameter);
	
	/**
	 * 회원정보 수정 
	 */
	@Transactional
	public ServiceResultDto updateMember(MemberInput parameter);
}
