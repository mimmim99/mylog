package com.smstudy.mylog.member.dto;

import com.smstudy.mylog.member.entity.Member;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MemberDto {

	private long id;
	private String username;
	private String password;
	private String email;
	private String nickname;
	private String oauth;
	
	public static MemberDto of(Member member) {
		return MemberDto.builder().id(member.getId())
								  .username(member.getUsername())
								  .password(member.getPassword())
								  .email(member.getEmail())
								  .nickname(member.getNickname())
								  .oauth(member.getOauth())
								  .build();
	}
}
