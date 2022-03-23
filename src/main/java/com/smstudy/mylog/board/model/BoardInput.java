package com.smstudy.mylog.board.model;

import com.smstudy.mylog.member.entity.Member;

import lombok.Data;

@Data
public class BoardInput {

	private long id;
	private String title;
	private String keywords;
	private String content;
	private Member member;
	private boolean postYn;
	
	//파일리스트 (최종본과 비교하기 위함)
	private String[] contentFiles;
}
