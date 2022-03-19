package com.smstudy.mylog.member.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.smstudy.mylog.common.dto.ServiceResultDto;
import com.smstudy.mylog.member.entity.RoleType;
import com.smstudy.mylog.member.entity.Member;
import com.smstudy.mylog.member.model.MemberInput;
import com.smstudy.mylog.member.repository.MemberRepository;
import com.smstudy.mylog.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
	
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder encoder;

	@Override
	public ServiceResultDto join(MemberInput parameter) {
		
		//중복체크
		Optional<Member> optionalMember = memberRepository.findByUsername(parameter.getUsername());
		if(optionalMember.isPresent()) {
			return new ServiceResultDto(false, "이미 존재하는 회원입니다.");
		}
		
		parameter.setPassword(encoder.encode(parameter.getPassword()));
		
		Member member = Member.builder().username(parameter.getUsername())
								  .password(parameter.getPassword())
								  .email(parameter.getEmail())
								  .nickname(parameter.getNickname())
								  .accessScope(parameter.getAccessScope())
								  .authKey(UUID.randomUUID().toString())
								  .authYn(false)
								  .role(RoleType.USER)
								  .build();
		
		memberRepository.save(member);
		
		return new ServiceResultDto();
	}
	
	@Override
	public ServiceResultDto checkDuplMember(MemberInput parameter) {

		//중복체크
		Optional<Member> optionalMember = memberRepository.findByUsername(parameter.getUsername());
		if(optionalMember.isPresent()) {
			return new ServiceResultDto();
		}
		
		return new ServiceResultDto(false);
	}

	@Override
	public ServiceResultDto updateMember(MemberInput parameter) {
		//select를 통해 영속성컨텍스트에 객체 올리기(이후 수정된 정보 세팅 후 변화 감지로 커밋되도록 하기 위함)
		Member member = memberRepository.findById(parameter.getId()).orElseThrow(()->{
			throw new IllegalArgumentException("존재하지 않는 회원입니다.");
		});
		
		if(member.getOauth() == null || member.getOauth().isBlank()) {
			if(!BCrypt.checkpw(parameter.getCurrPassword(), member.getPassword())) {
				return new ServiceResultDto(false, "현재 비밀번호가 일치하지 않습니다.");
			}
			
			member.setPassword(encoder.encode(parameter.getPassword()));
			member.setEmail(parameter.getEmail());
			member.setNickname(parameter.getNickname());
			member.setAccessScope(parameter.getAccessScope());
		}
		//서비스 종료 시 트랜잭션 종료, 컨텍스트 내용 커밋 - open-in-view
		
		return new ServiceResultDto();
	}

}
