package com.smstudy.mylog.member.model;

import lombok.Data;

@Data
public class MemberInput {

	long id;
	String username;
	String password;
	String email;
	String nickname;
	String accessScope;
	
	//회원정보 수정 확인용 비밀번호
	String currPassword;
	
}
